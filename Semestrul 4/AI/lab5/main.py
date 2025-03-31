import matplotlib
import numpy as np
import pandas as pd
import matplotlib
matplotlib.use('TkAgg')
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression


# Funcție pentru regresie manuală (OLS)
def ols_unidimensional(X, y):
    n = len(X)
    sum_x = sum(X)
    sum_y = sum(y)
    sum_x2 = sum(x ** 2 for x in X)
    sum_xy = sum(x * yi for x, yi in zip(X, y))
    slope = (n * sum_xy - sum_x * sum_y) / (n * sum_x2 - sum_x ** 2)
    intercept = (sum_y - slope * sum_x) / n
    return (slope, intercept)


def ols_multidimensional(X, y):
    def transpose(matrix):
        return [list(row) for row in zip(*matrix)]

    def matrix_multiply(a, b):
        return [
            [sum(a_row[i] * b_col[i] for i in range(len(a_row)))
             for b_col in zip(*b)
             ]
            for a_row in a
        ]

    def matrix_inverse(matrix):
        n = len(matrix)
        if n == 0 or any(len(row) != n for row in matrix):
            raise ValueError("Matrix must be square")
        augmented = [row[:] + [1.0 if i == j else 0.0 for j in range(n)] for i, row in enumerate(matrix)]
        for col in range(n):
            pivot_row = max(range(col, n), key=lambda r: abs(augmented[r][col]))
            if augmented[pivot_row][col] == 0:
                raise ValueError("Matrix is singular and cannot be inverted")
            augmented[col], augmented[pivot_row] = augmented[pivot_row], augmented[col]
            pivot_val = augmented[col][col]
            augmented[col] = [x / pivot_val for x in augmented[col]]
            for r in range(n):
                if r != col:
                    factor = augmented[r][col]
                    augmented[r] = [x - factor * augmented[col][i] for i, x in enumerate(augmented[r])]
        inverse = [row[n:] for row in augmented]
        return inverse

    if len(X) == 0 or len(X) != len(y):
        raise ValueError("X and y must have the same number of samples")

    X = [list(row) for row in X]

    X_aug = [[1.0] + row for row in X]
    X_aug_T = transpose(X_aug)
    XTX = matrix_multiply(X_aug_T, X_aug)
    try:
        XTX_inv = matrix_inverse(XTX)
    except ValueError as e:
        raise ValueError("Matrix is singular, cannot compute inverse") from e
    y_col = [[yi] for yi in y]
    XTy = matrix_multiply(X_aug_T, y_col)
    beta_matrix = matrix_multiply(XTX_inv, XTy)
    beta = [row[0] for row in beta_matrix]
    return beta


