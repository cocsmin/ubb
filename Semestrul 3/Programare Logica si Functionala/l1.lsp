; 8 a) elimina_n (L:lista, n:intreg) = {
;    [], daca lista e vida,
;    l2...ln, daca n = 1,
;    l1 + elimina_n(l2...ln, n-1)
;}

(defun elimina_n(lista n)
    (cond
        ((null lista) nil)
        ((= n 1) (cdr lista))
        (t (cons (car lista) (elimina_n (cdr lista) (- n 1))))
    )
)     

;(elimina_n '(1 2 3 4 5) 4)

; 8 b) inv(l: lista, aux: lista) = {
;    aux, lista e vida,
;    inv(l2...ln, l1 + aux)
;}

(defun inv (l aux)
    (cond
        ((null l) aux)
        (t (inv (cdr l) (cons (car l) aux)))
    )
)

;inversa(l: lista) = inv(l: lista, [])
(defun inversa (l)
    (inv l '())
)

;increment(l: lista) = {
;    1, daca l este vida,
;    increment(0 + increment(l2...ln)), daca l1 = 9,
;    (l1++1, l2...ln), daca l1 < 9
;}

(defun increment (l)
    (cond
        ((null l) '(1))
        ((= (car l) 9) (cons 0 (increment (cdr l)))) 
        (t (cons (+ (car l) 1) (cdr l)))
    )
)

;succesor(l: lista) = inversa(increment(inversa(l)))

(defun succesor (l)
    (inversa (increment (inversa l)))
)

;(succesor '(1 9 5 9 9))

; 8 c) 
;adauga(elem: intreg, multime: lista) = {
;    elem, daca multime e vida,
;    multime, daca m1 = elem
;    m1 + elem + adauga(elem, m2...mn), altfel
;}

(defun adauga (elem multime)
    (cond
        ((null multime) (cons elem nil))
        ((equal elem (car multime)) multime)
        (t (cons (car multime) (adauga elem(cdr multime))))
    )
)

;extrage_atomi(lista: lista, multime: lista) = {
;    multime, daca lista e vida,
;    adauga(l1, multime), daca l1 este atom,
;    extrage_atomi(l2...ln, extrage_atomi (l1, multime))
;}

(defun extrage_atomi (lista multime)
    (cond
        ((null lista) multime)
        ((atom lista) (adauga lista multime))
        (t (extrage_atomi (cdr lista) (extrage_atomi (car lista) multime)))
    )
)

;multime_atomi(lista: lista) = extrage_atomi(lista, [])

(defun multime_atomi (lista)
    (extrage_atomi lista nil)
)

;(multime_atomi'(1 (2 (1 3 (2 4) 3) 1) (1 4)))


; 8 d) exista(elem: intreg, lista: lista) = {
;    nil, daca lista e vida
;    true, daca l1 = elem
;    exista(elem, l2...ln), altfel
;}

(defun exista (elem lista)
    (cond
        ((null lista) nil)
        ((equal elem (car lista)) t)
        (t (exista elem (cdr lista)))
    )
)


;verifica_multime(lista: lista, verificat: lista) = {
;    t, daca lista e vida,
;    nil, daca exista(l1, verificat),
;    verifica_multime(l2...ln, l1+verificat)
;}

(defun verifica-multime (lista verificat)
    (cond
        ((null lista) t)
        ((exista (car lista) verificat) nil)
        (t (verifica-multime (cdr lista) (cons (car lista) verificat)))
    )
)

;este-multime(lista: lista) = verifica_multime(lista, []).

(defun este-multime (lista)
    (verifica-multime lista nil)
)