bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf, fprintf, fopen, fclose, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura cuvinte pana cand se citeste de la tastatura caracterul '$'. Sa se scrie in fisier doar cuvintele care contin cel putin o litera mare (uppercase).
    nume_fisier db "date26.txt", 0
    mod_acces db "w+", 0
    cuvant times 100 db 0
    format db "%s", 0
    rezultat times 100 db 0
    mesaj db "Introduceti textul: ", 0
    descriptor dd -1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mod_acces
        push nume_fisier
        call [fopen]
        mov [descriptor], EAX
        cmp EAX, 0
        je final
        citeste:
            push mesaj
            call [printf]
            push cuvant
            push format
            call [scanf]
            mov ESI, cuvant
            mov DH, 0
            verifica:
                lodsb
                cmp AL, '$'
                je gata
                cmp AL, 0
                je urm
                cmp AL, 'A'
                jl next
                cmp AL, 'Z'
                jg next
                mov DH, 1
                next:
                jmp verifica
            urm:
            cmp DH, 0
            je urm_cuv
            push cuvant
            push dword[descriptor]
            call [fprintf]
            add esp, 4*2
            urm_cuv:
            jmp citeste
            
        gata:
        
        push dword[descriptor]
        call [fclose]
        add esp, 4
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
