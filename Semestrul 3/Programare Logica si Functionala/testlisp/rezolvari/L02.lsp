(defun functie(l rez)
  (cond
    ((null l) rez)
    ((listp (car l)) (append rez (append (list (functie (car l) nil)) (functie (cdr l) nil))))
    (t (functie (cdr l) (append (list (car l)) rez)))
  )
)


(print (functie '(A B C (D (E F) G H I)) '()))
