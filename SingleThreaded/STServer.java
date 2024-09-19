import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class STServer {

    // Defining ServerLogic in the run method
    public void run() {
        int port = 8080; // Port to listen on
        try (ServerSocket serverSocket = new ServerSocket(port)) { // Open a socket on the given port
            serverSocket.setSoTimeout(10000); // Set a timeout of 20 seconds, after which the socket will close
            System.out.println("Server is listening on port: " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                        PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader fromClient = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()))) {
                    // The Server will pause here until a client connects, and if the client does
                    // not connect within 20 seconds, the socket will close

                    System.out.println("Connected to " + clientSocket.getRemoteSocketAddress());
                    toClient.println("Hello from the server");

                    String clientMessage = fromClient.readLine();
                    if (clientMessage != null) {
                        System.out.println("Client says: " + clientMessage);
                    }
                    toClient.close();
                    fromClient.close();
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        STServer server = new STServer(); // Since run is not a static method it's not in the memory, so we need to
                                          // create an object of the class to call the run method.
        server.run();
    }
}

/*
 * BufferedReader:
 * Used in inputstream, it combines all the incoming bytes and shows the result.
 * It requires a Reader to be passed as an argument for that we use
 * InputStreamReader that takes inputstream as an argument and converts it into
 * a InputStreamReader(Reader).
 * PrintWriter:
 * Used in outputstream, it sends the data in the form of bytes.
 * It requires an OutputStream to be passed as an argument.
 */