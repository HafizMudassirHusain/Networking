import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000); // Listen on port 5000
            System.out.println("Server is running... Waiting for client connection");

            Socket socket = serverSocket.accept(); // Accept client connection
            System.out.println("Client connected!");

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String clientMessage;
            while ((clientMessage = input.readLine()) != null) {
                System.out.println("Client: " + clientMessage);
                output.println("Message received: " + clientMessage);
            }

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
