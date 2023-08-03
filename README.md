TCPMultiUser
Welcome to TCPMultiUser! This is a Java-based project that allows for multi-user communication over TCP/IP connections. With this application, multiple users can exchange messages through a server-client architecture.

Getting Started
To run the project locally, follow the instructions below:

Prerequisites
Java Development Kit (JDK) 8 or later installed on your system.
Git (optional) if you want to clone the repository.
Installation
You can either clone the repository using Git or download the ZIP archive of the project.

Clone with Git (optional)
bash
Copy code
git clone https://github.com/George-glor/TCPMultiUser.git
Download ZIP (alternative)
Click on the "Code" button on the repository page.
Select "Download ZIP."
Extract the ZIP archive to a directory of your choice.
Running the Application
Open a terminal or command prompt.
Navigate to the root directory of the project.
Start the Server
In the terminal, navigate to the "server" directory:
bash
Copy code
cd server
Compile the server Java files:
bash
Copy code
javac Server.java
Run the server:
bash
Copy code
java Server
The server should now be up and running, listening for incoming client connections.

Start a Client
Open a new terminal or command prompt (leave the server running in the previous terminal).
Navigate to the "client" directory:
bash
Copy code
cd ../client
Compile the client Java files:
bash
Copy code
javac Client.java
Run the client:
bash
Copy code
java Client
The application will prompt you to enter a username. Type your desired username and press Enter.
Now, you are ready to start sending and receiving messages with other connected users through the server.

Configuring Multi-User Support
By default, the application is set up to support multi-user communication. However, it's essential to check the Java settings to ensure that your system allows multiple instances of the client to run simultaneously.

Go to your Java installation directory.
Locate the "jre" folder.
Inside the "jre" folder, find the "lib" directory and navigate to the "security" folder.
Look for the "java.policy" file and open it with a text editor.
Add the following lines to grant necessary permissions for multi-user support:
plaintext
Copy code
grant {
    permission java.net.SocketPermission "localhost:1024-", "listen,resolve";
    permission java.net.SocketPermission "your_server_ip:1024-", "listen,resolve";
};
Replace "your_server_ip" with the IP address of your server machine. This step will allow multiple client instances to connect to the server.

Save the changes to the "java.policy" file and restart your Java applications if they were running.

Contributing
We welcome contributions to this project. Feel free to create issues or pull requests if you have any ideas, bug fixes, or improvements.

License
This project is licensed under the MIT License.
