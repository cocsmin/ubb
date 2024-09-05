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
    ; (f*g - a*b*e)/(h+c*d)
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AX, [f] ; AX = 6
        mov DX, [g] ; DX = 7
        mul DX ; DX:AX = 42
        push DX
        push AX
        pop EAX ; EAX = 42
        
        
        mov EBX, EAX ; EBX = (f*g) = 42
        mov EAX, 0 ; EAX = 0
        mov AL, [a] ; AL = 1
        mov AH, [b] ; AH = 2
        mul AH ; AX = 2
        mov DX, [e] ; DX = 5
        mul DX ; DX:AX = 10
        push DX
        push AX
        pop EAX ; EAX = (a*b*e) = 10
        sub EBX, EAX ; EBX = (f*g - a*b*e) = 32
        mov EAX, 0 ; EAX = 0
        mov AL, [c] ; AL = 3
        mov AH, [d] ; AH = 4
        mul AH ; AX = 12
        mov CX, [h] ; CX = 8
        add CX, AX ; CX = (h + c*d) = 20
        mov EAX, EBX ; EAX = (f*g - a*b*e) = 32
        div CX ; EAX = 32/20 = 1, rest C
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
