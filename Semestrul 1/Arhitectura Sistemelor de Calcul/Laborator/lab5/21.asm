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
    A db 2, 1, -3, 3, -4, 2, 6 ; sirul A
    la equ $-A
    B db 4, 5, 7, 6, 2, 1 ; sirul B
    lb equ $-B
    L equ la + lb
    R times L db 0 
    ;R contine elementele lui B in ordine inversa urmate de elementele negative ale lui A
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ECX, lb
        mov ESI, lb - 1
        mov EDI, 0
        jecxz Incrementare
        Repeta1:
            mov AL, [B + ESI]
            mov [R + EDI], AL
            dec ESI
            inc EDI
        loop Repeta1
        
        Incrementare:
            mov ECX, la
            mov ESI, 0
            jecxz Sfarsit
            
        
        Repeta2:
            mov AL, [A + ESI]
            cmp AL, 0
            jg Pozitiv
            mov [R + EDI], AL
            inc EDI
            Pozitiv:
            inc ESI
        loop Repeta2
            
       
    
        Sfarsit:
            ; exit(0)
            push    dword 0      ; push the parameter for exit onto the stack
            call    [exit]       ; call exit to terminate the program
