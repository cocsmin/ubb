bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fscanf, fopen, fclose, printf, fread              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fscanf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
import fread msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de vocale si sa se afiseze aceasta valoare. Numele fisierului text este definit in segmentul de date.
    id dd -1
    format db "%d", 0
    nr_vocale dd 0
    nume_fisier db "date1.txt", 0
    mod_acces db "r", 0
    vocale db 'a','e','i','o','u', 0
    len equ 100
    text times len db 0
; our code starts here
segment code use32 class=code
    f1:
        mov EDI, text
        mov ESI, 0
        mov ECX, EAX ;numarul de caractere citite din fisier
        mov EDX, 0
        repeta:
            lodsb
            push ECX
            mov ECX, 5
            mov ESI, 0
            repeta2:
                cmp AL, [vocale+ESI]
                jne next
                inc EDX
                next:
                inc ESI
                loop repeta2
            pop ECX
            loop repeta
            
        ret
    start:
        ; ...
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        mov [id], EAX
        cmp EAX, 0
        je final
        
        push dword[id]
        push dword len
        push dword 1
        push dword text
        call [fread]
        add esp, 4*4
        
        call f1
        
        mov [nr_vocale], EDX
        push dword[nr_vocale]
        push format
        call [printf]
        add esp, 4*2
        
        push dword[id]
        call [fclose]
        add esp, 4
        
        
            
        
        
        
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
