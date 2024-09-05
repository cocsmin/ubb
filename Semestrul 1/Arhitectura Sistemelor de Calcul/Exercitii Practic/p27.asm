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
    ;Se dă un sir de caractere (definit in segmentul de date). Să se citească de la tastatură un caracter, să se determine numărul de apariţii al acelui caracter în şirul dat şi să se afişeze acel caracter împreună cu numărul de apariţii al acestuia
    sir db 'a', 'b', 'c', 'a', 'e', 'a', 0
    lungime equ $-sir
    format db "%c", 0
    caracter db 0
    contor dd 0
    mesaj db "Introduceti caracterul: ", 0
    afisare db "Caracterul %c apare de %d ori "
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mesaj
        call [printf]
        add esp, 4
        
        push caracter
        push format
        call [scanf]
        add esp, 4*2
        
        mov ESI, 0
        mov EBX, 0
        mov ECX, lungime
        repeta:
            mov AL, [sir+ESI]
            cmp AL, [caracter]
            jne next
            inc EBX
            next:
            inc ESI
            loop repeta
        
        mov [contor], EBX
        push dword[contor]
        push dword[caracter]
        push afisare
        call [printf]
        add esp, 4*3
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
