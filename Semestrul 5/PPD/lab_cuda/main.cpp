#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>

using namespace std;

// Declarăm funcția care vine din CUDA
extern "C" void run_gpu_wrapper(const long long* h_F, const long long* h_C, long long* h_V, int N, int M, int K, float* duration_ms);

// Funcție citire (identică cu lab 1)
bool read_input_tokens(const string &infile, int &N, int &M, int &K, vector<long long> &tokens) {
    ifstream fin(infile, ios::binary);
    if (!fin.is_open()) return false;
    // Citim tot fișierul în memorie rapid
    string content((istreambuf_iterator<char>(fin)), istreambuf_iterator<char>());
    fin.close();
    // Eliminăm BOM dacă există (pentru fișiere salvate din Notepad)
    if (content.size() >= 3 && (unsigned char)content[0] == 0xEF && (unsigned char)content[1] == 0xBB && (unsigned char)content[2] == 0xBF)
        content = content.substr(3);
    stringstream ss(content);
    long long x;
    while (ss >> x) tokens.push_back(x);
    if (tokens.size() < 3) return false;
    N = (int)tokens[0]; M = (int)tokens[1]; K = (int)tokens[2];
    return true;
}

// Funcție scriere
bool write_output_matrix(const string &outfile, int N, int M, const vector<long long>& V) {
    ofstream fout(outfile);
    if (!fout.is_open()) return false;
    fout << N << " " << M << "\n";
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            fout << V[i * M + j];
            if (j + 1 < M) fout << " ";
        }
        fout << "\n";
    }
    fout.close();
    return true;
}

int main(int argc, char** argv) {
    // Verificăm argumentele
    if (argc < 3) {
        cerr << "Utilizare: " << argv[0] << " <input_file> <output_file>\n";
        return 1;
    }
    string infile = argv[1];
    string outfile = argv[2];

    cout << "Citire date din: " << infile << " ..." << endl;

    int N, M, K;
    vector<long long> tokens;
    if (!read_input_tokens(infile, N, M, K, tokens)) {
        cerr << "Eroare: Nu s-a putut citi fisierul sau format invalid.\n";
        return 2;
    }

    // Alocare Host (vectori 1D liniarizați)
    vector<long long> h_F(N * M);
    vector<long long> h_C(K * K);
    vector<long long> h_V(N * M);

    // Populăm vectorii din tokeni
    size_t idx = 3;
    for (int i = 0; i < N * M; ++i) h_F[i] = tokens[idx++];
    for (int i = 0; i < K * K; ++i) h_C[i] = tokens[idx++];

    cout << "Matrice " << N << "x" << M << ", Filtru " << K << "x" << K << endl;
    cout << "Rulare CUDA..." << endl;

    float duration = 0;
    // Apelăm funcția CUDA
    run_gpu_wrapper(h_F.data(), h_C.data(), h_V.data(), N, M, K, &duration);

    cout << "------------------------------------------------" << endl;
    cout << "Timpi executie Kernel GPU: " << duration << " ms" << endl;
    cout << "------------------------------------------------" << endl;

    cout << "Scriere rezultat in: " << outfile << " ..." << endl;
    write_output_matrix(outfile, N, M, h_V);
    
    cout << "Done." << endl;
    return 0;
}