package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private String username;
    private Socket socket;
    private PrintWriter out;

    public GUI() {
        setTitle("Chat Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setPreferredSize(new Dimension(380, 300));

        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(300, 30));

        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(80, 30));

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(inputField);
        panel.add(sendButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void connectToServer(String serverAddress, int portNumber) {
        try {
            socket = new Socket(serverAddress, portNumber);
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new Listener(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String message = inputField.getText();
        out.println(username + ": " + message);
        inputField.setText("");
    }

    private void setUsername(String username) {
        this.username = username;
        setTitle("Chat Application - " + username);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatArea.append("You are logged in as: " + username + "\n");
                inputField.requestFocus();
            }
        });
    }

    private class Listener implements Runnable {
        private Socket socket;

        public Listener(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    chatArea.append(inputLine + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI chatGUI = new GUI();
                String username = JOptionPane.showInputDialog(chatGUI, "Enter your name:");
                chatGUI.setUsername(username);

                String serverAddress = "127.0.0.1"; // man kan välja med att skirva lockal host eller 127.0.0.1
                int portNumber = 5555; // Server port number
                chatGUI.connectToServer(serverAddress, portNumber);
            }
        });
    }
}
