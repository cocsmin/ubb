bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fprintf, fread, fopen, fclose,fscanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fprintf msvcrt.dll
import fread msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fscanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se da un fisier text. Fisierul contine numere (în baza 10) separate prin spatii. Sa se citeasca continutul acestui fisier, sa se determine maximul numerelor citite si sa se scrie rezultatul la sfarsitul fisierului.
    nume_fisier db "date29.txt", 0
    mod_citire db "r", 0
    mod_adaugare db "a", 0
    len equ 100
    format db "%d", 0
    numar dd 0
    maxim dd 0
    id_citire dd -1
    id_adaugare dd -1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mod_citire
        push nume_fisier
        call [fopen]
        
        mov [id_citire], EAX
        cmp EAX, 0
        je final
        
        mov EBX, 0
        mov ECX, 100
        cauta:
            push numar
            push format
            push dword[id_citire]
            call [fscanf]
            add esp, 4*3
            mov EAX, [numar]
            cmp [maxim], EAX
            jg next
            mov [maxim], EAX
            next:
        loop cauta
            
        push dword[id_citire]
        call [fclose]
        add esp, 4
        
        push mod_adaugare
        push nume_fisier
        call [fopen]
        add esp, 4*2
        
        mov [id_adaugare], EAX
        cmp EAX, 0
        je final
        
        push dword[maxim]
        push format
        push dword[id_adaugare]
        call [fprintf]
        add esp, 4*3
        
        push dword[id_adaugare]
        call [fclose]
        add esp, 4
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
