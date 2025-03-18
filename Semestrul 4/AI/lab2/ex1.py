import matplotlib
import numpy as np
import pandas as pd
from matplotlib import pyplot as plt
matplotlib.use('TkAgg')



df_lab = pd.read_csv("/Users/cosmin/Documents/facultate/sem4/AI/surveyDataSience.csv", delimiter=',', low_memory=False, skiprows=[1,2])

def nrOfRespondents():
    nrRespondenti = df_lab.shape[0]

    print(nrRespondenti)

nrOfRespondents()


def nrAndTypeOfInfoForRespondents():
    nr_informatii = df_lab.shape[1]

    print("Numarul de informatii pentru un respondent este: ", nr_informatii)

    print("Tipul informatiilor este: ", df_lab.dtypes)

nrAndTypeOfInfoForRespondents()


def nrOfRespondentsWithCompleteData():
    nr_complet = df_lab.notnull().all(axis=1).sum()

    print("Numarul de respondenti cu date complete este egal cu: ", nr_complet)

nrOfRespondentsWithCompleteData()

def meanStudyYears():
    mapStudyYears = {
        "Bachelor’s degree": 3,
        "Master’s degree": 5,
        "Doctoral degree": 8
    }
    df_lab["Study_Years"] = df_lab["Q4"].map(mapStudyYears)

    meanYears = df_lab["Study_Years"].mean()

    print("Media anilor de studiu este= ", meanYears)

    return meanYears

meanStudyYears()

def meanStudyYearsRomanians():
    mapStudyYears = {
        "Bachelor’s degree" : 3,
        "Master’s degree": 5,
        "Doctoral degree": 8
    }

    df_lab["Study_Years"] = df_lab["Q4"].map(mapStudyYears)


    df_romania = df_lab[df_lab["Q3"] == "Romania"]

    meanYears = df_romania["Study_Years"].mean()

    print("Media anilor de studiu din Romania este= ", meanYears)

    return meanYears

meanStudyYearsRomanians()

def meanStudyYearsRomanianWoman():
    mapStudyYears = {
        "Bachelor’s degree" : 3,
        "Master’s degree": 5,
        "Doctoral degree": 8
    }

    df_lab["Study_Years"] = df_lab["Q4"].map(mapStudyYears)


    df_romania_femei = df_lab[(df_lab["Q3"] == "Romania") & (df_lab["Q2"] == "Woman")]

    meanYears = df_romania_femei["Study_Years"].mean()

    print("Media anilor de studiu din Romania este= ", meanYears)

    return meanYears

meanStudyYearsRomanianWoman()

def compareStudyYears():
    studiuTotal = meanStudyYears()
    studiuRomania = meanStudyYearsRomanians()
    studiuRomaniaFemei = meanStudyYearsRomanianWoman()

    print("Comparație între valorile medii ale anilor de studiu:")

    print(f"Media anilor de studiu total: {studiuTotal}")
    print(f"Media anilor de studiu pentru români: {studiuRomania}")
    print(f"Media anilor de studiu pentru femeile din România: {studiuRomaniaFemei}")

    if studiuTotal > studiuRomania:
        print("Studiul total are mai mulți ani decât media românilor.")
    elif studiuTotal < studiuRomania:
        print("Studiul total are mai puțini ani decât media românilor.")
    else:
        print("Studiul total are aceeași medie de ani ca media românilor.")

    if studiuRomania > studiuRomaniaFemei:
        print("Românii în general au mai mulți ani de studiu decât femeile din România.")
    elif studiuRomania < studiuRomaniaFemei:
        print("Femeile din România au mai mulți ani de studiu decât românii în general.")
    else:
        print("Românii și femeile din România au aceeași medie de ani de studiu.")

    if studiuTotal > studiuRomaniaFemei:
        print("Studiul total are mai mulți ani decât media femeilor din România.")
    elif studiuTotal < studiuRomaniaFemei:
        print("Studiul total are mai puțini ani decât media femeilor din România.")
    else:
        print("Studiul total are aceeași medie de ani ca media femeilor din România.")

compareStudyYears()

def respondentsWomanWithCompleteData():
    df_completDataWoman = df_lab[(df_lab["Q2"] == "Woman") & (df_lab.notna().all(axis=1))]

    total = df_completDataWoman.shape[0]

    print("Numarul inregistrarilor care indeplinesc conditia ca sa aibe date complete femeile sunt: ", total)

respondentsWomanWithCompleteData()

def nrOfWomanInRomaniaThatCodeInPython():
    femei_romania_python = df_lab[(df_lab['Q2'] == "Woman") & (df_lab['Q3'] == "Romania") & (df_lab['Q7_Part_1'] == "Python")]

    total = femei_romania_python.shape[0]

    print("Numarul femeilor din romania care codeaza in python este= ", total)

    return total

nrOfWomanInRomaniaThatCodeInPython()

