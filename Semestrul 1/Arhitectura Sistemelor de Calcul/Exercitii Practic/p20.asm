bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Sa se citeasca de la tastatura in baza 16 doua numere a si b de tip dword si sa se afiseze suma partilor low si diferenta partilor high. Exemplu:
    ;a = 00101A35h, b = 00023219h
    ;suma = 4C4Eh
    ;diferenta = Eh
    a dd 0
    b dd 0
    mesaj_a db "a=", 0
    mesaj_b db "b=", 0
    format db "%x", 0
    sum db "suma=%x", 10, 13, 0
    dif db "diferenta=%x", 0
    suma dd 0
    diferenta dd 0

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mesaj_a
        call [printf]
        add esp, 4
        
        push a
        push format
        call [scanf]
        add esp, 4*2
        
        push mesaj_b
        call [printf]
        add esp, 4
        
        push b
        push format
        call [scanf]
        add esp, 4*2
        
        mov AX, [a]
        mov DX, [a+2]
        mov BX, [b]
        mov CX, [b+2]
        add AX, BX
        sub DX, CX
        mov [suma], AX
        mov [diferenta], DX
        
        push dword[suma]
        push sum
        call [printf]
        add esp, 4*2
        
        push dword[diferenta]
        push dif
        call [printf]
        add esp, 4*2
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
