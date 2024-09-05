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
    ; (c-a) + (b-d) + d FARA SEMN
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov EAX, [c] ; EAX = c 
        
        ; convertesc a la dw
        mov EBX, 0
        mov BL, [a] ; EBX = a
        sub EAX, EBX ; EAX = (c-a)
        
        ; conertesc b la qw
        mov EBX, 0
        mov ECX, 0
        mov BX, [b]
        
        sub EBX, dword[d] 
        sbb ECX, dword[d+4] ; ECX:EBX = (b-d)
        
        ; convertesc (c-a) la qw
        mov EDX, 0 ; EDX:EAX = (c-a)
        add EAX, EBX
        adc EDX, ECX ; EDX:EAX = (c-a) + (b-d)
        
        add EAX, dword[d]
        adc EDX, dword[d+4] ; EDX:EAX = (c-a) + (b-d) + d
        
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
