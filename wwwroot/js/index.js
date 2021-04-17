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

function getFunn() {
    var url = 'Funn/GetFunn?brukernavn=s333752@oslomet.no&funnId=1';
    //console.log(res.);
    //const data = res.json();
    //data:image/png;base64,
    var email = 's333752@oslomet.no';
    var id = 1;

    $.post(url, function (data) {
        console.log(data);
        // get path and display data
        console.log(data.image);
        document.getElementById("img").src = data.image;
    })
        .fail(function (data) {
            console.log("FEIL: " + data);
        });
}