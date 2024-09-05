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
    a db 20
    b dw 15
    e dd 10
    x dq 5
    ; (a*a/b+b*b)/(2+b)+e-x FARA SEMN
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AL, [a]
        mul byte[a] ; AX = a*a
        mov BX, AX ; BX = a*a
        
        mov AX, [b] ; AX = b
        mul word[b] ; DX:AX = b*b
        
        push DX
        push AX
        pop EAX ; EAX = b*b
        
        mov ECX, 0
        mov CX, [b] ; ECX = b
        add ECX, EAX ; ECX = b+b*b
        
        mov EDX, 0
        mov EAX, 0
        mov AX, BX ; EDX:EAX = a*a
        div ECX ; EAX = EDX:EAX / ECX ; EDX = EDX:EAX % ECX
        
        mov BX, [b]
        add BX, 2 ; BX = b+2
        push EAX
        pop AX
        pop DX
        div BX ; AX = DX:AX / BX ; DX = DX:AX % BX
        
        mov BX, AX
        mov EAX, 0
        mov AX, BX
        add EAX, [e]
        mov EDX, 0
        sub EAX, dword[x]
        sbb EDX, dword[x+4]
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
