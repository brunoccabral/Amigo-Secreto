//O principal objetivo deste desafio é fortalecer suas habilidades em lógica de programação. Aqui você deverá desenvolver a lógica para resolver o problema.

// Lista para armazenar nomes até o sorteio.
let nomesNaTela = [];

// Adiciona um nome à lista e atualiza a exibição
function adicionarAmigo() {
    let nomeAmigo = document.querySelector("input").value.trim();
//pega o valor digitado no campo de entrada HTML, foi usado o trim para remover espaços no inicio ou final.

    if (nomeAmigo == '') //validação : se estiver vazio irá exibir o alerta
    {
        alert('Insira um nome.');
    } else { 
        nomesNaTela.push(nomeAmigo); //adicionar nome na lista
        listarNomes(); //chama a função para exibir nome adicionado
        limparCampo();  // chama a função para apagar o texto no campo de entrada
    }
    
} 

// Exibe os nomes na lista
function listarNomes() {
    let listaAmigos = document.getElementById("listaAmigos"); //obter elemento ul
    listaAmigos.innerHTML = "";
    // Limpa a lista antes de renderizar novamente
    
    nomesNaTela.forEach(nome => {
        let li = document.createElement("li"); //criar item da lista ao elemento ul
        li.textContent = nome;
        listaAmigos.appendChild(li);
    });
}

// Sorteia um nome da lista
function sortearAmigo() {
    let tamanho = nomesNaTela.length; //obter numero dos nomes.
    let numeroSorteio = Math.floor(Math.random() * tamanho);
    //Math.random() : gerar um número decimal entre 0 e 1.
    // * tamanho :multiplica pelo tamanho da lista
    // Math.floor: converte para numero inteiro

    let amigoSorteado = nomesNaTela[numeroSorteio];

    if (nomesNaTela.length == "") {
        alert ('Nenhum nome adicionado.'); //valida se tem nomes
    }else{
        let resultado = document.getElementById("resultado").innerHTML;
        resultado = "<li>" + "O amigo sorteado foi: " + amigoSorteado + "</li>";
        document.getElementById("resultado").innerHTML = resultado;
        // insere o nome sorteado
        document.getElementById('listaAmigos').innerHTML = "";
        nomesNaTela = [];
        // reinicia a lista.
    }
}


function limparCampo() {
    let nomeAmigo = document.querySelector("input");
    nomeAmigo.value = '';
}