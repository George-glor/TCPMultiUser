package src;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private List<Socket> clients; // lista för att koppla med klient
    private ServerSocket serverSocket;

    public Server() {
        clients = new ArrayList<>();
    }

    public void start(int port) {  //trådning
        try {
            serverSocket = new ServerSocket(port); // server s
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // accepterar klient koppling
                clients.add(clientSocket); // lägg till en klient koplling för list ock koppla klient med


                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();

                System.out.println("New client connected: " + clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler implements Runnable {  // inne i klassen
        private Socket clientSocket;
        private Scanner in;
        private PrintWriter out;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                // Initialize input och output streams för att kommencera med klient
                in = new Scanner(clientSocket.getInputStream());
                out = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    if (in.hasNextLine()) {
                        String message = in.nextLine(); // Läsa message från klinet
                        System.out.println("Received message: " + message);
                        broadcast(message); // Broadcast the message to all connected clients
                    }
                }
            } finally {
                clients.remove(clientSocket); // Ta bort kilent från server
                System.out.println("Client disconnected: " + clientSocket);
            }
        }

        private void broadcast(String message) {
            for (Socket client : clients) {
                try {
                    PrintWriter clientOut = new PrintWriter(client.getOutputStream(), true); //autoflush
                    clientOut.println(message); // skicka meddlenade för varje inkopplad klient
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(5555); // börja port
    }
}
