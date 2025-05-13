// Conversor de Moedas
// Projeto desenvolvido como parte do programa ONE (Oracle Next Education)
// O objetivo é demonstrar o consumo de uma API pública de câmbio, com uso de orientação a objetos

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// Representa uma moeda com código e nome
class Moeda {
    private String codigo;
    private String nome;

    public Moeda(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }
}

// Representa os dados necessários para realizar uma conversão
class Conversao {
    private Moeda origem;
    private Moeda destino;
    private double valor;

    public Conversao(Moeda origem, Moeda destino, double valor) {
        this.origem = origem;
        this.destino = destino;
        this.valor = valor;
    }

    public Moeda getOrigem() {
        return origem;
    }

    public Moeda getDestino() {
        return destino;
    }

    public double getValor() {
        return valor;
    }
}

// Responsável por se comunicar com a API de câmbio
class APIClient {
    private static final String API_KEY = "SUA_API_KEY_AQUI"; // Substitua pela sua chave de acesso à API
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    // Obtém a taxa de conversão entre duas moedas
    public double obterTaxa(String de, String para) {
        try {
            String urlString = BASE_URL + API_KEY + "/pair/" + de + "/" + para;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                resposta.append(linha);
            }
            reader.close();

            JsonObject obj = JsonParser.parseString(resposta.toString()).getAsJsonObject();
            return obj.get("conversion_rate").getAsDouble();

        } catch (Exception e) {
            System.out.println("Erro ao conectar com a API: " + e.getMessage());
            return 0;
        }
    }
}

// Realiza a conversão e exibe o resultado no console
class Conversor {
    private APIClient apiClient = new APIClient();

    public void converter(Conversao conversao) {
        double taxa = apiClient.obterTaxa(conversao.getOrigem().getCodigo(), conversao.getDestino().getCodigo());
        double resultado = conversao.getValor() * taxa;

        System.out.printf("\n%.2f %s = %.2f %s\n",
                conversao.getValor(),
                conversao.getOrigem().getCodigo(),
                resultado,
                conversao.getDestino().getCodigo());
    }
}

// Classe principal que executa o menu e interage com o usuário
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Conversor conversor = new Conversor();

        while (true) {
            System.out.println("\n===== CONVERSOR DE MOEDAS =====");
            System.out.println("1 - USD → BRL");
            System.out.println("2 - BRL → USD");
            System.out.println("3 - EUR → BRL");
            System.out.println("4 - BRL → EUR");
            System.out.println("5 - GBP → BRL");
            System.out.println("6 - ARS → BRL");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();

            if (opcao == 0) break;

            System.out.print("Digite o valor a ser convertido: ");
            double valor = sc.nextDouble();

            Moeda origem = null;
            Moeda destino = null;

            switch (opcao) {
                case 1 -> {
                    origem = new Moeda("USD", "Dólar Americano");
                    destino = new Moeda("BRL", "Real Brasileiro");
                }
                case 2 -> {
                    origem = new Moeda("BRL", "Real Brasileiro");
                    destino = new Moeda("USD", "Dólar Americano");
                }
                case 3 -> {
                    origem = new Moeda("EUR", "Euro");
                    destino = new Moeda("BRL", "Real Brasileiro");
                }
                case 4 -> {
                    origem = new Moeda("BRL", "Real Brasileiro");
                    destino = new Moeda("EUR", "Euro");
                }
                case 5 -> {
                    origem = new Moeda("GBP", "Libra Esterlina");
                    destino = new Moeda("BRL", "Real Brasileiro");
                }
                case 6 -> {
                    origem = new Moeda("ARS", "Peso Argentino");
                    destino = new Moeda("BRL", "Real Brasileiro");
                }
                default -> {
                    System.out.println("\nOpção inválida. Tente novamente.");
                    continue;
                }
            }

            Conversao conversao = new Conversao(origem, destino, valor);
            conversor.converter(conversao);
        }

        System.out.println("\nObrigado por utilizar o conversor de moedas!");
    }
}
