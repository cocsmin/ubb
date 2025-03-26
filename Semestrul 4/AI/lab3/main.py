
import jellyfish
import numpy as np
from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from azure.cognitiveservices.vision.computervision.models import OperationStatusCodes
from azure.cognitiveservices.vision.computervision.models import VisualFeatureTypes
from msrest.authentication import CognitiveServicesCredentials
from array import array
import os
from PIL import Image, ImageFilter, ImageOps
import sys
import time

from skimage import color, filters, exposure, io
from skimage.morphology import square, closing
from skimage.restoration import denoise_tv_chambolle

'''
Authenticate
Authenticates your credentials and creates a client
'''

subscription_key = "azure_key"
endpoint = "azure_endpoint"
computervision_client = ComputerVisionClient(endpoint, CognitiveServicesCredentials(subscription_key))
'''
END - Authenticate
'''

'\nEND - Authenticate \n'

# read_response = computervision_client.read_in_stream(
#     image=img,
#     mode="Printed",
#     raw=True
# )
#
# operation_id = read_response.headers['Operation-Location'].split('/')[-1]
# while True:
#     read_result = computervision_client.get_read_result(operation_id)
#     if read_result.status not in ['notStarted', 'running']:
#         break
#     time.sleep(1)
#
# result = []
# if read_result.status == OperationStatusCodes.succeeded:
#     for text_result in read_result.analyze_result.read_results:
#         for line in text_result.lines:
#             #print(line.text)
#             result.append(line.text)
#
# print()
#
# #evaluating
#
# #groundTruth = ["Google Cloud", "Platform"]
# groundTruth = ["Succes in rezolvarea", "tEMELOR la", "LABORAtoaree de", "Inteligenta Artificiala!"]
#
# #compute
# noOfCorrectLines = sum(i == j for i, j in zip(result, groundTruth))
# print(noOfCorrectLines)


def image_refining(image_path):
    img = io.imread(image_path)

    # 2. Reducerea zgomotului (dacă imaginea are canale de culoare, se specifică multichannel=True)
    img_denoised = denoise_tv_chambolle(img, weight=0.1)

    # 3. Conversia în grayscale
    img_gray = color.rgb2gray(img_denoised)

    # 4. Aplicarea unui unsharp mask pentru a accentua detaliile
    img_sharp = filters.unsharp_mask(img_gray, radius=1, amount=1)

    # 5. Ajustarea contrastului
    img_contrast = exposure.rescale_intensity(img_sharp, in_range=(0.2, 0.9))

    # 6. Binarizarea imaginii: calcularea pragului Otsu și aplicarea acestuia
    threshold_value = filters.threshold_otsu(img_contrast)
    img_binary = img_contrast > threshold_value

    # 7. Aplicarea operației morfologice de closing pentru a umple găurile mici
    img_closed = closing(img_binary, square(3))

    # 8. Convertirea imaginii binare (boolean) într-o imagine uint8 (0 și 255)
    img_result = (img_closed * 255).astype(np.uint8)

    # 9. Convertirea într-un obiect PIL.Image și salvarea imaginii procesate
    img_bin_pil = Image.fromarray(img_result)
    img_bin_pil.save("test2_procesat.jpg")

    return img_bin_pil

def levenshtein_distance(seq1, seq2):
    """
    Calculează distanța Levenshtein între două secvenţe (poate fi folosită pentru string-uri sau liste).
    """
    if len(seq1) < len(seq2):
        return levenshtein_distance(seq2, seq1)
    if len(seq2) == 0:
        return len(seq1)
    previous_row = list(range(len(seq2) + 1))
    for i, c1 in enumerate(seq1):
        current_row = [i + 1]
        for j, c2 in enumerate(seq2):
            insertions = previous_row[j + 1] + 1
            deletions = current_row[j] + 1
            substitutions = previous_row[j] + (c1 != c2)
            current_row.append(min(insertions, deletions, substitutions))
        previous_row = current_row
    return previous_row[-1]


def compute_cer(recognized_text, ground_truth):
    """
    Calculează Character Error Rate (CER) ca:
      CER = Levenshtein(recognized_text, ground_truth) / len(ground_truth)
    """
    if not ground_truth:
        return 0
    distance = levenshtein_distance(recognized_text, ground_truth)
    return distance / len(ground_truth)


def compute_wer(recognized_text, ground_truth):
    """
    Calculează Word Error Rate (WER) folosind Levenshtein pe liste de cuvinte.
    """
    recognized_words = recognized_text.split()
    ground_truth_words = ground_truth.split()
    if not ground_truth_words:
        return 0
    distance = levenshtein_distance(recognized_words, ground_truth_words)
    return distance / len(ground_truth_words)


def compute_hamming_distance(s1, s2):
    """
    Calculează Hamming distance (doar pentru string-uri de aceeaşi lungime).
    """
    if len(s1) != len(s2):
        raise ValueError("Șirurile trebuie să aibă aceeași lungime pentru Hamming distance.")
    return sum(ch1 != ch2 for ch1, ch2 in zip(s1, s2))