def ageIntervalWithMostWomanThatCodeInPython():
    femei_python = df_lab[(df_lab['Q2'] == "Woman") & (df_lab['Q3'] == "Romania") & (df_lab['Q7_Part_1'] == "Python")]

    interval_varsta_femei_python = femei_python['Q1'].value_counts()

    interval_max_femei_python = interval_varsta_femei_python.idxmax()
    numar_femei_max = interval_varsta_femei_python.max()

    print(f"Intervalul de vârstă cu cele mai multe femei care programează în Python este: {interval_max_femei_python}")
    print(f"Numărul de femei din acest interval de vârstă este: {numar_femei_max}")

    return numar_femei_max

ageIntervalWithMostWomanThatCodeInPython()

def ageIntervalWithMostWomanThatCodeInCpp():
    femei_python = df_lab[(df_lab['Q2'] == "Woman") & (df_lab['Q3'] == "Romania") & (df_lab['Q7_Part_5'] == "C++")]

    interval_varsta_femei_python = femei_python['Q1'].value_counts()

    interval_max_femei_python = interval_varsta_femei_python.idxmax()
    numar_femei_max = interval_varsta_femei_python.max()

    print(f"Intervalul de vârstă cu cele mai multe femei care programează în Python este: {interval_max_femei_python}")
    print(f"Numărul de femei din acest interval de vârstă este: {numar_femei_max}")

    return numar_femei_max

ageIntervalWithMostWomanThatCodeInCpp()

def compareFunctionsResults():

    nr_femei_python = nrOfWomanInRomaniaThatCodeInPython()
    nr_femei_python_max_interval = ageIntervalWithMostWomanThatCodeInPython()
    nr_femei_cpp_max_interval = ageIntervalWithMostWomanThatCodeInCpp()

    print("\nCompararea rezultatelor:")
    print(f"Numărul total de femei care codează în Python: {nr_femei_python}")
    print(
        f"Numărul de femei din intervalul de vârstă cu cele mai multe femei care programează în Python: {nr_femei_python_max_interval}")
    print(
        f"Numărul de femei din intervalul de vârstă cu cele mai multe femei care programează în C++: {nr_femei_cpp_max_interval}")

    if nr_femei_python > nr_femei_python_max_interval:
        print(
            "\nMai multe femei din România codează în Python decât cele din intervalul cu cele mai multe femei care codează în Python.")
    else:
        print(
            "\nMai multe femei din intervalul de vârstă cu cele mai multe femei care codează în Python decât femei din România care codează în Python.")

    if nr_femei_python_max_interval > nr_femei_cpp_max_interval:
        print(
            "\nIntervalul cu cele mai multe femei care programează în Python are mai multe femei decât intervalul cu cele mai multe femei care programează în C++.")
    else:
        print(
            "\nIntervalul cu cele mai multe femei care programează în C++ are mai multe femei decât intervalul cu cele mai multe femei care programează în Python.")

    if nr_femei_python == nr_femei_python_max_interval and nr_femei_python_max_interval == nr_femei_cpp_max_interval:
        print("\nNumărul de femei este același în toate cele trei cazuri.")
    else:
        print("\nNumerele din cele trei funcții nu sunt identice.")


compareFunctionsResults()

def get_feature_summary(df):
    for column in df.columns:
        print(f"Caracteristicile pentru coloana '{column}':")

        if pd.api.types.is_numeric_dtype(df[column]):
            min_value = df[column].min()  # Valoarea minimă
            max_value = df[column].max()  # Valoarea maximă
            print(f"  - Domeniul de valori: Min = {min_value}, Max = {max_value}")

        else:
            unique_values = df[column].unique()
            num_unique_values = len(unique_values)
            print(f"  - Valori posibile (unice): {num_unique_values} valori")
            print(f"    Valori unice: {unique_values[:5]}...")

            value_counts = df[column].value_counts()
            print(f"    Valoarea cea mai frecventă: {value_counts.idxmax()} ({value_counts.max()} apariții)")

        print()

get_feature_summary(df_lab)

def programmingExperience():
    vechime_map = {
        "< 1 years": 0.5,
        "1-3 years": 2,
        "3-5 years": 4,
        "5-10 years": 7.5,
        "10-20 years": 15,
        "20+ years": 30
    }

    df_lab["Experience"] = df_lab["Q6"].map(vechime_map)

    min_vechime = df_lab['Experience'].min()
    max_vechime = df_lab['Experience'].max()
    mean_vechime = df_lab['Experience'].mean()
    std_dev_vechime = df_lab['Experience'].std()
    median_vechime = df_lab['Experience'].median()

    print(f"Minimul vechimii în ani: {min_vechime}")
    print(f"Maximul vechimii în ani: {max_vechime}")
    print(f"Media vechimii în ani: {mean_vechime}")
    print(f"Deviatia standard a vechimii: {std_dev_vechime}")
    print(f"Mediana vechimii: {median_vechime}")

programmingExperience()

