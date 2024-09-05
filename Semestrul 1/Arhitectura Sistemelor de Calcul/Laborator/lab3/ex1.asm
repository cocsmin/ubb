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
    a dd 1
    b db 2
    c dw 3
    d db 4
    e dq 5
    ; a+b/c-d*2-e 
; our code starts here
segment code use32 class=code
    start:
        ; ...
    ; FARA SEMN
        mov AL, [b]
        mov AH, 0 ; AX = b
        mov DX, 0 ; DX:AX = b 
        div word [c] ; AX = b/c DX = b%c 
        mov BX, AX ; BX = b/c
        mov AL, 2 ; AL = 2
        mul byte [d] ; AX = d*2
        sub BX, AX ; BX = b/c - d*2
        mov CX, 0 ; CX:BX = b/c -d*2
        ; adunam parte cu parte, partea low cu partea low si partea high cu partea high
        
        add BX, word [a]
        adc CX, word [a+2] ; CX:BX = a+b/c-d*2 
        
        push CX
        push BX
        pop EAX
        mov EDX, 0 ; EDX:EAX = a+b/c-d*2
        
        sub EAX, dword[e]
        sbb EDX, dword[e+4]
        
    ; CU SEMN
        mov AL, [b]
        cbw
        cwd
        idiv word [c] ; AX = b/c DX = b%c 
        mov BX, AX ; BX = b/c
        mov AL, 2 ; AL = 2
        imul byte [d] ; AX = d*2
        mov AX, BX
        cwd ; DX:AX = b/c-d*2
        ; adunam parte cu parte, partea low cu partea low si partea high cu partea high
        
        add AX, word [a]
        adc DX, word [a+2] ; DX:AX = a+b/c-d*2 
        
        push DX
        push AX
        pop EAX
        cdq ; EDX:EAX = a+b/c-d*2
        
        sub EAX, dword[e]
        sbb EDX, dword[e+4]
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
