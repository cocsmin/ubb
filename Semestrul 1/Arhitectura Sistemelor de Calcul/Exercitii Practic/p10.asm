bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Sa se citeasca de la tastatura un numar in baza 10 si sa se afiseze valoarea acelui numar in baza 16.
    ;Exemplu: Se citeste: 28; se afiseaza: 1C
    numar dd 0
    format db "%d", 0
    hexa db "%x", 0
    msj db "Cititi numarul: ", 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push msj
        call [printf]
        add esp, 4
        
        push numar
        push format
        call [scanf]
        add esp, 4*2
        
        push dword[numar]
        push hexa
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
