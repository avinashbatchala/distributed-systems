package ex2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Sends HTTP GET requests using a raw socket and prints the response.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
public class HttpClient {

    /**
     * Requests a resource and prints the complete HTTP response.
     *
     * @param host the server hostname or IP address
     * @param port the server port
     * @param path the resource path, starting with a slash
     * @throws IOException if the connection or request fails
     */
    public static void request(
            String host, int port, String path) throws IOException {

        try (Socket socket = new Socket()) {
            socket.connect(
                    new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(5000);

            String request =
                    "GET " + path + " HTTP/1.1\r\n" +
                            "Host: " + host + ":" + port + "\r\n" +
                            "Connection: close\r\n\r\n";

            OutputStream out = socket.getOutputStream();
            out.write(request.getBytes(
                    StandardCharsets.US_ASCII));
            out.flush();

            InputStream in = socket.getInputStream();

            System.out.println("HTTP CLIENT OUTPUT:: \n");
            in.transferTo(System.out);
            System.out.println("\n-----------------------\n");
        }
    }
}
