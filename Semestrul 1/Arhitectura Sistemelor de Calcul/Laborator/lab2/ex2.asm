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
    b db 6
    c db 7
    d db 8
    a1 dw 1
    b1 dw 2
    c1 dw 3
    d1 dw 4
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AL, [a]
        sub AL, [b]
        mov BL, [d]
        sub BL, [c]
        add AL, BL
        
        
        mov DL, [d1]
        add DL, 2
        mov CL, [a1]
        sub CL, [c1]
        add CL, [d1]
        sub CL, 7
        add CL, [b1]
        sub CL, DL
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