def process_file(file_path, file_label):
    print("\nPrelucrarea datelor pentru:", file_label)

    df = pd.read_csv(file_path)
    df.dropna(inplace=True)
    print("Primele 5 rânduri (după eliminarea valorilor lipsă):")
    print(df.head())

    # cazul 1: Doar de Produsul intern brut (GDP)
    print("\n--- Scenariul 1: GDP ")
    X_gdp = df[["Economy..GDP.per.Capita."]].values
    y = df["Happiness.Score"].values

    # tool (scikit-learn)
    model_gdp = LinearRegression()
    model_gdp.fit(X_gdp, y)
    slope_gdp_tool = model_gdp.coef_[0]
    intercept_gdp_tool = model_gdp.intercept_
    print("[Tool] GDP -> Panta: {:.4f}, Intercept: {:.4f}".format(slope_gdp_tool, intercept_gdp_tool))

    # manuala (unidimensional)
    X_gdp_flat = df["Economy..GDP.per.Capita."].values
    slope_gdp_manual, intercept_gdp_manual = ols_unidimensional(X_gdp_flat, y)
    print("[Manual] GDP -> Panta: {:.4f}, Intercept: {:.4f}".format(slope_gdp_manual, intercept_gdp_manual))

    y_pred_tool = model_gdp.predict(X_gdp)
    plt.figure(figsize=(8, 5))
    plt.scatter(X_gdp, y, color="blue", label="Date reale")
    plt.plot(X_gdp, y_pred_tool, color="red", linewidth=2, label="Regresie (Tool)")
    plt.xlabel("Economy..GDP.per.Capita.")
    plt.ylabel("Happiness.Score")
    plt.title(f"{file_label}: Happiness vs GDP")
    plt.legend()
    plt.show()

    # cazul 2: Doar de "Family"
    print("\n--- Scenariul 2: Family ---")
    X_family = df[["Family"]].values

    # tool
    model_family = LinearRegression()
    model_family.fit(X_family, y)
    slope_family_tool = model_family.coef_[0]
    intercept_family_tool = model_family.intercept_
    print("[Tool] Family -> Panta: {:.4f}, Intercept: {:.4f}".format(slope_family_tool, intercept_family_tool))

    # manuală (unidimensional)
    X_family_flat = df["Family"].values
    slope_family_manual, intercept_family_manual = ols_unidimensional(X_family_flat, y)
    print("[Manual] Family -> Panta: {:.4f}, Intercept: {:.4f}".format(slope_family_manual, intercept_family_manual))

    y_pred_family_tool = model_family.predict(X_family)
    plt.figure(figsize=(8, 5))
    plt.scatter(X_family, y, color="blue", label="Date reale")
    plt.plot(X_family, y_pred_family_tool, color="red", linewidth=2, label="Regresie (Tool)")
    plt.xlabel("Family")
    plt.ylabel("Happiness.Score")
    plt.title(f"{file_label}: Happiness vs Family")
    plt.legend()
    plt.show()

    # cazul 3: De GDP și Freedom (regresie multiplă)
    print("\n--- Scenariul 3: GDP și Freedom ---")
    X_multi = df[["Economy..GDP.per.Capita.", "Freedom"]].values

    # tool
    model_multi = LinearRegression()
    model_multi.fit(X_multi, y)
    coeff_multi_tool = model_multi.coef_
    intercept_multi_tool = model_multi.intercept_
    print("[Tool] Multi -> Coeficienți: {}, Intercept: {:.4f}".format(coeff_multi_tool, intercept_multi_tool))

    # manuală (regresie multiplă)
    beta_manual = ols_multidimensional(X_multi, y)
    print("[Manual] Multi -> Intercept: {:.4f}, Coef GDP: {:.4f}, Coef Freedom: {:.4f}".format(
        beta_manual[0], beta_manual[1], beta_manual[2]))

    y_pred_multi_tool = model_multi.predict(X_multi)
    plt.figure(figsize=(12, 5))
    plt.subplot(1, 2, 1)
    plt.scatter(df["Economy..GDP.per.Capita."], y, color="blue", label="Date reale")
    plt.scatter(df["Economy..GDP.per.Capita."], y_pred_multi_tool, color="red", marker="x", label="Predicții (Tool)")
    plt.xlabel("Economy..GDP.per.Capita.")
    plt.ylabel("Happiness.Score")
    plt.title(f"{file_label}: GDP vs Happiness (Multi)")
    plt.legend()

    plt.subplot(1, 2, 2)
    plt.scatter(df["Freedom"], y, color="blue", label="Date reale")
    plt.scatter(df["Freedom"], y_pred_multi_tool, color="red", marker="x", label="Predicții (Tool)")
    plt.xlabel("Freedom")
    plt.ylabel("Happiness.Score")
    plt.title(f"{file_label}: Freedom vs Happiness (Multi)")
    plt.legend()
    plt.tight_layout()
    plt.show()


files = [
    ("v1_world-happiness-report-2017.csv", "v1"),
    ("v2_world-happiness-report-2017.csv", "v2"),
    ("v3_world-happiness-report-2017.csv", "v3")
]

for file_path, label in files:
    process_file(file_path, label)
