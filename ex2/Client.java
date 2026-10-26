package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Sends messages to a TCP server and reads its replies.
 *
 * @author Avinash Batchala, Bhargav Parvatha, Nithin Kumar
 */
public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    /**
     * Connects to the server.
     *
     * @param ip   the server address
     * @param port the server port
     * @throws IOException if the connection fails
     */
    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    /**
     * Sends a message and waits for a reply.
     *
     * @param msg the message to send
     * @return the reply, or {@code null} if the server closes the stream
     * @throws IOException if reading the reply fails
     */
    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    /**
     * Closes the streams and socket.
     *
     * @throws IOException if closing fails
     */
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
