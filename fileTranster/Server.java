import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 5000; // Port number
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is waiting for a client to connect...");

            Socket socket = serverSocket.accept(); // Accept connection
            System.out.println("Client connected!");

            // Receiving file
            InputStream in = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(in);
            String fileName = dataInputStream.readUTF(); // Read file name
            long fileSize = dataInputStream.readLong(); // Read file size

            File receivedFile = new File("Received_" + fileName);
            FileOutputStream fileOut = new FileOutputStream(receivedFile);
            byte[] buffer = new byte[4096];
            int bytesRead;

            System.out.println("Receiving file: " + fileName);
            while (fileSize > 0 && (bytesRead = in.read(buffer, 0, (int) Math.min(buffer.length, fileSize))) != -1) {
                fileOut.write(buffer, 0, bytesRead);
                fileSize -= bytesRead;
            }

            System.out.println("File received and saved as: " + receivedFile.getName());

            fileOut.close();
            dataInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
