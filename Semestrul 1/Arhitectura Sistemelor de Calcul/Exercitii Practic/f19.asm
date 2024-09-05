bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fprintf, fread, fopen, fclose               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions
import fprintf msvcrt.dll
import fread msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    ;Se dau in segmentul de date un nume de fisier si un text (poate contine orice tip de caracter). Sa se calculeze suma cifrelor din text. Sa se creeze un fisier cu numele dat si sa se scrie suma obtinuta in fisier.
    text db "Ba sa mor eu ce-as bea 5 beri si 2 sticle de vin", 0
    nume_fisier db "date19.txt", 0
    mod_acces db "w+", 0
    suma_cf dd 0
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
        mov BH, 0
        repeta:
            lodsb
            cmp AL, 0
            je afara
            cmp AL, '0'
            jl next
            cmp AL, '9'
            jg next
            sub AL, '0'
            add BH, AL
            next:
            jmp repeta
        afara: 
        mov [suma_cf], BH
        
        push suma_cf
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
