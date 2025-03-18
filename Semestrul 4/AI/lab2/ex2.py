import re

import matplotlib

matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
import skimage
from skimage import io
from skimage.feature import canny
from skimage.color import rgb2gray
from skimage.transform import resize
import os


# Cerinta 1
def show_single_image():
    img_path = os.path.join("/Users/cosmin/Documents/facultate/sem4/AI/pictures", "Altman.webp")
    img = io.imread(img_path)
    io.imshow(img)
    io.show()


show_single_image()

# Cerintele 2,3

def fetch_and_resize_images(directory='pictures', new_size=(128, 128), as_gray=False):
    valid_ext = ('.jpg', '.jpeg', '.png', '.webp')
    files = [f for f in os.listdir(directory) if f.endswith(valid_ext)]
    resized_imgs = []
    for file in files:
        full_path = os.path.join(directory, file)
        image = io.imread(full_path, as_gray=as_gray)
        image_resized = resize(image, new_size, anti_aliasing=True)
        resized_imgs.append(image_resized)
    return resized_imgs, files

def show_images_grid(img_list, file_names, cols=3, cmap_val=None):
    rows = (len(img_list) + cols - 1) // cols
    fig, axs = plt.subplots(rows, cols, figsize=(cols * 3, rows * 3))
    axs = axs.ravel()

    for i, (img, ax) in enumerate(zip(img_list, axs)):
        ax.imshow(img, cmap=cmap_val)
        ax.set_title(file_names[i])
        ax.axis('off')

    for j in range(i + 1, len(axs)):
        axs[j].axis('off')

    plt.tight_layout()
    plt.show()


color_imgs, color_files = fetch_and_resize_images()
show_images_grid(color_imgs, color_files)
gray_imgs, gray_files = fetch_and_resize_images(as_gray=True)
show_images_grid(gray_imgs, gray_files)


# Cerinta 4
def compare_blur_effect():
    file = os.path.join('pictures', 'Altman.webp')
    image = io.imread(file)
    blurred_image = skimage.filters.gaussian(image, sigma=2)

    images = [image, blurred_image]
    image_titles = ['Before (Original)', 'After (Blurred)']

    rows = 1
    columns = 2
    fig, axes = plt.subplots(rows, columns, figsize=(columns * 3, rows * 3))

    for i, (image, ax) in enumerate(zip(images, axes)):
        ax.imshow(image)
        ax.set_title(image_titles[i])

    io.imshow(image)
    io.show()

compare_blur_effect()


# Cerinta 5
def compare_edge_detection():
    img_path = os.path.join('pictures', 'LeCun.jpg')
    orig_img = io.imread(img_path)

    # Convert to grayscale if needed
    gray_img = rgb2gray(orig_img) if orig_img.ndim == 3 else orig_img
    edges = canny(gray_img, sigma=2)

    images = [orig_img, edges]
    titles = ['Before (Original)', 'After (Edges)']

    fig, axes = plt.subplots(1, 2, figsize=(2 * 3, 1 * 3))
    for idx, (img, ax) in enumerate(zip(images, axes)):
        if img.ndim == 3:
            ax.imshow(img)
        else:
            ax.imshow(img, cmap='gray')
        ax.set_title(titles[idx])

    plt.tight_layout()
    plt.show()


compare_edge_detection()


# Problema 4
def print_image_info():
    with open('texts.txt', 'r') as r:
        text = r.read()

    sentences = re.split(r'[.!?]\s*', text)
    sentences = [sentence for sentence in sentences if sentence.strip()]

    for sentence in sentences:
        words = re.findall(r'\w+', sentence)
        freq = {}

        for word in words:
            word = word.lower()
            if not word in freq:
                freq[word] = 1
            else:
                freq[word] += 1

        normalized_freq = {word: count / len(words) for word, count in freq.items()}

        print(sentence)
        print("Normalized Frequency:", normalized_freq)
        print()


print_image_info()
