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
    ;Sa se citeasca de la tastatura un numar in baza 16 si sa se afiseze valoarea acelui numar in baza 10.
    ;Exemplu: Se citeste: 1D; se afiseaza: 29
    numar dd 0
    hexa db "%x", 0
    deca db "%d", 0
    msj db "Introduceti numarul: ", 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push msj
        call [printf]
        add esp, 4
        
        push numar
        push hexa
        call [scanf]
        add esp, 4*2
        
        push dword[numar]
        push deca
        call [printf]
        add esp,4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
