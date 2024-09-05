bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fprintf, fopen, fclose,              ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici si spatii. Sa se inlocuiasca toate literele de pe pozitii pare cu numarul pozitiei. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier.
    text_dat db "ana are mere", 0
    len equ $-text_dat-1
    nume_fisier db "date20.txt", 0
    mod_acces db "w", 0
    rezultat times len+1 db 0
    descriptor dd -1
    
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov ESI, text_dat
        mov EDI, rezultat
        mov BH, 0
        mov ECX, len
        repeta:
            lodsb
            cmp AL, 'a'
            jl next
            cmp AL, 'z'
            jg next
            test BH, 1
            jnz next
            mov AL, BH
            ;add AL, '0'
            next:
            stosb
            inc BH
            loop repeta
        mov AL, 0
        stosb
        
        push mod_acces
        push nume_fisier
        call [fopen]
        add esp, 4*2
        
        mov [descriptor] , EAX
        cmp EAX, 0
        je final
        
        
        ;mov [rezultat], EDI
        push rezultat
        push dword[descriptor]
        call [fprintf]
        add esp, 4*2
        
        push dword[descriptor]
        call [fclose]
        add esp, 4
        
        
        
        
        
        
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
