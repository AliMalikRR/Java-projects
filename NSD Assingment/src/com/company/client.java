package com.company;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

class ServerHandler extends Thread {
    Socket socket;
    public ServerHandler(Socket socket){
        this.socket = socket;
    }
    public void run(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

public class client {

    public static void main(String[] args) throws IOException {

        String user;
        while (true){
            System.out.println("Enter User Name:");
            user = new Scanner(System.in).nextLine();
            if(user.length() > 0 || user != null) {
                System.out.println("Connected Enter Message:");
                break;
            }
        }

        Socket socket = new Socket("localhost", 12345);
        ServerHandler reciever = new ServerHandler(socket);
        reciever.start();

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(user +": "+ userInput);
        }
    }
}
