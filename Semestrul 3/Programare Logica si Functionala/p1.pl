
% problema 6:
% a)elimina rep
/*
stergeAparitii(lista, element, lista_rezultat)
flux(i, i, o)
stergeAparitii([l1...ln], Elem) = {
    [], daca l e goala
    l1 u stergeAparitii([l2...ln], Elem), daca l1 != Elem
    stergeAparitii([l2...ln], Elem), daca l1 = Elem
}

*/
stergeAparitii([], _, []).
stergeAparitii([H|T], Elem, [H|R]):- H =\= Elem,
                                    stergeAparitii(T, Elem, R).
stergeAparitii([H|T], Elem, R):- H =:= Elem,
                                stergeAparitii(T, Elem, R).

/* 
nrAparitii(lista, element, rezultat)
flux(i, i, o)

nrAparitii([l1...ln], Elem) = {
    0, daca l e goala
    1 + nrAparitii([l2...ln], Elem), daca l1 = Elem
    nrAparitii([l2...ln], Elem), daca l1 != Elem
}


*/

nrAparitii([], _, 0).
nrAparitii([H|T], Elem, N):- H =:= Elem,
                            nrAparitii(T, Elem, NN),
                            N is NN + 1.
nrAparitii([H|T], Elem, N):- H =\= Elem,
                            nrAparitii(T, Elem, N).

/*
elimina_rep(lista, lista_rezultat)
flux(i, o)

elimina_rep([l1...ln], R) = {
    [], daca l e goala
    elimina_rep([l2...ln], l1 u R) daca nrAparitii(l1) = 1
    elimina_rep([l2...ln], R) daca nrAparitii > 1
}

*/
elimina_rep([], []).
elimina_rep([H|T], [H|R]):- nrAparitii([H|T], H, Nr),
                            Nr =:= 1,
                            elimina_rep(T, R).
elimina_rep([H|T], R):- nrAparitii([H|T], H, Nr),
                        Nr > 1,
                        stergeAparitii([H|T], H, RR),
                        elimina_rep(RR, R).

/*
b)elimina maxmul


numarMaxim(nr1, nr2, rezultat)
flux(i, i, o)

numarMaxim(nr1, nr2) = {
    nr1, daca nr1 >= nr2
    nr2, daca nr1 < nr2
}

*/

numarMaxim(X, Y, X):- X >= Y.
numarMaxim(X, Y, Y):- X < Y.

/*
maximLista(lista, rezultat)
flux(i, o)

maximLista([l1...ln], R) = {
    l1, daca lista e formata doar din l1
    numarMaxim(l1, maximLista([l2...ln]), altcumva
}

*/
maximLista([H], H).
maximLista([H|T], R):- maximLista(T, RM),
                        numarMaxim(H, RM, R).


/*


*/

elimina_max([], []).
elimina_max(L, R):- maximLista(L, RM),
                    stergeAparitii(L, RM, R).

/*
['/Users/cosmin/Documents/facultate/sem3/plf/lab1.pl']
elimina_rep([1,2,3,4,1,2], R).
elimina_rep([1,1,1], R).
elimina_rep([], R).


elimina_max([1,2,3,4], R).
elimina_max([4,4,4], R).
elimina_max([], R).

*/
