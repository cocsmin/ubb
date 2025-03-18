import os
import re
import webbrowser

from matplotlib import pyplot as plt
from skimage import io


# Cerinta 1
def count_sentences():
    with open('texts.txt', 'r') as file:
        content = file.read()
    sentence_list = re.split(r'[.!?]\s*', content)
    sentence_list = [s for s in sentence_list if s.strip()]
    print(f"Numărul de propoziții este: {len(sentence_list)}")

count_sentences()

# Cerinta 2
def count_words():
    with open('texts.txt', 'r') as file:
        content = file.read()
    word_list = re.findall(r'\w+', content)
    print(f"Numărul de cuvinte este: {len(word_list)}")

count_words()

# Cerinta 3
def count_unique_words():
    with open('texts.txt', 'r') as file:
        content = file.read()
    word_list = re.findall(r'\w+', content)
    unique_set = set()
    for w in word_list:
        if w not in unique_set:
            unique_set.add(w)
    print(f"Numărul de cuvinte este: {len(unique_set)}")

count_unique_words()

# Cerinta 4
def find_min_max_words():
    with open('texts.txt', 'r') as file:
        content = file.read()
    word_list = re.findall(r'\w+', content)
    words_by_length = {}

    for word in word_list:
        length = len(word)
        if length not in words_by_length:
            words_by_length[length] = [word]
        else:
            if word not in words_by_length[length]:
                words_by_length[length].append(word)

    min_length = min(words_by_length.keys())
    max_length = max(words_by_length.keys())
    shortest = words_by_length[min_length]
    longest = words_by_length[max_length]

    print(f"Cuvintele cu cele mai puține litere ({min_length} litere):")
    print(shortest)
    print(f"\nCuvintele cu cele mai multe litere ({max_length} litere):")
    print(longest)

find_min_max_words()

# Cerinta 5
def remove_diacritics():
    with open('texts.txt', 'r') as file:
        content = file.read()

    content = content.replace("ș", "s")
    content = content.replace("Ș", "S")
    content = content.replace("ț", "t")
    content = content.replace("Ț", "T")
    content = content.replace("ă", "a")
    content = content.replace("Ă", "A")
    content = content.replace("î", "i")
    content = content.replace("Î", "I")
    content = content.replace("â", "a")
    content = content.replace("Â", "A")

    print(content)

remove_diacritics()

# Cerinta 6
def search_synonyms():
    with open('texts.txt', 'r') as file:
        content = file.read()
    word_list = re.findall(r'\w+', content)

    longest_word = ''
    for word in word_list:
        modified = re.sub(r"(.)\1{2,}", r"\1\1", word)
        if len(modified) > len(longest_word):
            longest_word = modified

    query_url = f"https://www.google.com/search?q=sinonime+pentru+cuvantul+{longest_word}"
    webbrowser.open(query_url)

search_synonyms()

# Problema 4
def print_sentence_frequencies():
    file = os.path.join('pictures', 'chatGPT.png')
    image = io.imread(file)
    image = image / 255

    plt.imshow(image)
    plt.title('Image with Pixel Values')
    plt.axis('off')

    print("Shape of the image:", image.shape)
    print("Data type of the image:", image.dtype)
    print("Pixel values in the image:")
    print(image)

print_sentence_frequencies()
