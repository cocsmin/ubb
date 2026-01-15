# generate_date.py
import sys, random
if len(sys.argv) < 5:
    print("Usage: python3 generate_date.py N M K maxval")
    sys.exit(1)
N = int(sys.argv[1]); M = int(sys.argv[2]); K = int(sys.argv[3]); maxv = int(sys.argv[4])
with open("date.txt", "w") as f:
    f.write(f"{N} {M} {K}\n")
    for i in range(N):
        f.write(" ".join(str(random.randint(0,maxv)) for _ in range(M)) + "\n")
    for i in range(K):
        f.write(" ".join(str(random.randint(-2,2)) for _ in range(K)) + "\n")
