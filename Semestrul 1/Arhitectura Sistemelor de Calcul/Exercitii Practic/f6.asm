bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fread, printf, fopen, fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fread msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se da un fisier text. Sa se citeasca continutul fisierului, sa se determine cifra cu cea mai mare frecventa si sa se afiseze acea cifra impreuna cu frecventa acesteia. Numele fisierului text este definit in segmentul de date.
    mod_acces db "r", 0
    nume_fisier db "date6.txt"
    frecventa times 10 db 0
    len equ 100
    text times len db 0
    descriptor dd -1
    afisare db "Cifra e %d cu frecventa maxima %d"
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
        
        push dword [descriptor]
        push dword len
        push dword 1
        push dword text
        call [fread]
        add esp, 4*4
        
        mov ESI, text
        mov ECX, EAX
        repeta:
            mov EAX, 0
            lodsb
            sub AL, '0'
            inc byte[frecventa + EAX]
            loop repeta
        mov EBX, 0
        mov ECX, 10
        mov EDI, 0
        mov EAX, 0
        maxim:
            cmp byte[frecventa + EDI], AL
            jle nu
            mov AL, [frecventa + EDI]
            mov EBX, EDI
            nu:
                inc EDI
                loop maxim
                
        cbw
        cwde
        push EAX
        push EBX
        push afisare
        call [printf]
        add esp, 4*3
        
        push dword[descriptor]
        call [fclose]
        add esp, 4
        


        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
