bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Sa se citeasca de la tastatura doua numere a si b de tip word. Sa se afiseze in baza 16 numarul c de tip dword pentru care partea low este suma celor doua numere, iar partea high este diferenta celor doua numere. Exemplu:
    ;a = 574, b = 136
    ;c = 01B602C6h
    a dw 0
    b dw 0
    c dd 0
    mesaj_a db "a=", 0
    mesaj_b db "b=", 0
    deca db "%d", 0
    hexa db "c=%x", 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mesaj_a
        call [printf]
        add esp, 4
        
        push a
        push deca
        call [scanf]
        add esp, 4*2
        
        push mesaj_b
        call [printf]
        add esp, 4
        
        push b
        push deca
        call [scanf]
        add esp, 4*2
        
        mov AX, [a]
        add AX, [b]
        
        mov BX, [a]
        sub BX, [b]
        
        mov [c], AX
        mov [c+2], BX
        
        push dword[c]
        push hexa
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
