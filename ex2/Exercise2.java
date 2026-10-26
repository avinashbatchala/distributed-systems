package ex2;

import java.io.IOException;

/**
 * Demonstrates a TCP greeting exchange with a running server.
 *
 * @author Avinash Batchala, Bhargav Parvatha, Nithin Kumar
 */
public class Exercise2 {
    /**
     * Sends a greeting to localhost on port 6666 and prints the reply.
     *
     * @throws IOException if communication fails
     */
    static void main() throws IOException {
        Client client = new Client();
        client.startConnection("127.0.0.1", 6666);
        String response = client.sendMessage("stop");
        System.out.println(response);
        client.stopConnection();
    }
}
