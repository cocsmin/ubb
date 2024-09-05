bits 32 ; assembling for the 32 bits architecture

global _verificare_prim
segment data public data use32
segment code public use32 class=code
    _verificare_prim:
        push EBP
        mov EBP, ESP
        mov EAX, [EBP + 8]
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
            mov ESP, EBP
            pop ebp
            ret 
        
        nu_e_prim:
            mov AL, 0
            mov ESP, EBP
            pop ebp
            ret
            