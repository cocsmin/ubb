;adancimeArbore(l:lista, nivel: intreg, adancimea curenta) = {
    ; nivel, daca l este atom(frunza)
    ; max({adancimeArbore(li, nivel + 1)}), unde li £ l si l este lista
    ; }

(defun adancimeArbore(l nivel)
    (cond
        ((atom l) nivel)
        (t (apply #'max (mapcar #'(lambda (list) (adancimeArbore list (+ nivel 1))) l)))
    )
)

;adancimeArboreMain(l: lista) = adancimeArbore(l, 0)

(defun adancimeArboreMain(l)
    (adancimeArbore l 0)
)


; (adancimeArboreMain '(a  (b (c)) (d) (e (f))))