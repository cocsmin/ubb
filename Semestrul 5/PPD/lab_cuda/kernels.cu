#include <cuda_runtime.h>
#include <stdio.h>
#include <stdlib.h>

// Macro pentru verificarea erorilor CUDA (esențial pentru debugging)
#define CHECK_CUDA(call) { \
    cudaError_t err = call; \
    if (err != cudaSuccess) { \
        fprintf(stderr, "CUDA Error: %s at line %d\n", cudaGetErrorString(err), __LINE__); \
        exit(1); \
    } \
}

/*
 * KERNEL-UL CUDA
 * Fiecare thread procesează un singur pixel din matricea rezultat V.
 * Implementează logica de "Clamping" (bordare virtuală) cerută în laborator.
 */
__global__ void convolution_kernel(const long long* F, const long long* C, long long* V, int N, int M, int K) {
    // Calculăm coordonatele globale ale pixelului (i, j)
    int j = blockIdx.x * blockDim.x + threadIdx.x; // Coloana
    int i = blockIdx.y * blockDim.y + threadIdx.y; // Linia

    // Verificăm dacă suntem în interiorul matricii (pentru a nu accesa memorie invalidă)
    if (i < N && j < M) {
        int half = K / 2;
        long long sum = 0;

        // Parcurgem elementele filtrului de convoluție
        for (int u = 0; u < K; ++u) {
            for (int v = 0; v < K; ++v) {
                // Coordonatele vecinului din matricea F
                int fi = i + (u - half);
                int fj = j + (v - half);

                // CERINTA LAB 1: Bordare virtuală (Clamping)
                // Dacă indexul iese din matrice, îl tragem înapoi la margine
                if (fi < 0) fi = 0;
                if (fj < 0) fj = 0;
                if (fi >= N) fi = N - 1;
                if (fj >= M) fj = M - 1;

                // Calculăm produsul și adunăm (matricea este liniarizată)
                sum += F[fi * M + fj] * C[u * K + v];
            }
        }
        // Scriem rezultatul final
        V[i * M + j] = sum;
    }
}

/*
 * Funcție Wrapper (interfața dintre C++ și CUDA)
 * Se ocupă de alocarea memoriei pe GPU și lansarea kernel-ului.
 */
extern "C" void run_gpu_wrapper(const long long* h_F, const long long* h_C, long long* h_V, int N, int M, int K, float* duration_ms) {
    size_t sizeF = (size_t)N * M * sizeof(long long);
    size_t sizeC = (size_t)K * K * sizeof(long long);
    
    long long *d_F, *d_C, *d_V;

    // 1. Alocare Memorie pe Device (GPU)
    CHECK_CUDA(cudaMalloc((void**)&d_F, sizeF));
    CHECK_CUDA(cudaMalloc((void**)&d_C, sizeC));
    CHECK_CUDA(cudaMalloc((void**)&d_V, sizeF));

    // 2. Copiere Date Host -> Device
    CHECK_CUDA(cudaMemcpy(d_F, h_F, sizeF, cudaMemcpyHostToDevice));
    CHECK_CUDA(cudaMemcpy(d_C, h_C, sizeC, cudaMemcpyHostToDevice));

    // 3. Configurare Arhitectură Thread-uri
    // Folosim blocuri de 16x16 thread-uri (standard pentru imagini)
    dim3 threadsPerBlock(16, 16);
    // Grid-ul trebuie să acopere toată matricea NxM
    dim3 numBlocks((M + threadsPerBlock.x - 1) / threadsPerBlock.x, 
                   (N + threadsPerBlock.y - 1) / threadsPerBlock.y);

    // Evenimente pentru măsurarea timpului (mult mai precis decât clock() din C++)
    cudaEvent_t start, stop;
    CHECK_CUDA(cudaEventCreate(&start)); 
    CHECK_CUDA(cudaEventCreate(&stop));
    
    // -- START MĂSURĂTOARE --
    CHECK_CUDA(cudaEventRecord(start));
    
    // Lansare Kernel
    convolution_kernel<<<numBlocks, threadsPerBlock>>>(d_F, d_C, d_V, N, M, K);
    
    // -- STOP MĂSURĂTOARE --
    CHECK_CUDA(cudaEventRecord(stop));
    
    // Sincronizare (așteptăm să termine GPU-ul)
    CHECK_CUDA(cudaEventSynchronize(stop));
    CHECK_CUDA(cudaEventElapsedTime(duration_ms, start, stop));

    // Verificăm dacă a crăpat kernel-ul
    CHECK_CUDA(cudaGetLastError());

    // 4. Copiere Rezultat Device -> Host
    CHECK_CUDA(cudaMemcpy(h_V, d_V, sizeF, cudaMemcpyDeviceToHost));

    // 5. Eliberare Memorie
    CHECK_CUDA(cudaFree(d_F)); 
    CHECK_CUDA(cudaFree(d_C)); 
    CHECK_CUDA(cudaFree(d_V));
    CHECK_CUDA(cudaEventDestroy(start));
    CHECK_CUDA(cudaEventDestroy(stop));
}