def compute_iou(boxA, boxB):
    """
    Calculează Intersection over Union (IoU) între două bounding box-uri.
    Fiecare box este reprezentat ca un tuplu (x1, y1, x2, y2).
    """
    xA = max(boxA[0], boxB[0])
    yA = max(boxA[1], boxB[1])
    xB = min(boxA[2], boxB[2])
    yB = min(boxA[3], boxB[3])
    inter_width = max(0, xB - xA)
    inter_height = max(0, yB - yA)
    inter_area = inter_width * inter_height
    boxA_area = (boxA[2] - boxA[0]) * (boxA[3] - boxA[1])
    boxB_area = (boxB[2] - boxB[0]) * (boxB[3] - boxB[1])
    union_area = boxA_area + boxB_area - inter_area
    return inter_area / union_area if union_area != 0 else 0


def ocr_azure(img, computervision_client):
    """
    Realizează OCR pe imaginea dată folosind Azure Computer Vision.
    Returnează textul recunoscut și lista de bounding box-uri pentru fiecare linie detectată.
    """
    read_response = computervision_client.read_in_stream(
        image=img,
        mode="Printed",
        raw=True
    )

    operation_location = read_response.headers["Operation-Location"]
    operation_id = operation_location.split("/")[-1]

    while True:
        result = computervision_client.get_read_result(operation_id)
        if result.status.lower() not in ['notstarted', 'running']:
            break
        time.sleep(1)

    recognized_text = ""
    bounding_boxes = []
    if result.status == "succeeded":
        for page in result.analyze_result.read_results:
            for line in page.lines:
                recognized_text += line.text + "\n"
                pts = line.bounding_box
                x_coords = pts[0::2]
                y_coords = pts[1::2]
                x_min, x_max = min(x_coords), max(x_coords)
                y_min, y_max = min(y_coords), max(y_coords)

                box = (x_min, y_min, x_max, y_max)
                bounding_boxes.append(box)
                # bounding_boxes.append(line.bounding_box)

    return recognized_text.strip(), bounding_boxes


# img = open("test1.png", "rb")
# img = open("test2.jpeg", "rb")
# img = open("shaorma.jpg", "rb")
img_r = image_refining("test2.jpeg")
img = open("test2_procesat.jpg", "rb")

recognized_text, detected_boxes = ocr_azure(img, computervision_client)

print("Textul recunoscut de OCR:")
print(recognized_text)
print("\nBounding boxes detectate:")
for box in detected_boxes:
    print(box)


# ground_truth_text = "Google Cloud Platform"
ground_truth_text = "Succes in rezolvarea tEMELOR la LABORAtoaree de Inteligenta Artificiala!"
# ground_truth_text = "Mie imi place shaorma"
# ground_truth_box = [(170, 40, 415, 90),
#                     (235, 110, 350, 145)]
ground_truth_box = [(70, 305, 1335, 430),
                    (130, 590, 1050, 710),
                    (80, 925, 1010, 1025),
                    (100, 1140, 1450, 1285)]
# ground_truth_box = [(240, 215, 1890, 840),
#                     (585, 985, 1460, 1190)]

# 1) a)
cer = compute_cer(recognized_text, ground_truth_text)
wer = compute_wer(recognized_text, ground_truth_text)
print("\nEvaluare folosind Levenshtein (o singură metrică):")
print("Character Error Rate (CER):", cer)
print("Word Error Rate (WER):", wer)

# 1) b)
metrics = {}
metrics["levenshtein_cer"] = cer
metrics["levenshtein_wer"] = wer

# Hamming distance
try:
    if len(recognized_text) == len(ground_truth_text):
        hamming = compute_hamming_distance(recognized_text, ground_truth_text) / len(ground_truth_text)
        metrics["hamming_cer"] = hamming
except ValueError as ve:
    metrics["hamming_cer"] = str(ve)

# Jaro-Winkler
if jellyfish is not None:
    jaro_sim = jellyfish.jaro_winkler_similarity(recognized_text, ground_truth_text)
    metrics["jaro_winkler_cer"] = 1 - jaro_sim

print("\nEvaluare folosind mai multe metrici:")
for key, value in metrics.items():
    print(f"{key}: {value}")

# 2)
for i, detected_box in enumerate(detected_boxes):
    iou = compute_iou(detected_box, ground_truth_box[i])
    print("\nCalitatea localizării textului (IoU):", iou)

# 3)
# print("\nSugestii pentru îmbunătățirea recunoașterii textului:")
# print("1. Preprocesare: aplicarea de filtre pentru eliminarea zgomotului, ajustarea contrastului și binarizarea imaginii.")
# print("2. Utilizarea unui model OCR mai performant sau a unor modele de deep learning specializate.")
# print("3. Postprocesare: corectarea automată a textului folosind dicționare sau modele de limbaj.")
# print("4. Combinarea rezultatelor din mai multe surse/metode OCR pentru o recunoaștere mai robustă.")

# "bike01.jpg": [(5, 32, 411, 404)],
# "bike02.jpg": [(15, 88, 381, 320)],
# "bike03.jpg": [(159, 148, 345, 410)],
# "bike04.jpg": [(2, 2, 417, 418)],
# "bike05.jpg": [(69, 237, 357, 350)],
# "bike06.jpg": [(69, 150, 197, 398)],  # Adăugat manual - presupunem că este prezentă bicicleta
# "bike07.jpg": [(60, 184, 298, 420)],
# "bike08.jpg": [(55, 8, 389, 352)],  # Adăugat manual
# "bike09.jpg": [(5, 12, 382, 386)],
# "bike10.jpg": [(142, 126, 375, 410)]

