package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler extends Thread {

    private Socket client;

    public ClientHandler(Socket socket) throws IOException {
        client = socket;
    }

    public void run() {

        PrintWriter out = null;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                for (Socket socket: server.clients) {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(inputLine);
                }
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try{
                if(client != null){
                    client.close();
                    server.clients.remove(this.client);
                }
                in = null;
                out = null;
            }catch (IOException e){}
        }
    }
}
public class server {

    static ArrayList<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(12345);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clients.add(clientSocket);
            clientHandler.start();
        }
    }
}