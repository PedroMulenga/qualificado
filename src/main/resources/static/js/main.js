/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('document').ready(function () {
    $('.table .editar').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (provincia, status) {
            $('#codigoEdit').val(provincia.codigo);
            $('#nomeEdit').val(provincia.nome);
            $('#siglaEdit').val(provincia.sigla);

        });

        $.get(href, function (municipio, status) {
            $('#codigoEdit').val(municipio.codigo);
            $('#nomeEdit').val(municipio.nome);
            $('#cursoEdit').val(municipio.nome);
            $('#provinciaEdit').val(municipio.provincia.codigo);

        });
        $.get(href, function (bairro, status) {
            $('#codigoEdit').val(bairro.codigo);
            $('#nomeEdit').val(bairro.nome);
            $('#municipioEdit').val(bairro.municipio.codigo);
            $('#nomeVulgarEdit').val(bairro.vulgar);


        });
        $.get(href, function (turma, status) {
            $('#codigoEdit').val(turma.codigo);
            $('#nomeEdit').val(turma.nome);
            $('#cursoEdit').val(turma.curso.codigo);

        });

        $.get(href, function (funcionario, status) {
            $('#tipoFuncionarioEdit').val(funcionario.tipoFuncionario);

        });
        $.get(href, function (aluno, status) {
            $('#codigoEdit').val(aluno.codigo);
            $('#sobrenomeEdit').val(aluno.sobrenome);
            $('#biEdit').val(aluno.bi);
            $('#dataNascimentoEdit').val(aluno.dataNascimento);
            $('#emailEdit').val(aluno.email);
            $('#telefoneEdit').val(aluno.telefone);
            $('#nomePaiEdit').val(aluno.nomePai);
            $('#nomeMaeEdit').val(aluno.nomeMae);
            $('#numCRMEdit').val(aluno.numCRM);
            $('#generoEdit').val(aluno.genero);
            $('#provinciaEdit').val(aluno.provincia.codigo);
            $('#municipioEdit').val(aluno.municipio.codigo);
            $('#bairroEdit').val(aluno.bairro.codigo);
            $('#situacaoEdit').val(aluno.situacao);
            $('#estadoEdit').val(aluno.estado);

        });
        $('#editarModal').modal();

    });
    $('.table #deleteButton').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#eliminarModal #delRef').attr('href', href);
        $('#eliminarModal').modal();

    });
});

function excluir(codigo) {
    swal({
        title: "Tem certeza?",
        text: "Você não poderá recuperar os dados após a exclusão.",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Sim, Excluir!",
        closeOnConfirm: false,
        showLoaderOnConfirm: true
    }, function () {
        setTimeout(function () {
            $.ajax({
                method: 'POST',
                url: 'eliminarAluno' + codig,
                contentType: 'application/json',
                data: JSON.stringify({codigo: codigo}),
                dataType: 'json',
                success: function (resposta) {
                    console.log(resposta);
                }
            });

            swal("Excluído!", "Registo Eliminado com sucesso.", "success");
        }, 2000);
    });
}
function GerarNumeroEstudante() {
    var texto = "20";
    var aleatorio = Math.floor(Math.random() * 1500);
    document.getElementById('numEstudante').value = (texto + aleatorio);

}

$('#button').click(function (e) {
    var bi = $('#bi').val();
    console.log(bi);
    $.ajax({
        method: 'POST',
        url: '/aluno/' + bi,
        contentType: 'application/json',
        data: JSON.stringify({bi: bi}),
        dataType: 'json',
        beforeSend: function (xhr) {
            var token = $('input[name=_csrf]').val();
            var header = $('input[name=_csrf_header]').val();
            xhr.setRequestHeader(header, token);
        },
        error: onErrorVisualizarResumo,
        success: onSuccessVisualizarResumo
    });

    function onErrorVisualizarResumo() {
        $('.loading').html("<img src='image/loading.gif' width='150'>");
        swal({
            title: "Estudante não Inscrito ou Cancelado",
            type: "warning"
        });
        //window.alert('ALUNO NÃO ENCONTRADO OU CANCELADO');
        document.getElementById('bi').value = ('');
        document.getElementById('nome').value = ('');
        document.getElementById('email').value = ('');
        document.getElementById('telefone').value = ('');
        document.getElementById('genero').value = ('');
        document.getElementById('crm').value = ('');
        document.getElementById('dataNascimento').value = ('');
        //$('.loading').hide();
        //console.log(arguments);
    }

    function onSuccessVisualizarResumo(response) {
        $('.loading').hide();
        swal({
            title: "Dados Encontrados com Sucesso",
            type: "success"
        });
        document.getElementById('nome').value = (response.nome) +' '+ (response.sobrenome);
        document.getElementById('email').value = (response.email);
        document.getElementById('telefone').value = (response.telefone);
        document.getElementById('genero').value = (response.genero);
        document.getElementById('crm').value = (response.numCRM);
        document.getElementById('dataNascimento').value = (response.dataNascimento);
        //console.log(response);
    }
});


