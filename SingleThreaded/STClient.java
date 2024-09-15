import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class STClient {

    public void run() {
        int port = 8080;
        try {
            InetAddress address = InetAddress.getByName("localhost"); // Get the IP address of the local machine as the
                                                                      // server is running on the same machine.
            try (Socket socket = new Socket(address, port);
                    PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader fromSocket = new BufferedReader(
                            new java.io.InputStreamReader(socket.getInputStream()))) {

                System.out.println("Connected to server on port " + port);
                toSocket.println("Hello from the client");
                String serverResponse = fromSocket.readLine();
                if (serverResponse != null) {
                    System.out.println("Server says: " + serverResponse);
                } else {
                    System.out.println("No response from server");
                }
                toSocket.close();
                fromSocket.close();
                socket.close();
            } catch (IOException e) {
                System.err.println("Error communicating with server: " + e.getMessage());
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        STClient client = new STClient();
        client.run();
    }
}
