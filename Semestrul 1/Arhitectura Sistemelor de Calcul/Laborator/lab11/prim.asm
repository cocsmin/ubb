bits 32 ; assembling for the 32 bits architecture

global verificare_prim

segment code use32 class=code
    verificare_prim:
        cmp EAX, 1
        je nu_e_prim
        mov ECX, 2
        imparte:
            cmp ECX, EAX
            je e_prim
            mov EDX, 0
            mov EBX, EAX
            div ECX
            cmp EDX, 0
            jz nu_e_prim
            mov EAX, EBX
            inc ECX
            jmp imparte
            
        e_prim:
            mov AL, 1
            ret
        
        nu_e_prim:
            mov AL, 0
            ret