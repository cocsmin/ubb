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
    sir dw 12345, 20778, 4596
    ; cifre = 1, 2, 3, 4, 5, 2, 0, 7, 7, 8, 4, 5, 9, 6
    L equ ($-sir)/2
    zece dw 10
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
            mov BL, 5 ; nr maxim de cifre
            mov BH, 0 ; nr real de cifre ale numarului
            ;mov [lung], BX
            cifra:
                mov DX, 0
                div word[zece]
                push DX
                inc BH
                cmp AX, 0
                je sari
                dec BL
                cmp BL, 0
                jne cifra
            sari:    
            ;mov BX, 5
            baga:
                pop AX
                stosb
                dec BH
                cmp BH, 0
                jne baga
        
        loop conversie
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
