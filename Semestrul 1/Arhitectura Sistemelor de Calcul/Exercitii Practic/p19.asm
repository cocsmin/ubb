bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import printf msvcrt.dll
import scanf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Sa se citeasca de la tastatura un octet si un cuvant. Sa se afiseze pe ecran daca bitii octetului citit se regasesc consecutiv printre bitii cuvantului. Exemplu:
    ;a = 10 = 0000 1010b
    ;b = 256 = 0000 0001 0000 0000b
    ;Pe ecran se va afisa NU.
    ;a = 0Ah = 0000 1010b
    ;b = 6151h = 0110 0001 0101 0001b
    ;Pe ecran se va afisa DA (bitii se regasesc pe pozitiile 5-12).
    a dw 0
    b db 0
    format1 db "%d", 0
    format2 db "%d", 0
    mesaj_da db "DA", 0
    mesaj_nu db "NU", 0
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
        push format1
        call [scanf]
        add esp, 4*2
        
        push mesaj_b
        call [printf]
        add esp, 4
        
        push b
        push format2
        call [scanf]
        add esp, 4*2
        
        xor AX, AX
        mov ECX, 16
        mov AX, [b]
        mov BL, [a]
        repeta:
            cmp AL, BL
            jz da
            shr AX, 1
            loop repeta
        
        nu:
            push mesaj_nu
            call [printf]
            add esp, 4
            jmp gata
        
        da:
            push mesaj_da
            call [printf]
            add esp, 4
            
        gata:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
