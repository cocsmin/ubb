bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fclose, fprintf, fread              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fread msvcrt.dll   ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    text db "3 mere 2 pere 3215g de faina", 0
    lungime equ $-text-1
    nume db "rezultat.txt", 0
    acces db "w", 0
    id dd -1
    rezultat times lungime+1 db 0
; our code starts here
segment code use32 class=code
    start:
        ;Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, cifre si spatii. Sa se inlocuiasca toate cifrele de pe pozitii impare cu caracterul ‘X’. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.
        ; ...
        cld
        mov ESI, text
        mov EDI, rezultat
        mov ECX, lungime
        mov BH, 0
        repeta:
            lodsb
            test BH, 1
            jz next
            cmp AL, '0'
            jb next
            cmp AL, '9'
            ja next
            mov AL, 'X'
            
            next:
            stosb
            add BH, 1
        loop repeta
        
        mov AL, 0
        stosb ; ca sa pot pune in fisier
        
        
        push dword acces
        push dword nume
        call [fopen]
        add ESP, 4*2
        
        mov [id], EAX
        cmp EAX, 0
        je final
        
        push dword rezultat
        push dword [id]
        call [fprintf]
        add ESP, 4*2
        
        push dword [id]
        call [fclose]
        add esp, 4*1
        
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
