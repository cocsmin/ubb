bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, printf, scanf, verificare_prim ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ; ...
    format_nr db '%d', 0
    format2 db 'Numarul %d este prim',13,10, 0
    mesaj_citire db 'Introduceti numerele intregi (introduceti 0 pentru a incheia citirea): ',13 , 10, 0
    numar dd 0
    mesaj_afisare db 'Numerele prime sunt',13 ,10, 0
    sir dd 0

; our code starts here
segment code use32 class=code
    start:
        push dword mesaj_citire
        call [printf]
        add esp, 4*1
        citeste:
            ;mov eax, [numar]
            push dword numar
            push dword format_nr
            call [scanf]
            add esp, 4*2
            
            mov EAX, [numar]
            call verificare_prim
            cmp AL, 1
            jne nu_afisa
            
            push dword [numar]
            push dword format2
            call [printf]
            add esp, 4*2
            nu_afisa:
            cmp dword[numar], 0
            jg citeste        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
        
        ;input: in EAX e numarul care urmeaza sa fie verficat
        ;output: in AL se pune 1 daca e prim si 0 daca nu e prim
        ; mov CX, 0
        ; mov BX, 1
        ; imparte:
            ; mov EAX, [ESP+4]
            ; div BX
            ; cwde
            ; cmp DX, 0
            ; jne nu_creste
            ; inc CX
            ; nu_creste:
            ; cmp BX, [ESP+4]
            ; je final
            ; inc BX
        ; final:
