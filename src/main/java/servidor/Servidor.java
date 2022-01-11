package servidor;

import configs.Config;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Servidor {
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        initServerSocket();
    }

    private static void initServerSocket() throws IOException {
        System.out.println("Inciando servidor na porta " + Config.PORT + " ...\n");

        if (serverSocket == null) {
            serverSocket = new ServerSocket(Config.PORT);
        }

        InetAddress inet = serverSocket.getInetAddress();
        System.out.println("HostAddress: " + inet.getHostAddress());
        System.out.println("HostName: " + inet.getHostName());
        System.out.println("CanonicalName: " + inet.getCanonicalHostName());

        escutarRequisicoes();
    }

    private static void escutarRequisicoes() throws IOException {
        while (true) {
            Socket conexao = abrirConexaoSocke();
            processarConexao(conexao);
            conexao.close();
        }
    }

    private static Socket abrirConexaoSocke() throws IOException {
        return serverSocket.accept();
    }

    private static void processarConexao(Socket conexao) throws IOException {
        System.out.println("\n-------------\nConxeão aberta com: " + conexao.getInetAddress().getHostAddress());

        InputStreamReader in = new InputStreamReader(conexao.getInputStream());
        BufferedReader requisicaoReader = new BufferedReader(in);

        PrintWriter respostaStream = new PrintWriter(conexao.getOutputStream());

        String body = getBody(requisicaoReader);
        System.out.println("Body da requisicao: " + body + "\n");

        String response = getResponse(body);
        System.out.println("Resposta da requisicao: " + response + "\n");

        enviarResponsta(respostaStream, response);

        System.out.println("Resposta enviada com sucesso!");
    }

    private static void enviarResponsta(PrintWriter respostaStream, String resposta) throws IOException {
        respostaStream.println(resposta);
        respostaStream.flush();
    }

    private static String getBody(BufferedReader reader) throws IOException {
       return reader.readLine();
    }

    private static String getResponse(String body) {
        return simularAplicacaoWeb(body);
    }

    private static String simularAplicacaoWeb(String input) {
        return "O QUE VOCE DIGITOU, AGORA EM CAIXA ALTAS: " + input.toUpperCase();
    }
}
