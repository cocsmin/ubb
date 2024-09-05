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
    ;Se dau doua numere naturale a si b (a: dword, b: word, definite in segmentul de date). Sa se calculeze a/b si sa se afiseze catul impartirii in urmatorul format: "<a>/<b> = <cat>"
    ;Exemplu: pentru a = 200 si b = 5 se va afisa: "200/5 = 40"
    ;Valorile vor fi afisate in format decimal (baza 10) cu semn.
    a dd 200
    b dw 5
    cat dd 0
    format db "%d/%d = %d", 0
; our code starts here
segment code use32 class=code
    start:
        ; ...
        mov AX, [a]
        mov DX, [a+2]
        idiv word[b]
        mov [cat], AX
        mov AX, [b]
        cwde
        push dword[cat]
        push EAX
        push dword[a]
        push format
        call [printf]
        add esp, 4*4
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
