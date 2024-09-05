bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf            ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Sa se citeasca de la tastatura un numar hexazecimal format din 2 cifre. Sa se afiseze pe ecran acest numar in baza 10, interpretat atat ca numar fara semn cat si ca numar cu semn (pe 8 biti).
    numar dd 0
    x dd 0
    y dd 0
    mesaj db "Introduceti numarul: ", 0
    hexa db "%x", 0
    deca db "%d", 0
    afisare db "Fara semn: %d // Cu semn: %d ", 0
    

; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mesaj
        call [printf]
        add esp, 4
        
        push numar
        push hexa
        call [scanf]
        add esp, 4*2
        
        mov EAX, [numar]
        mov [y], EAX
        sub EAX, 256 ; pt stim ca e pe 8 biti
        mov [x], EAX
        
        
        push dword[x]
        push dword[y]
        push afisare
        call [printf]
        add esp, 4*3
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
