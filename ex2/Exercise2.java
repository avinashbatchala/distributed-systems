package ex2;

import java.io.IOException;

/**
 * Demonstrates the socket, URL, and proxy HTTP clients.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
public class Exercise2 {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String PATH = "/index.html";

    private static final String PROXY_HOST = "stud.fh-wedel.de";
    private static final int PROXY_PORT = 80;
    private static final String PROXY_PATH = "/";

    /**
     * Runs the client examples using the configured server and proxy.
     *
     * @throws Exception if a client request fails
     */
    static void main() throws Exception {
        httpClient();
        urlClient();
      //  proxyClient();
    }

    // Exercise 1(a): Raw socket client
    // HttpClient.request("stud.fh-wedel.de", 80, "/");
    // HttpClient.request("192.168.1.50", 8080, "/");
    static void httpClient() throws IOException {
        HttpClient.request(HOST, PORT, PATH);
    }

    // Exercise 1(b): URL client
    static void urlClient() throws IOException {
        UrlClient.request("http://" + HOST + ":" + PORT + PATH);
    }

    // Exercise 1(c): Proxy client
    static void proxyClient() throws IOException {
        ProxyClient.request(
                "http://" + HOST + ":" + PORT + PROXY_PATH,
                PROXY_HOST,
                PROXY_PORT
        );
    }
}
