import os
import cv2
from azure.cognitiveservices.vision.computervision import ComputerVisionClient
from msrest.authentication import CognitiveServicesCredentials

subscription_key = "azure_key"
endpoint = "azure_endpoint"

client = ComputerVisionClient(endpoint, CognitiveServicesCredentials(subscription_key))


def infer_classification(image_path):
    """
    Utilizează Azure pentru a obține o descriere a imaginii și pentru a decide dacă imaginea conține bicicletă.
    Returnează: predicted (True dacă s-a găsit 'bicycle' în descriere), și obiectul de analiză.
    """
    with open(image_path, "rb") as image_stream:
        analysis = client.describe_image_in_stream(image_stream)
    predicted = False
    if analysis.captions:
        for caption in analysis.captions:
            if "bicycle" in caption.text.lower():
                predicted = True
                break
    return predicted, analysis


def test_classification(image_files, folder_path):
    """
    Parcurge lista de imagini, aplică inferența și compară predicția cu eticheta ground truth.
    Ground truth: imaginile care încep cu "bike" sunt pozitive, iar cele "traffic" negative.
    Returnează rezultatele și acuratețea clasificării.
    """
    results = {}
    for image_file in image_files:
        true_label = image_file.startswith("bike")
        image_path = os.path.join(folder_path, image_file)
        predicted, analysis = infer_classification(image_path)
        results[image_file] = {"predicted": predicted, "expected": true_label, "analysis": analysis}
        print(f"{image_file}: predicted = {predicted}, expected = {true_label}")

    correct = sum(1 for file in image_files if results[file]["predicted"] == results[file]["expected"])
    accuracy = correct / len(image_files)
    print(f"\nAccuracy-ul clasificarii: {accuracy:.2f}")
    return results, accuracy


#Cerința 2. a)
def detect_bicycles(image_path, confidence_threshold=0.3):
    """
    Utilizează API-ul detect_objects_in_stream pentru a detecta automat bicicletele.
    Se caută atât 'bicycle' cât și 'bike' în denumirea obiectului.
    Returnează o listă de bounding boxes în formatul (x, y, w, h).
    """
    with open(image_path, "rb") as image_stream:
        detection = client.detect_objects_in_stream(image_stream)
    detected_boxes = []
    if detection.objects:
        for obj in detection.objects:
            label = obj.object_property.lower()
            if ("bicycle" in label or "bike" in label) and obj.confidence >= confidence_threshold:
                bbox = obj.rectangle
                detected_boxes.append((bbox.x, bbox.y, bbox.w, bbox.h))
    return detected_boxes


def draw_boxes(image_path, boxes, output_path, box_color=(0, 255, 0)):
    """
    Desenează bounding boxes pe imagine și salvează rezultatul.
    Parametrul box_color poate fi specificat (implicit verde).
    Se așteaptă ca box-urile să fie în formatul (x, y, w, h).
    """
    image = cv2.imread(image_path)
    for (x, y, w, h) in boxes:
        cv2.rectangle(image, (x, y), (x + w, y + h), box_color, 2)
    cv2.imwrite(output_path, image)


def draw_manual_boxes(image_path, manual_boxes, output_path):
    """
    Desenează bounding boxes pe imagine folosind coordonatele manuale, în formatul (x1, y1, x2, y2),
    și salvează rezultatul. Folosește culoarea roșie pentru a evidenția chenarele manuale.
    """
    image = cv2.imread(image_path)
    for (x1, y1, x2, y2) in manual_boxes:
        cv2.rectangle(image, (x1, y1), (x2, y2), (0, 0, 255), 2)
    cv2.imwrite(output_path, image)


def convert_detection_box_to_coords(box):
    """
    Convertește un bounding box de la formatul (x, y, w, h) la (x1, y1, x2, y2)
    unde (x1, y1) este colțul din stânga sus și (x2, y2) este colțul din dreapta jos.
    """
    x, y, w, h = box
    return (x, y, x + w, y + h)


# Cerința 2. b)

# coord manuale definite în formatul (x1, y1, x2, y2)
manual_annotations = {
    "bike01.jpg": [(5, 32, 411, 404)],
    "bike02.jpg": [(15, 88, 381, 320)],
    "bike03.jpg": [(159, 148, 345, 410)],
    "bike04.jpg": [(2, 2, 417, 418)],
    "bike05.jpg": [(69, 54, 357, 350)],
    "bike06.jpg": [(69, 150, 197, 398)],
    "bike07.jpg": [(60, 184, 298, 420)],
    "bike08.jpg": [(55, 8, 389, 352)],
    "bike09.jpg": [(5, 12, 382, 386)],
    "bike10.jpg": [(142, 126, 375, 410)]
}


