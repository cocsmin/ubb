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
    ;Sa se citeasca de la tastatura doua numere a si b (in baza 10) şi să se determine relaţia de ordine dintre ele (a < b, a = b sau a > b). Afisati rezultatul în următorul format: "<a> < <b>, <a> = <b> sau <a> > <b>".
    a dd 0
    b dd 0
    format db "%d", 0
    mare db "%d > %d", 0
    mic db "%d < %d", 0
    egal db "%d = %d", 0
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
        
        mov EAX, [a]
        cmp EAX, [b]
        jg mai_mare
        jl mai_mic
        push dword[b]
        push dword[a]
        push egal
        call [printf]
        add esp, 4*3
        jmp gata
        
        mai_mare:
            push dword[b]
            push dword[a]
            push mare
            call [printf]
            add esp, 4*3
            jmp gata
            
        mai_mic:
            push dword[b]
            push dword[a]
            push mic
            call [printf]
            add esp, 4*3
            jmp gata
            
        gata:
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
