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
    ;Se dau doua numere naturale a si b (a, b: word, definite in segmentul de date). Sa se calculeze a/b si sa se afiseze catul si restul impartirii in urmatorul format: "Cat = <cat>, rest = <rest>"
    ;Exemplu: pentru a=23 si b=10 se va afisa: "Cat = 2, rest = 3"
    ;Valorile vor fi afisate in format decimal (baza 10) cu semn.
    a dw 23
    b dw 10
    catul dd 0
    restul dd 0
    format db "Cat = %d , rest = %d", 0
    
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AX, [a]
        cwd
        idiv word[b]
        mov [catul], AX
        mov [restul], DX
        
        push dword[restul]
        push dword[catul]
        push format
        call [printf]
        add esp, 4*3
        
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
