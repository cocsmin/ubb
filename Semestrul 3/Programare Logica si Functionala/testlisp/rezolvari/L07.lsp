(defun inserare_n (e lista n)
  (cond
    ((= n 1) (cons e (cons (car lista) (cdr lista))))
    (t (cons (car lista) (inserare_n e (cdr lista) (- n 1))))
  )
)

(defun inserare_aux (elem lista poz lungime)
  (cond
    ((> poz (+ 1 lungime)) NIL)
    (t (cons (inserare_n elem lista poz) (inserare_aux elem lista (+ 1 poz) lungime)))
  )
)

(defun inserare (elem lista)
  (inserare_aux elem lista 1 (length lista))
)

(print (inserare '1 '(2 3)))
