; nivelNod(l:elemnt/lista, e: element, nivel) = {
;     nivel, l este atom si l = e;
;     0, l este atom;
;     nivelNod-helper(l e nivel 0), altcumva
; }


(defun nivelNod (l e nivel)
  (cond
    ((and (atom l) (eq l e)) nivel)
    ((atom l) 0)
    (t (nivelNod-helper l e nivel 0))))

; nivelNod-helper(l1...ln: lista, e: element, nivel, max-nivel: nivelul maxim) = {
;     max-nivel, daca l e vida;
;     nivelNod-helper(l2...ln, e, nivel, max(max-nivel, nivelNod(l1, e, nivel + 1))), altcumva 
; }

(defun nivelNod-helper (l e nivel max-nivel)
  (cond
    ((null l) max-nivel)
    (t (nivelNod-helper (cdr l) e nivel (max max-nivel (nivelNod (car l) e (+ 1 nivel)))))))

;mainNivel(l:element/lista, e:element) = nivelNod(l, e, -1)

(defun mainNivel(l e)
  (nivelNod l e -1)
)

 (mainNivel '(a (b c) (d (e) (f))) 'e)
 (mainNivel '(a (b(d(g))(e(h(l(m n))i)))) (c(f(j k(q(p(r)))))) )