
//Funcionalitat Formulari de Contacte
$(document).ready(function () {

    var emailreg = /^[a-zA-Z0-9_\.\-]+@[a-zA-Z0-9\-]+\.[a-zA-Z0-9\-\.]+$/;

    $("#name").blur(function(){
        $(".error").remove();
    });

    $("#correu").blur(function(){
        $(".error").remove();
    });

    $("#assumpte").blur(function(){
        $(".error").remove();
    });

    $("#message").blur(function(){
        $(".error").remove();
    });

    $("#botoenviar").click(function (){
        if( $("#name").val() == "" ){
            $("#name").focus().after("<span class='error'>Afegeix el seu nom</span>");
            return false;
        }else if( $("#correu").val() == "" || !emailreg.test($("#correu").val()) ) {
            $("#correu").focus().after("<span class='error'>Afegeix un email correcte</span>");
            return false;
        }else if( $("#assumpte").val() == ""){
            $("#assumpte").focus().after("<span class='error'>Afegeix un assumpte</span>");
            return false;
        }else if( $("#message").val() == "" ) {
            $("#message").focus().after("<span class='error'>Afegeix un missatge</span>");
            return false;
        }
            $('#success').show();
            return false;
    });
});

