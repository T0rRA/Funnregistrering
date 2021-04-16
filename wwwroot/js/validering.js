function validerPassord(pw, nr) {
    const regexp = /^(?=.*[A-ZÅÆØÅ])(?=.*\d)[A-ZÆØÅa-zæøå\d]{6,}$/;
    const ok = regexp.test(pw);
    if (!ok) {
        $("#feil" + nr).html("Passord må inneholde minst 6 tegn, et tall og en stor bokstav");
        return false;
    }
    else {
        $("#feil" + nr).html("");
        return true;
    }
}