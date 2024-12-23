%submultimi_suma_div(L:lista, R:lista | rezultat, format din subliste)
%flux(i, o)
%submultimi_suma_div(L) = findall(submultime(L) | suma(Sub) % lungime(L) = 0)


submultimi_suma_div(L, R):- lungime(L, Lung),
                            findall(Sub, (submultime(L, Sub), suma(Sub, Rez), Rez mod Lung =:= 0), R).


%submultime(L:lista, Sub:lista)
%flux(i, o)
%submultime([l1...ln]) = {
%   1)[], daca n = 0;
%   2)l1 U submultime([l2...ln])
%   3)
%   
}
submultime([], []).
submultime([H|T], [H|Sub]):- submultime(T, Sub).
submultime([_|T], Sub):- submultime(T, Sub).

%suma(L:lista, Rez:numar | rezultat)
%flux(i,o)
%suma([l1...ln]) = {
    0, daca n = 0
    l1 + suma([l2...ln])
}

suma([], 0).
suma([H|T], Rez):- suma(T, Sum),
                   Rez is H + Sum.

%lungime(L:lista, Lung:numar)
%flux(i,o)
%lungime([l1...ln]) = {
    0, daca n=0;
    1 + lungime([l2...ln])
}

lungime([], 0).
lungime([_|T], L):- lungime(T, L1),
                    L is L1 + 1.

%['/Users/cosmin/Documents/facultate/sem3/plf/lab3.pl'].
%submultimi_suma_div([1,2,3,4], R).