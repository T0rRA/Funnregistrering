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

function getImg() {
    var url = 'Funn/dJ?jsonStr=';


    const img = {
        "version": "1.0",
        "image": {
            "name": "/home/videosdownloader/public_html/image/public/uploadt/toby guard dog.png",
            "baseName": "toby guard dog.png",
            "format": "PNG",
            "formatDescription": "PNG",
            "mimeType": "image/png",
            "class": "DirectClass",
            "geometry": {
                "width": 771,
                "height": 443,
                "x": 0,
                "y": 0
            },
            "resolution": {
                "x": 56.69,
                "y": 56.69
            },
            "printSize": {
                "x": 13.6003,
                "y": 7.81443
            },
            "units": "PixelsPerCentimeter",
            "type": "TrueColor",
            "baseType": "Undefined",
            "endianness": "Undefined",
            "colorspace": "sRGB",
            "depth": 8,
            "baseDepth": 8,
            "channelDepth": {
                "red": 8,
                "green": 8,
                "blue": 1
            },
            "pixels": 1024659,
            "imageStatistics": {
                "Overall": {
                    "min": 10,
                    "max": 226,
                    "mean": 128.315,
                    "median": 24,
                    "standardDeviation": 12865.5,
                    "kurtosis": -1.19735,
                    "skewness": -0.317101,
                    "entropy": 0.923746
                }
            },
            "channelStatistics": {
                "red": {
                    "min": 18,
                    "max": 223,
                    "mean": 129.572,
                    "median": 24,
                    "standardDeviation": 12616.3,
                    "kurtosis": -1.15958,
                    "skewness": -0.353574,
                    "entropy": 0.925366
                },
                "green": {
                    "min": 15,
                    "max": 223,
                    "mean": 128.936,
                    "median": 24,
                    "standardDeviation": 12671.7,
                    "kurtosis": -1.20662,
                    "skewness": -0.318031,
                    "entropy": 0.916588
                },
                "blue": {
                    "min": 10,
                    "max": 226,
                    "mean": 126.436,
                    "median": 24,
                    "standardDeviation": 13308.5,
                    "kurtosis": -1.23407,
                    "skewness": -0.274614,
                    "entropy": 0.929285
                }
            },
            "renderingIntent": "Perceptual",
            "gamma": 0.45455,
            "chromaticity": {
                "redPrimary": {
                    "x": 0.64,
                    "y": 0.33
                },
                "greenPrimary": {
                    "x": 0.3,
                    "y": 0.6
                },
                "bluePrimary": {
                    "x": 0.15,
                    "y": 0.06
                },
                "whitePrimary": {
                    "x": 0.3127,
                    "y": 0.329
                }
            },
            "matteColor": "#BDBDBD",
            "backgroundColor": "#FFFFFF",
            "borderColor": "#DFDFDF",
            "transparentColor": "#00000000",
            "interlace": "None",
            "intensity": "Undefined",
            "compose": "Over",
            "pageGeometry": {
                "width": 771,
                "height": 443,
                "x": 0,
                "y": 0
            },
            "dispose": "Undefined",
            "iterations": 0,
            "compression": "Zip",
            "orientation": "Undefined",
            "properties": {
                "date:create": "2021-04-19T10:11:09+00:00",
                "date:modify": "2021-04-19T10:11:09+00:00",
                "png:cHRM": "chunk was found (see Chromaticity, above)",
                "png:gAMA": "gamma=0.45455 (See Gamma, above)",
                "png:IHDR.bit-depth-orig": "8",
                "png:IHDR.bit_depth": "8",
                "png:IHDR.color-type-orig": "2",
                "png:IHDR.color_type": "2 (Truecolor)",
                "png:IHDR.interlace_method": "0 (Not interlaced)",
                "png:IHDR.width,height": "771, 443",
                "png:pHYs": "x_res=5669, y_res=5669, units=1",
                "png:sRGB": "intent=0 (Perceptual Intent)",
                "signature": "742d4ffdc7ef0ebf43df3565d133e4f7f24740d8a1f9c2809ee6f6a42b16a261"
            },
            "tainted": false,
            "filesize": "356433B",
            "numberPixels": "341553",
            "pixelsPerSecond": "13.132MB",
            "userTime": "0.030u",
            "elapsedTime": "0:01.026",
            "version": "ImageMagick 7.0.10-58 Q16 x86_64 2021-01-14 https://imagemagick.org"
        }
    };

    console.log(img);



$.ajax({
    url: url + JSON.stringify(img),
    type: 'Post',
    data: JSON.stringify(img),
    dataType: 'json',
    contentType : 'application/json; chartset=utf-8',
    success: function (data) {
        console.log("it work: " + data);
    },
    fail: function (data) {
        console.log("it dont work: " + data);
    }
});

}