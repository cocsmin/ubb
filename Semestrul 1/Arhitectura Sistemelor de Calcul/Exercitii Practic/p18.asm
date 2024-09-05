bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import scanf msvcrt.dll
import printf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Sa se citeasca de la tastatura un numar in baza 10 si un numar in baza 16. Sa se afiseze in baza 10 numarul de biti 1 ai sumei celor doua numere citite. Exemplu:
    ;a = 32 = 0010 0000b
    ;b = 1Ah = 0001 1010b
    ;32 + 1Ah = 0011 1010b
    ;Se va afisa pe ecran valoarea 4.
    a dd 0
    b dd 0
    deca db "%d", 0
    hexa db "%x", 0
    mesaj_a db "a=", 0
    mesaj_b db "b=", 0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mesaj_a
        call [printf]
        add esp, 4
        
        push a
        push deca
        call [scanf]
        add esp, 4*2
    
        push mesaj_b
        call [printf]
        add esp, 4
        
        push b
        push hexa
        call [scanf]
        add esp, 4*2
        
        xor EAX, EAX
        mov EAX, [a]
        add EAX, [b]
        
        mov ECX, 32
        mov EBX, 0
        calc_biti:
            test eax, 1
            jz bit_0
            inc EBX
            bit_0:
            shr EAX, 1
            loop calc_biti
            
        push EBX
        push deca
        call [printf]
        add esp, 4*2
            
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
