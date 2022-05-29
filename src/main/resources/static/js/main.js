/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//JAVA SCRIPT PARA DESABILITAR OS ALERTAS AUTOMÁTICAMENTE APOIS 5 MINUTOS
$('document').ready(function () {
    $('#file').change(function () {
        showImageThumbnail(this);
    });
    setTimeout(function () {
        $(".alertas").fadeOut("slow", function () {
            $(this).alert('close');
        });
    }, 5000);
});
//JAVA SCRIPT PARA APRESENTAR A IMAGEM APÓIS SER INSERIDA NO CADASTRO DE USUÁRIOS E MATRÍCULAS
function showImageThumbnail(fileInput) {
    file = fileInput.files[0];
    reader = new FileReader();
    reader.onload = function (e) {
        $('#thumbal').attr('src', e.target.result);
    };
    reader.readAsDataURL(file);
}

$(document).ready(function () {
    var phone = $('.phone').mask('000-000-000');
    var bi = $('.bi').mask('000000000SS000');
});

