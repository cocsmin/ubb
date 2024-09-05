bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fprintf, fopen, fclose, printf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    
    ;Se da numele unui fisier si un numar pe cuvant scris in memorie. Se considera numarul in reprezentarea fara semn. Sa se scrie cifrele zecimale ale acestui numar ca text in fisier, fiecare cifra pe o linie separata.

    nume_fisier db "date22.txt", 0
    mod_acces db "w+", 0
    numar_binar db "1111111111111111", 0
    numar_zecimal db 0
    descriptor dd -1
    baza equ 2
    format db "%d", 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ESI, numar_binar
        mov ECX, 15
        mov EDX, 0
        repeta:
            mov EAX, 0
            lodsb
            cmp AL, 0
            je next
            mov EBX, ECX
            mov AH, baza
            putere:
                mul AH
                loop putere
            add DX, AX
            mov ECX, EBX
            next:
            loop repeta
        
        mov [numar_zecimal], DX
        push dword[numar_zecimal]
        push format
        call [printf]
        add esp, 4*2
        
        ; push mod_acces
        ; push nume_fisier
        ; call [fopen]
        ; add esp, 4*2
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
