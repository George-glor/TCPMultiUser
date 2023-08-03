package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Listener implements Runnable {
    private Socket socket;

    public Listener(Socket socket) { //socket som lyssna
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Process the received message here
                System.out.println("Received message: " + inputLine);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
