bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fprintf, fopen, fclose, scanf, printf         ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se da un nume de fisier (definit in segmentul de date). Sa se creeze un fisier cu numele dat, apoi sa se citeasca de la tastatura cuvinte si sa se scrie in fisier cuvintele citite pana cand se citeste de la tastatura caracterul '$'.
    nume_fisier db "date11.txt", 0
    mod_acces db "w+", 0
    cuvant times 100 db 0
    mesaj db "Introduceti cuvantul: ", 0
    format db "%s", 0
    descriptor dd -1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        mov [descriptor], EAX
        cmp EAX, 0
        je final
        citeste:
            push mesaj
            call [printf]
            add esp, 4
            
            push cuvant
            push format
            call [scanf]
            add esp, 4*2
            
            cmp byte[cuvant], '$'
            je afara
            
            push cuvant
            push dword[descriptor]
            call [fprintf]
            add esp, 4*2
            jmp citeste
        
        afara:
        push dword[descriptor]
        call [fclose]
        
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
