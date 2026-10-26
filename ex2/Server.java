package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Handles one client message over TCP.
 *
 * @author Avinash Batchala, Bhargav Parvatha, Nithin Kumar
 */
public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Waits for a client and replies to its message.
     *
     * @param port the listening port
     * @throws IOException if communication fails
     */
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String message = in.readLine();
        if ("hello".equals(message)) {
            out.println("hello client, I have received your message");
        }

        if ("stop".equalsIgnoreCase(message)) {
            out.println("Stopping the server");
            stop();
        }

    }

    /**
     * Closes the streams and sockets.
     *
     * @throws IOException if closing fails
     */
    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Server is stopped by client");
    }

    /**
     * Starts the server on port 6666.
     *
     * @throws IOException if communication fails
     */
    static void main() throws IOException {
        Server server = new Server();
        server.start(6666);
    }
}
