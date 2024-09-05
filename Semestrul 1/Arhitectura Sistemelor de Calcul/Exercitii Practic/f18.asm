bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fread, printf, fopen, fclose,               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fread msvcrt.dll
import printf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    nr_cuv dd 0
    descriptor dd -1
    len equ 100
    text times len db 0
    afisare db "Numarul de cuvinte este: %d", 0
    nume_fisier db "date18.txt", 0
    mod_acces db "r", 0
    

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
        
        push dword[descriptor]
        push len
        push 1
        push text
        call [fread]
        add esp, 4*4
        
        mov EBX, 0
        mov ESI, text
        repeta:
            lodsb
            cmp AL, 0
            je afara
            cmp AL, 'a'
            jl next
            cmp AL, 'z'
            jg next
            repeta2:
                lodsb
                cmp AL, '.'
                je aduna
                cmp AL, ' '
                je aduna
                jmp repeta2
            aduna:
            inc EBX ; nr_cuv
            next:
            jmp repeta
            
        
        afara:
        mov [nr_cuv], EBX
        
        push dword[nr_cuv]
        push afisare
        call [printf]
        add esp, 4*2
        
        push dword[descriptor]
        call [fclose]
        add esp, 4
            
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
