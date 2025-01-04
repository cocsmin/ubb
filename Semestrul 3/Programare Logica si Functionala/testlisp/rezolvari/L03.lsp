(defun pereche(e lista)
  (cond
    ((null lista) nil)
    ((not (numberp (car lista))) (cons (list e (car lista)) (pereche e (cdr lista))))
    (t (pereche e (cdr lista)))
  )
)

(defun functie (lista)
  (cond
    ((null lista) '())
    ((numberp (car lista)) (functie (cdr lista)))
    (t (append (pereche (car lista) (cdr lista)) (functie (cdr lista))))
  )
)

(print (functie '(A 2 B 3 C D 1)))
