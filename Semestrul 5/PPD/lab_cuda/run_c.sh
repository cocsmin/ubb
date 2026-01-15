#!/usr/bin/env bash

set -euo pipefail

if [ "$#" -lt 6 ]; then
  echo "Usage: $0 <exe_path> <input_file> <mode> <threads> <alloc> <runs>"
  exit 1
fi

EXE="$1"
INPUT="$2"
MODE="$3"
THREADS="$4"
ALLOC="$5"
RUNS="$6"

CSV="outC.csv"
if [ ! -f "$CSV" ]; then
  echo "Tip Matrice,Tip alocare,Nr threads,Timp executie" > "$CSV"
fi

header="$(head -n 1 "$INPUT" | tr -d '\r' | sed 's/[^[:print:]]//g')"
read -r N M K <<< "$header"
if [ -z "${N:-}" ] || [ -z "${M:-}" ] || [ -z "${K:-}" ]; then
  MATRIX_TYPE=""
else
  MATRIX_TYPE="N=${N} M=${M} n=${K} m=${K}"
fi

sum=0
count=0

for ((i=1;i<=RUNS;i++)); do
  echo "Run $i / $RUNS ..."
  TMP_OUT="out_tmp.txt"
  set +e
  "$EXE" "$INPUT" "$TMP_OUT" "$MODE" "$THREADS" "$ALLOC" > program_stdout.txt 2>&1
  rc=$?
  set -e
  cat program_stdout.txt
  time_val="$(grep -Eo 'compute_ms=[0-9]+(\.[0-9]+)?' program_stdout.txt | tail -n1 | sed 's/.*=//')"
  if [ -z "$time_val" ]; then
    time_val="$(grep -Eo 'total_ms=[0-9]+(\.[0-9]+)?' program_stdout.txt | tail -n1 | sed 's/.*=//')"
  fi
  if [ -z "$time_val" ]; then
    time_val="$(tr -s '[:space:]' '\n' < program_stdout.txt | grep -E '^[0-9]+(\.[0-9]+)?$' | tail -n1)"
  fi
  if [ -z "$time_val" ]; then
    echo "Warning: could not parse time from program output. Using 0."
    time_val="0"
  fi
  printf "Parsed time: %s ms\n" "$time_val"
  sum=$(awk -v a="$sum" -v b="$time_val" 'BEGIN{printf "%.9f", a + b}')
  count=$((count+1))
done

avg=$(awk -v s="$sum" -v n="$count" 'BEGIN{ if(n>0) printf "%.6f", s/n; else print "0"}')
echo "Average compute time (ms): $avg"

echo "\"$MATRIX_TYPE\",$ALLOC,$THREADS,$avg" >> "$CSV"
echo "Appended to $CSV"