# Cerința 2. c)
def compute_iou(boxA, boxB):
    """
    Calculează Intersection over Union (IoU) pentru două bounding boxes.
    Ambii box sunt așteptați în formatul (x1, y1, x2, y2).
    """
    # Calculăm coordonatele intersecției
    xA = max(boxA[0], boxB[0])
    yA = max(boxA[1], boxB[1])
    xB = min(boxA[2], boxB[2])
    yB = min(boxA[3], boxB[3])

    interW = max(0, xB - xA)
    interH = max(0, yB - yA)
    interArea = interW * interH

    # Aria totală a fiecărui box
    boxAArea = (boxA[2] - boxA[0]) * (boxA[3] - boxA[1])
    boxBArea = (boxB[2] - boxB[0]) * (boxB[3] - boxB[1])

    iou = interArea / float(boxAArea + boxBArea - interArea) if (boxAArea + boxBArea - interArea) > 0 else 0
    return iou


def evaluate_detection(manual_annotations, detection_results, iou_threshold=0.5):
    """
    Compară bounding boxes detectate (din 2a) cu cele manual anotate (din 2b) folosind:
      - IoU mediu
      - Precizie și Recall (bazate pe pragul IoU)
    Returnează un dicționar cu metricile calculate.
    """
    total_iou = []
    total_tp = 0
    total_fp = 0
    total_fn = 0

    for image_file, gt_boxes in manual_annotations.items():
        detected_boxes = detection_results.get(image_file, [])
        matched = [False] * len(gt_boxes)
        image_tp = 0

        for det_box in detected_boxes:
            det_box_coords = convert_detection_box_to_coords(det_box)
            best_iou = 0
            best_idx = -1
            for idx, gt_box in enumerate(gt_boxes):
                iou = compute_iou(det_box_coords, gt_box)
                if iou > best_iou:
                    best_iou = iou
                    best_idx = idx
            if best_iou >= iou_threshold and best_idx != -1 and not matched[best_idx]:
                image_tp += 1
                matched[best_idx] = True
                total_iou.append(best_iou)
            else:
                total_fp += 1

        image_fn = len(gt_boxes) - image_tp
        total_fn += image_fn
        total_tp += image_tp

    avg_iou = sum(total_iou) / len(total_iou) if total_iou else 0
    precision = total_tp / (total_tp + total_fp) if (total_tp + total_fp) > 0 else 0
    recall = total_tp / (total_tp + total_fn) if (total_tp + total_fn) > 0 else 0

    print(f"\nEvaluare detectare:")
    print(f"IoU mediu: {avg_iou:.2f}")
    print(f"Precizie: {precision:.2f}")
    print(f"Recall: {recall:.2f}")

    return {"avg_iou": avg_iou, "precision": precision, "recall": recall}


folder_path = "bikes"
image_files = ["bike{:02d}.jpg".format(i) for i in range(1, 11)] + ["traffic{:02d}.jpg".format(i) for i in range(1, 11)]

print(" Cerința 1: Clasificarea imaginilor...")
classification_results, classification_accuracy = test_classification(image_files, folder_path)

print("\n Cerința 2a): Detectare auto chenarelor bicicletelor... \n")
detection_results = {}
for image_file in image_files:
    if image_file.startswith("bike"):
        image_path = os.path.join(folder_path, image_file)
        boxes = detect_bicycles(image_path, confidence_threshold=0.3)
        detection_results[image_file] = boxes
        print(f"{image_file}: Detectate {len(boxes)} obiecte -> {boxes}")
        if boxes:
            output_path = os.path.join(folder_path, "auto_" + image_file)
            draw_boxes(image_path, boxes, output_path, box_color=(0, 255, 0))
        else:
            print(f" Atenție: Nu s-au găsit chenare auto pentru {image_file}.")

print("\n Cerința 2b): Desenare manuala a chenarelor bicicletelor... \n")
for image_file, manual_boxes in manual_annotations.items():
    image_path = os.path.join(folder_path, image_file)
    output_path = os.path.join(folder_path, "manual_" + image_file)
    draw_manual_boxes(image_path, manual_boxes, output_path)
    print(f"{image_file}: Done.")

print("\n Clar 2 b) a durat mai mult ca pana am stat sa numar pixelii aia vai si-amar \n")

print("Cerința 2c: Evaluare performanță detectare")
metrics = evaluate_detection(manual_annotations, detection_results, iou_threshold=0.5)
