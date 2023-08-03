package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; //  ip
        int portNumber = 5555; //   port

        try (Socket socket = new Socket(serverAddress, portNumber)) {  //socket för att skicka meddelande
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = userInput.readLine()) != null) {
                // Send user input to the server
                out.println(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
