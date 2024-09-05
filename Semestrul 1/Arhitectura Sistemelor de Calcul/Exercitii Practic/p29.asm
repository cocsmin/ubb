bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se citesc de la tastatura trei numere a, m si n (a: word, 0 <= m, n <= 15, m > n). Sa se izoleze bitii de la m-n ai lui a si sa se afiseze numarul intreg reprezentat de acesti bitii in baza 10.
    a dw 0
    m db 0
    n db 0
    mesaj_a db "a=", 0
    mesaj_m db "m=", 0
    mesaj_n db "n=", 0
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
        
        push mesaj_m
        call [printf]
        add esp, 4
        
        push m
        push format
        call [scanf]
        add esp, 4*2
        
        push mesaj_n
        call [printf]
        add esp, 4
        
        push n
        push format
        call [scanf]
        add esp, 4*2
        
        mov AX, [a]
        mov CL, [m]
        sub CL, [n]
        shl AX, CL
        
        cwde
        push EAX
        push format
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