$('#buttonPesquisar').click(function () {
    var numeroEstudante = $('#numeroEstudante').val();
    console.log(numeroEstudante);
    $.ajax({
        method: 'POST',
        url: '/pegarMatricula/' + numeroEstudante,
        contentType: 'application/json',
        data: JSON.stringify({numeroEstudante: numeroEstudante}),
        dataType: 'json',
        beforeSend: function (xhr) {
            var token = $('input[name=_csrf]').val();
            var header = $('input[name=_csrf_header]').val();
            xhr.setRequestHeader(header, token);
        },
        error: onErrorVisualizarResultado,
        success: onSuccessVisualizarResultado
    });

    function onErrorVisualizarResultado() {
        swal({
            title: "Estudante não Encontrado ou Cancelado",
            type: "warning"
        });
        document.getElementById('numeroEstudante').value = ('');
        document.getElementById('bi').value = ('');
        document.getElementById('nome').value = ('');
        document.getElementById('periodo').value = ('');
        document.getElementById('curso').value = ('');
        document.getElementById('turma').value = ('');
        document.getElementById('anoAcademico').value = ('');
        document.getElementById('anoCurricular').value = ('');
        //$('.loading').hide();
        //console.log(arguments);
    }

    function onSuccessVisualizarResultado(response) {
        swal({
            title: "Dados Encontrados com Sucesso",
            type: "success"
        });
        document.getElementById('nome').value = (response.aluno.nome) + ' '+(response.aluno.sobrenome);
        document.getElementById('bi').value = (response.aluno.bi);
        document.getElementById('periodo').value = (response.periodo);
        document.getElementById('anoCurricular').value = (response.anoCurricular);
        document.getElementById('anoCurricular').value = (response.anoAcademico);
        document.getElementById('turma').value = (response.turma.nome);
        document.getElementById('curso').value = (response.turma.curso.nome);
        //console.log(response);
    }
});

$(document).ready(function () {
    $('#buttonVerificar').click(function (e) {
        var numeroEstudante = $('#numeroEstudante').val();
        //var tabelaPagamentos = document.getElementById('#tabelaPagamentos');
        console.log(numeroEstudante);
        $.ajax({
            method: 'POST',
            url: '/pagamentoTransporte/' + numeroEstudante,
            contentType: 'application/json',
            data: JSON.stringify({numeroEstudante: numeroEstudante}),
            dataType: 'json',
            beforeSend: function (xhr) {
                var token = $('input[name=_csrf]').val();
                var header = $('input[name=_csrf_header]').val();
                xhr.setRequestHeader(header, token);
            },
            error: onErrorVisualizarResultados,
            success: onSuccessVisualizarResultados
        });

        function onErrorVisualizarResultados() {
            swal({
                title: "Dados Não Encontrados!",
                type: "warning"
            });
            document.getElementById('numeroEstudante').value = ('');
            document.getElementById('bi').value = ('');
            document.getElementById('nome').value = ('');
            document.getElementById('curso').value = ('');
            document.getElementById('turma').value = ('');
            document.getElementById('tabelaPagamentos').value = ('');
            $.get('/carregarPaginaFragmento', $(this).serialize(), function (emolumento) {
                $("#tabelaPagamentos").load("/carregarPaginaFragmento");
            });
            //$('.loading').hide();
            //console.log(arguments);
        }

        function onSuccessVisualizarResultados(resposta) {
            swal({
                title: "Dados Encontrados com Sucesso",
                type: "success"
            });
            document.getElementById('nome').value = (resposta.aluno.nome) + ' '+ (resposta.aluno.sobrenome);
            document.getElementById('bi').value = (resposta.aluno.bi);
            document.getElementById('turma').value = (resposta.turma.nome);
            document.getElementById('curso').value = (resposta.turma.curso.nome);
            $("#tabelaPagamentos").load("/carregarPaginaFragmento");
            //$("#tabelaPagamentos").data(resposta);
            //console.log(resposta);
        }
    });
});

