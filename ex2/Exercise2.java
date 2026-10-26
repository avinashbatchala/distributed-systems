package ex2;

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

    private static final String PROXY_HOST = "127.0.0.1";
    private static final int PROXY_PORT = 3128;

    /**
     * Runs the client examples using the configured server and proxy.
     *
     * @throws Exception if a client request fails
     */
    static void main() throws Exception {

        // Exercise 1(a): Raw socket client
        HttpClient.request(HOST, PORT, PATH);
        // HttpClient.request("stud.fh-wedel.de", 80, "/");
        // HttpClient.request("192.168.1.50", 8080, "/");

        // Exercise 1(b): URL client
         UrlClient.request("http://" + HOST + ":" + PORT + PATH);

        // Exercise 1(c): Proxy client
        ProxyClient.request(
                "https://example.com/",
                PROXY_HOST,
                PROXY_PORT
        );
    }
}
