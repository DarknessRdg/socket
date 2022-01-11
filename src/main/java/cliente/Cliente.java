package cliente;

import configs.Config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        PrintWriter output = new PrintWriter(socket.getOutputStream());
        output.println(entrada);
        output.flush();
    }

    private static String getRespostaServidor() throws IOException {
        InputStreamReader resposta = new InputStreamReader(new BufferedInputStream(socket.getInputStream()));
        BufferedReader reader = new BufferedReader(resposta);
        return reader.readLine();
    }

    private static void abriConexao() throws IOException {
        socket = new Socket(Config.HOST, Config.PORT);
    }

}
