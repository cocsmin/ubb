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
   
    ; (a*a/b+b*b)/(2+b)+e-x CU SEMN

; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AL, [a]
        imul byte[a] ; AX = a*a
        mov BX, AX ; BX = a*a
        
        mov AX, [b]
        imul word[b] ; DX:AX = b*b
        
        push DX
        push AX
        pop ECX ; ECX = b*b
        
        mov AX, [b]
        cwde
        add ECX, EAX ; ECX = b+b*b
        
        mov AX, BX
        cwde
        cdq ; EDX:EAX = a*a
        idiv ECX ; EAX = EDX:EAX / ECX ; EDX = EDX:EAX % ECX
        
        push EAX
        pop AX
        pop DX ; DX:AX = (a*a/b+b*b)
        mov CX, [b]
        add CX, 2
        idiv CX ; AX = (a*a/b+b*b) / (2+b)
        
        cwde ; EAX = (a*a/b+b*b) / (2+b)
        add EAX, [e]
        cdq ; EDX:EAX = (a*a/b+b*b) / (2+b) + e
        sub EAX, dword[x]
        sbb EDX, dword[x+4]
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
