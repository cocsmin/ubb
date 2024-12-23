#include "validator.h"
#include <string.h>
using namespace std;

void Validator::validare(string titlu, string gen, string actor, int an) 
{
    string errors;
    if (titlu.length()<2)
        errors.append("Titlul nu poate fi vid");
    if (actor.length() < 2)
        errors.append("Numele actorului nu poate fi vid");
    if (an < 0)
        errors.append("Anul nu poate fi mai mic ca 0");
    if (gen.length() < 2)
        errors.append("Genul filmului nu poate fi vid");
    if (errors.size() != 0)
        throw Exceptie(errors);
}
