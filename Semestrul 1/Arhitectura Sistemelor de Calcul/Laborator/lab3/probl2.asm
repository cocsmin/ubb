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
    a db 5
    b dw 10
    c dd 15
    d dq 20
    ; d-a + (b+a-c) CU SEMN
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AL, [a]
        cbw
        cwde
        cdq ; EDX:EAX = a
        mov EBX, dword[d]
        mov ECX, dword[d+4] ; ECX:EBX = d
        
        sub EBX, EAX
        sbb ECX, EDX ; ECX:EBX = d-a
        
        
        mov AL, [a]
        cbw ; AX = a
        mov DX, [b]
        add DX, AX ; DX = b+a
        
        mov AX, DX
        cwde ; EAX = b+a
        sub EAX, [c] ; EAX = (b+a-c)
        
        cdq
        add EAX, EBX
        adc EDX, ECX ; EDX:EAX = (d-a) + (b+a-c)
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
