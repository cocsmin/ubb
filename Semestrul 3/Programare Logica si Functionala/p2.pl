/*
problema 14
['/Users/cosmin/Documents/facultate/sem3/plf/lab2.pl'].
a) predicatul trebuie sa determine predecesorul unui număr 
reprezentat cifra cu cifra intr o lista ex [1,9,3,6,0,0] => [1,9,3,5,9,9]

predecesor([l1...ln]) = {
        [], daca lista e goala
        [], daca lista are un singur element, pe 0
        decrement([ln...l1]), altfel
    }


*/
inversa(L, Linv) :- inv(L, [], Linv).
inv([], Aux, Aux).
inv([H|T], Aux, Linv) :- inv(T, [H|Aux], Linv).  

predecesor([], []).
predecesor([0], []) :- !.
predecesor(L, R) :- inversa(L, Linv), 
                    decrement(Linv, Rinv),
                    inversa(Rinv, R).  

  

/*
decrement([l1...ln]) = {
        [], daca lista e goala
        9 u decrement([l2...ln]), daca l1 = 0
        (l1 - 1) u l2...ln, altfel
    }

*/

decrement([], []).
decrement([0|T], [9|R]) :-  decrement(T, R).

decrement([H|T], [H1|T]) :- H > 0,
                            H1 is H - 1.

/* TESTE
predecesor([1,9,3,6,0,0], R).
predecesor([2,0,0], R).
predecesor([1,9,9], R).
predecesor([1], R).
predecesor([0], R).
*/

%b) se da o lista eterogena formata din numere si liste, pt fiecare sublista sa se determine predecesorul

/*

predecesor2([l1...ln]) = {
        [], daca lista e goala
        predecesor[l1] u predecesor2([l2...ln]), daca l1 este o lista
        l1 u predecesor2([l2...ln]), altfel
    }

*/

predecesor2([], []).
predecesor2([0], []).
predecesor2([H|T], R) :- is_list(H),
                         predecesor(H, HH),
                         predecesor2(T, RR),
                         R = [HH|RR].

predecesor2([H|T], R) :- number(H),
                         predecesor2(T, RR),
                         R = [H|RR].                         
/* TESTE
predecesor2([1,[2,3],4,5,[6,7,9],10,11,[1,2,0],6], R), write(R).       
predecesor2([1,2,3,4], R).
predecesor2([[1] ,[2], [3], [4]], R).
predecesor2([[0], [1]], R).
*/
