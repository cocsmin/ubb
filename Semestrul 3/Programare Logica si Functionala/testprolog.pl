%['/Users/cosmin/Documents/facultate/sem3/plf/testprolog.pl'].
%cmmdc(nr1:numar, nr2:numar, nr1: numar | rezultatul)
%flux(i,i,o)
%cmmdc(A, B) = {
%    B, daca A = 0;
%    A, daca B = 0;
%    cmmdc(A/B), daca A > B;
%    cmmdc(B/A), daca A < B;
%}

cmmdc(0, B, B):-!.
cmmdc(A, 0, A):-!.
cmmdc(A, B, Rez):-
    A>=B,
    A1 is A-B,
    cmmdc(B, A1, Rez).

cmmdc(A, B, Rez):-
    A<B,
    B1 is B-A,
    cmmdc(B1, A, Rez).

%maimari(L:lista, X;numar, Rez:lista|rezultat)
%flux(i,i,o)
%maimari([l1...ln], X) = {
%    [], daca n = 0;
%    l1 U maimari([l2...ln]), daca l1 > X;
%    maimari([l2...ln]), altcumva
%}

maimari([], _, []).
maimari([H|T], X, [H|Rez]):-
    H > X,
    maimari(T, X, Rez).

maimari([H|T], X, Rez):-
    H =< X,
    maimari(T, X, Rez).

%predicat(L:lista, X:numar, D:numar | rezultatul)
%flux(i,i,o)
%predicat([l1...ln], X) = {
%    0, daca n = 0;
%    l1, daca n = 1;
%    (cmmdc(cmmdc(x1,x2)),x3...), unde x1...xm e lista formata de maimari(L, X).
%}

%cmmdc_lista(L:lista, R:numar|rezultat)
%cmmdc_lista={
%    0, n = 0;
%    l1, daca n = 1;
%    
%}

cmmdc_lista([], 0).
cmmdc_lista([X], X).
cmmdc_lista([H|T], Rez):-
    cmmdc(H, Rez, Div),
    cmmdc_lista(T, Div).


predicat(L, X, D):-
    maimari(L, X, R),
    cmmdc_lista(R, D).






