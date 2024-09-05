bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fprintf, fopen, fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fopen msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se dau un nume de fisier si un text (definite in segmentul de date). Textul contine litere mici, litere mari, cifre si caractere speciale. Sa se transforme toate literele mici din textul dat in litere mari. Sa se creeze un fisier cu numele dat si sa se scrie textul obtinut in fisier
    text db "Ana Are 3 mere si 420 DE $!", 0
    len equ $-text
    nume_fisier db "date13.txt"
    text_nou times len db 0
    mod_acces db "w+", 0
    descriptor dd -1
; our code starts here
segment code use32 class=code
    start:
        ; ...
        push mod_acces
        push nume_fisier
        call [fopen]
        mov [descriptor], EAX
        cmp EAX, 0
        je final
        
        mov ESI, text
        mov EDI, text_nou
        mov EBX, 0
        repeta:
            cmp EBX, len
            je afara
            lodsb
            cmp AL, 'a'
            jl next
            cmp AL, 'z'
            jg next
            sub AL, 'a'-'A'
            next:
            stosb
            inc EBX
            jmp repeta
            
        afara:
        push text_nou
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
