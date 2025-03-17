import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverAddress = "localhost"; // Change this to the server's IP if running on a network
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port)) {
            System.out.println("Connected to Server!");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter file path to send: ");
            String filePath = scanner.nextLine();
            File file = new File(filePath);

            if (!file.exists()) {
                System.out.println("File does not exist!");
                return;
            }

            // Sending file
            FileInputStream fileInput = new FileInputStream(file);
            OutputStream out = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            
            dataOutputStream.writeUTF(file.getName()); // Send file name
            dataOutputStream.writeLong(file.length()); // Send file size
            
            byte[] buffer = new byte[4096];
            int bytesRead;

            System.out.println("Sending file: " + file.getName());
            while ((bytesRead = fileInput.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            System.out.println("File sent successfully!");

            fileInput.close();
            dataOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
