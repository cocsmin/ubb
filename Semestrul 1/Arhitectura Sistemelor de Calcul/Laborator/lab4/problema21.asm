bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    a dw 0110011001100110b ; cuvantul a
    b dw 0011001100110011b ; cuvantul b
    c resw 2 ; cuvantul c care se cere
; our code starts here
segment code use32 class=code
    start:
        ; ...
        ;bitii 0-3 ai lui C coincid cu bitii 5-8 ai lui B
        
        mov EAX, 0
        mov AX, [b]
        and AX, 0000000111100000b ; AX = 0000000100100000
        shr AX, 5 ; AX = 0000000000001111
        or [c], EAX ;c = 0000000000000000 0000000000001001
        
        ;bitii 4-10 ai lui C sunt invers fata de bitii 0-6 ai lui B
        
        mov EAX, 0
        mov AX, [b]
        and AX, 0000000001111111b ; AX = 0000000000110011
        shl AX, 4 ; AX = 0000001100110000
        not AX ; AX = 11111 1001100 1111
        and AX, 0000011111110000b ; AX = 00000 1001100 0000
        or [c], EAX ; c = 0000000000000 00000000 10011001001
        
        ;bitii 11-18 ai lui C sunt 1
        
        mov EAX, 0
        or EAX, 11111111111111111111111111111111b
        and EAX, 00000000000001111111100000000000b ; EAX = 0000000000000 11111111 00000000000
        or [c], EAX ; c = 0000000000000 11111111 10011001001
        
        ;bitii 19-31 ai lui C coincid cu bitii 3-15 ai lui B
        
        mov EAX, 0
        mov AX, [b]
        and AX, 1111111111111000b ; AX = 0011001100110 000
        shl EAX, 16
        or [c], EAX ; c = 00110011001101111111110011001001
        mov EBX, [c]
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
