bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf             ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se citesc de la tastatura numere (in baza 10) pana cand se introduce cifra 0. Determinaţi şi afişaţi cel mai mic număr dintre cele citite.
    x dd 0
    minim dd 10000000
    mesaj db "Introduceti numarul: ", 10, 13, 0
    format db "%d", 0
    afisare db "Minimul este %d", 0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        citeste:
            push mesaj
            call [printf]
            add esp, 4
            
            push x
            push format
            call [scanf]
            add esp, 4*2
            
            mov EAX, [x]
            cmp [minim], EAX
            jl next
            mov [minim], EAX
            next
            cmp dword[x], 0
            jne citeste
            
        push dword[minim]
        push afisare
        call [printf]
        add esp, 4*2
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
