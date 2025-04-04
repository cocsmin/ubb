bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Dandu-se un sir de cuvinte sa se obtina sirul (de octeti) cifrelor in baza zece ale fiecarui cuvant din acest sir.
    sir dw 12345, 20778, 45967
    ; cifre = 1, 2, 3, 4, 5, 2, 0, 7, 7, 8, 4, 5, 9, 6, 7
    L equ ($-sir)/2
    cf dw 10000
    zece db 10
    aux dw 0
    cifre times L*L db 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ESI, sir
        mov EDI, cifre
        mov ECX, L
        conversie:
            jecxz final
            lodsw 
            mov BX, 5
            cifra:
                mov DX, 0
                div word[cf]
                stosw
                mov AX, word[cf]
                mul byte[zece]
                mov word[cf], AX
                mov AX, DX
                dec BX
                cmp BX, 0
                jne cifra
            sfarsit:
                
        loop conversie
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
