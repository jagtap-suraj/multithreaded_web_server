import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {
    // We are using functional interface as we can pass it as an argument.
    public Consumer<Socket> getConsumer() {
        // Create a lambda that doesn't return anything
        return (clientSocket) -> {
            try {
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server");
                toClient.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8080;
        Server server = new Server();
        try {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                serverSocket.setSoTimeout(20000);
                System.out.println("Server is listening on port " + port);
                while (true) {
                    Socket acceptedSocket = serverSocket.accept();
                    // As soon as it gets accept we don't have to send anything to the client but
                    // create a new thread
                    // for each socket it accepts.
                    Thread thread = new Thread(() -> server.getConsumer().accept(acceptedSocket));
                    // In this thread we are
                    // going to communicate
                    // with the client
                    // associated with the
                    // respective socket.
                    thread.start();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