$(document).ready(function () {
    $('#buttonBuscarDados').click(function (e) {
        var numeroEstudante = $('#numeroEstudante').val();
        //var tabelaPagamentos = document.getElementById('#tabelaPagamentos');
        console.log(numeroEstudante);
        $.ajax({
            method: 'POST',
            url: '/verificacaoRapida/' + numeroEstudante,
            contentType: 'application/json',
            data: JSON.stringify({numeroEstudante: numeroEstudante}),
            dataType: 'json',
            beforeSend: function (xhr) {
                var token = $('input[name=_csrf]').val();
                var header = $('input[name=_csrf_header]').val();
                xhr.setRequestHeader(header, token);
            },
            error: onErrorVisualizarResultados,
            success: onSuccessVisualizarResultados
        });

        function onErrorVisualizarResultados() {
            swal({
                title: "Não Pago!",
                type: "warning"
            });
            document.getElementById('numeroEstudante').value = ('');
            document.getElementById('bi').value = ('');
            document.getElementById('nome').value = ('');
            document.getElementById('curso').value = ('');
            document.getElementById('turma').value = ('');
            document.getElementById('mesReferente').value = ('');
            document.getElementById('situacao').value = ('');

            //$('.loading').hide();
            //console.log(arguments);
        }

        function onSuccessVisualizarResultados(resposta) {
            swal({
                title: "Pago com Sucesso",
                type: "success"
            });
            document.getElementById('nome').value = (resposta.matricula.aluno.nome) + ' '+  (resposta.matricula.aluno.sobrenome);
            document.getElementById('bi').value = (resposta.matricula.aluno.bi);
            document.getElementById('turma').value = (resposta.matricula.turma.nome);
            document.getElementById('curso').value = (resposta.matricula.turma.curso.nome);
            document.getElementById('mesReferente').value = (resposta.mesReferente);
            document.getElementById('situacao').value = (resposta.situacao);
            //console.log(resposta);
        }
    });
});

$(document).ready(function () {
    $('#buscarUsuario').click(function (e) {
        var bi = $('#bi').val();
        console.log(bi);
        $.ajax({
            method: 'POST',
            url: '/usuarioFuncionario/' + bi,
            contentType: 'application/json',
            data: JSON.stringify({bi: bi}),
            dataType: 'json',
            beforeSend: function (xhr) {
                var token = $('input[name=_csrf]').val();
                var header = $('input[name=_csrf_header]').val();
                xhr.setRequestHeader(header, token);
            },
            error: onErrorVisualizarResultados,
            success: onSuccessVisualizarResultados
        });

        function onErrorVisualizarResultados() {
            swal({
                title: "Dados do Usuário Não Encontrados!",
                type: "error"
            });
            document.getElementById('bi').value = ('');
            document.getElementById('nomeUsuario').value = ('');
        }

        function onSuccessVisualizarResultados(resposta) {
            swal({
                title: "Dados do Usuário Encontrados com Sucesso",
                type: "success"
            });
            document.getElementById('nomeUsuario').value = (resposta.nome);
            document.getElementById('email').value = (resposta.email);
            //console.log(resposta);
        }
    });
});



//JAVA SCRIPT PARA DESABILITAR OS ALERTAS AUTOMÁTICAMENTE APOIS 5 MINUTOS
$('document').ready(function () {
    setTimeout(function (){
        $(".alertas").fadeOut("slow", function(){
          $(this).alert('close');  
        });       
    }, 5000);
});




