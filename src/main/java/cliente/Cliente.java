package cliente;

import configs.Config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class Cliente {
    private static final Scanner input = new Scanner(System.in);

    private static Socket socket;

    public static void main(String[] args) throws IOException {
        String entrada = getEntradaDoUsuario();

        abriConexao();
        enviarRequisicao(entrada);
        String response = getRespostaServidor();

        System.out.println("Resposta do servidor: " + response);
        socket.close();
        System.out.println("Conexão encerreda...");
    }

    private static String getEntradaDoUsuario() {
        System.out.println("O que deseja enviar ao servidor?");
        System.out.print(">>> ");

        return input.nextLine();
    }

    private static void enviarRequisicao(String entrada) throws IOException {
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        output.writeBytes(entrada);
        output.flush();
    }

    private static String getRespostaServidor() throws IOException {
        DataInputStream resposta = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(resposta));

        Optional<String> body = reader.lines().reduce((i, s) -> i + s);

        return body.orElse("Sem resposta do servidor :(   ...");
    }

    private static void abriConexao() throws IOException {
        socket = new Socket(Config.HOST, Config.PORT);
    }

}
