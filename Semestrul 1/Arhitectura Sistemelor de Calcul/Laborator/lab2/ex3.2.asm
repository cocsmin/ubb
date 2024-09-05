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
    a db 1
    b db 2
    c db 3
    d db 4
    e dw 5
    f dw 6
    g dw 7
    h dw 8
;(f*g-a*b*e)/(h+c*d)

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AX, [f]
        mov DX, [g]
        mul DX
        ;mov EBX, DX:AX
       ; mov AX, 0
       ; mov AL, [a]
       ; mov AH, [b]
       ; mul AH
       ; mov DX, [e]
       ; mul DX
       ; sub EBX, DX : AX
       ; mov AX, 0
       ; mov AL, [c]
       ; mov AH, [d]
       ; mul AH
       ; mov CX, [h]
       ; add CX, AX
       ; mov DX : AX, EBX
       ; div CX
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
