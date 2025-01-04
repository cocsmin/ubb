(defun functie (lista indice n)
  (cond
    ((null lista) nil)
    ((= indice n) (functie (cdr lista) (+ 1 indice) (* 2 n)))
    ((cons (car lista) (functie (cdr lista) (+ 1 indice) n)))
  )
)

(print (functie '(1 2 3 4 5 6) 1 1))
