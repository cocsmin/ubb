# run_tests.ps1
param(
    [string]$ExePath = ".\convolution_cuda.exe",
    [string]$InputFile = "date.txt",
    [int]$Runs = 10,
    [string]$CsvFile = "rezultate_cuda.csv"
)

# 1. Citim dimensiunile N, M, K din prima linie a fisierului de intrare
if (-not (Test-Path $InputFile)) {
    Write-Error "Fisierul de intrare $InputFile nu exista!"
    exit 1
}

$header = Get-Content $InputFile -TotalCount 1
$dims = $header -split '\s+' | Where-Object { $_ -ne "" }
$N = $dims[0]
$M = $dims[1]
$K = $dims[2]
$MatrixType = "N=$N M=$M n=$K m=$K"

Write-Host "--- Incepem testarea pentru: $MatrixType ($Runs rulari) ---" -ForegroundColor Cyan

# 2. Pregatim CSV-ul (Daca nu exista, scriem header-ul conform cerintei)
if (-not (Test-Path $CsvFile)) {
    # Formatul cerut: Tip matrice, Tip alocare, Nr threads, Timp executie
    Add-Content -Path $CsvFile -Value "Tip Matrice,Tip alocare,Nr threads,Timp executie"
}

$totalTime = 0
$validRuns = 0

# 3. Bucla de executie
for ($i = 1; $i -le $Runs; $i++) {
    Write-Host -NoNewline "Run $i / $Runs ... "
    
    # Rulam executabilul si capturam tot output-ul
    # Folosim un fisier temporar de output ca sa nu incarcam discul inutil, dar programul cere un argument
    $dummyOut = "temp_ignore.txt"
    
    # Masuram timpul extern (optional) dar ne bazam pe ce zice CUDA
    $output = & $ExePath $InputFile $dummyOut 2>&1
    
    # Cautam linia cu "Timpi executie Kernel GPU: X ms"
    # Regex-ul cauta numarul (cu punct zecimal)
    $timeString = $null
    foreach ($line in $output) {
        if ($line -match "Timpi executie Kernel GPU:\s+([0-9.]+)") {
            $timeString = $Matches[1]
            break
        }
    }

    if ($timeString) {
        # Convertim string-ul in numar (folosim InvariantCulture pt a trata punctul ca zecimala)
        $timeVal = [double]::Parse($timeString, [System.Globalization.CultureInfo]::InvariantCulture)
        $totalTime += $timeVal
        $validRuns++
        Write-Host "$timeVal ms" -ForegroundColor Green
    } else {
        Write-Host "Eroare la parsare output!" -ForegroundColor Red
        Write-Host $output
    }
}

# 4. Calculam media si salvam in CSV
if ($validRuns -gt 0) {
    $avg = $totalTime / $validRuns
    # Formatam media la 6 zecimale
    $avgStr = "{0:N6}" -f $avg
    
    # In CUDA alocarea e mereu "Dynamic" (cudaMalloc) si nr threads e "GPU"
    $csvLine = "$MatrixType,Dynamic (CUDA),GPU,$avgStr"
    Add-Content -Path $CsvFile -Value $csvLine
    
    Write-Host "`n------------------------------------------------"
    Write-Host "Finalizat. Medie: $avgStr ms" -ForegroundColor Yellow
    Write-Host "Rezultat salvat in $CsvFile"
    Write-Host "------------------------------------------------`n"
}