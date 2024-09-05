bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se citesc de la tastatura doua numere a si b. Sa se calculeze valoarea expresiei (a+b)*k, k fiind o constanta definita in segmentul de date. Afisati valoarea expresiei (in baza 10).
    a dw 0
    b dw 0
    k equ 5
    mesaj_a db "a=", 0
    mesaj_b db "b=", 0
    format db "%d", 0
   
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mesaj_a
        call [printf]
        add esp, 4
        
        push a
        push format
        call [scanf]
        add esp, 4*2
        
        push mesaj_b
        call [printf]
        add esp, 4
        
        push b
        push format
        call [scanf]
        add esp, 4*2
        
        mov AX, [a]
        add AX, [b]
        
        mov BX, k
        imul BX
        
        push DX
        push AX
        push format
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
