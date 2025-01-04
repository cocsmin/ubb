(defun functie(lista rez)
  (cond
    ((null lista) rez)
    ((numberp (car lista)) (functie (cdr lista) rez))
    ((atom (car lista)) (functie (cdr lista) (cons (car lista) rez)))
    (t (functie (cdr lista) (functie (car lista) rez)))
  )
)

(print (functie '((((A B) 2 C) 3 (D 1 E))) ()))
