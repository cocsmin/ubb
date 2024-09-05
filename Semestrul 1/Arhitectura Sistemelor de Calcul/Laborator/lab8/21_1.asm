bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
extern printf
extern scanf
import printf msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;Sa se citeasca de la tastatura doua numere a si b de tip word. Sa se afiseze in baza 16 numarul c de tip dword pentru care partea low este suma celor doua numere, iar partea high este diferenta celor doua numere. Exemplu:
    ;a = 574, b = 136
    ;c = 01B602C6h
    ; ...
    a dw 0
    b dw 0
    c dd 0
    citire_a db "a= ", 0
    citire_b db "b= ", 0
    afisare db "c= ", 0
    format db "%d", 0
    format2 db "%x", 0

; our code starts here
segment code use32 class=code
    start:
        push dword citire_a
        call [printf]
        add esp, 4*1
        push dword a
        push dword format
        call [scanf]
        add esp, 4*2
        
        push dword citire_b
        call [printf]
        add esp, 4*1
        push dword b
        push dword format
        call [scanf]
        add esp, 4*2
        
        mov AX, [a]
        add AX, [b] ; AX = a + b
        
        mov DX, [a]
        sub DX, [b] ; DX = a - b
        
        mov [c], AX
        mov [c+2], DX
        
        push dword [c+2]
        push dword [c]
        push dword format2
        call [printf]
        add esp, 4*3
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