def visualizePythonProgrammersByAgeGroup():
    python = df_lab[(df_lab['Q7_Part_1'] == "Python")].copy()

    def convert_age_group_to_middle(age_group):
        if age_group == '70+':
            return 75
        else:
            start, end = map(int, age_group.split('-'))
            return (start + end) / 2

    python.loc[:, 'Age_Middle'] = python['Q1'].apply(convert_age_group_to_middle)

    bins = [18, 22, 25, 30, 35, 40, 45, 50, 55, 60, 70, 100]
    labels = ['18-21', '22-24', '25-29', '30-34', '35-39', '40-44', '45-49', '50-54', '55-59', '60-69', '70+']

    python.loc[:, 'Age_Group'] = pd.cut(python['Age_Middle'], bins=bins, labels=labels, right=False)

    age_distribution = python['Age_Group'].value_counts()

    plt.figure(figsize=(8, 6))
    age_distribution.sort_index().plot(kind='bar')
    plt.title('Distribuția oamenilor care programează în Python pe categorii de vârstă')
    plt.xlabel('Interval de vârstă')
    plt.ylabel('Număr de programatori')
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()

visualizePythonProgrammersByAgeGroup()

def visualizeRomanianPythonProgrammersByAgeGroup():
    python = df_lab[(df_lab['Q7_Part_1'] == "Python") & (df_lab['Q3'] == "Romania")].copy()

    def convert_age_group_to_middle(age_group):
        if age_group == '70+':
            return 75
        else:
            start, end = map(int, age_group.split('-'))
            return (start + end) / 2

    python['Age_Middle'] = python['Q1'].apply(convert_age_group_to_middle).copy()

    bins = [18, 22, 25, 30, 35, 40, 45, 50, 55, 60, 70, 100]
    labels = ['18-21', '22-24', '25-29', '30-34', '35-39', '40-44', '45-49', '50-54', '55-59', '60-69', '70+']

    python['Age_Group'] = pd.cut(python['Age_Middle'], bins=bins, labels=labels, right=False)

    age_distribution = python['Age_Group'].value_counts()

    plt.figure(figsize=(8, 6))
    age_distribution.sort_index().plot(kind='bar')
    plt.title('Distribuția oamenilor care programează în Python pe categorii de vârstă')
    plt.xlabel('Interval de vârstă')
    plt.ylabel('Număr de romani')
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()

visualizeRomanianPythonProgrammersByAgeGroup()

def visualizeRomanianWomanPythonProgrammersByAgeGroup():
    python = df_lab[(df_lab['Q7_Part_1'] == "Python") & (df_lab['Q3'] == "Romania") & (df_lab['Q2'] == "Woman")].copy()

    def convert_age_group_to_middle(age_group):
        if age_group == '70+':
            return 75
        else:
            start, end = map(int, age_group.split('-'))
            return (start + end) / 2

    python['Age_Middle'] = python['Q1'].apply(convert_age_group_to_middle).copy()

    bins = [18, 22, 25, 30, 35, 40, 45, 50, 55, 60, 70, 100]
    labels = ['18-21', '22-24', '25-29', '30-34', '35-39', '40-44', '45-49', '50-54', '55-59', '60-69', '70+']

    python['Age_Group'] = pd.cut(python['Age_Middle'], bins=bins, labels=labels, right=False)

    age_distribution = python['Age_Group'].value_counts()

    plt.figure(figsize=(8, 6))
    age_distribution.sort_index().plot(kind='bar')
    plt.title('Distribuția oamenilor care programează în Python pe categorii de vârstă')
    plt.xlabel('Interval de vârstă')
    plt.ylabel('Număr de femei din Romania')
    plt.xticks(rotation=45)
    plt.tight_layout()
    plt.show()

visualizeRomanianWomanPythonProgrammersByAgeGroup()

def identify_outliers_and_plot():
    programatori = df_lab

    programatori = programatori[programatori['Q1'].str.contains(r'\d+-\d+', na=False)]

    def convert_age_group_to_middle(age_group):
        if age_group == '70+':
            return 75
        else:
            start, end = map(int, age_group.split('-'))
            return (start + end) / 2

    programatori = programatori.copy()
    programatori['Age_Middle'] = programatori['Q1'].apply(convert_age_group_to_middle)

    Q1 = programatori['Age_Middle'].quantile(0.25)
    Q3 = programatori['Age_Middle'].quantile(0.75)
    IQR = Q3 - Q1

    lower_limit = Q1 - 1.5 * IQR
    upper_limit = Q3 + 1.5 * IQR

    outliers = programatori[(programatori['Age_Middle'] < lower_limit) | (programatori['Age_Middle'] > upper_limit)]

    plt.figure(figsize=(8, 6))
    plt.boxplot(programatori['Age_Middle'])
    plt.title('Distribuția vechimii în programare (Toți programatorii)')
    plt.ylabel('Vechimea în programare (ani)')
    plt.xticks([1], ['Programatori'])
    plt.tight_layout()
    plt.show()

    print("Outlieri identifiati:")
    print(outliers)

    return outliers

identify_outliers_and_plot()
