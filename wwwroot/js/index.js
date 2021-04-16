function valider() {
    const pw1OK = validerPassord($("#pw1").val(), 1);
    const pw2OK = validerPassord($("#pw2").val(), 2);
    const pwSjekk = sjekkPassord();

    if (pw1OK && pw2OK && pwSjekk) {
        endrePassord($("#pw1").val());
    }

}

function sjekkPassord() {
    var pw1 = $("#pw1").val();
    var pw2 = $("#pw2").val();

    if (pw1 == pw2) {
        $("#feil").html("");
        return true;
    }
    else {
        $("#feil").html("Passordene er ikke like.");
        return false;
    }
}

function endrePassord(pw) {
    var url = window.location.href;
    var urlnToken = url.split("&");
    var token = urlnToken[2];
    var username = urlnToken[1];

    $.post("Bruker/ChangePassword", username, token, pw, function () {
        // smth that tells u the password has been changed
    })
    .fail(function () {
        $("#feil").html("Kunne ikke endre passord.");
    });
}