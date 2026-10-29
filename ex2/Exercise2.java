
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
     * Runs the configured HTTP client examples.
     *
     * @throws Exception if a client request fails
     */
    static void main() throws Exception {
        httpClient();
        urlClient();
        // proxyClient();
    }

    /**
     * Exercise 1(a): Sends an HTTP GET request using a raw TCP socket.
     *
     * <p>Examples:
     * <pre>
     * HttpClient.request("stud.fh-wedel.de", 80, "/");
     * HttpClient.request("192.168.1.50", 8080, "/");
     * </pre>
     *
     * @throws IOException if socket communication fails
     */
    static void httpClient() throws IOException {
        HttpClient.request(HOST, PORT, PATH); //200
        //  HttpClient.request(HOST, PORT, "/../ex2/HttpServer.java"); // 403
        //  HttpClient.request(HOST, PORT, PATH + "1"); // 404
        //  HttpClient.request("stud.fh-wedel.de", 80, "/");
        //   HttpClient.request("example.com", 80, "/");
    }

    /**
     * Exercise 1(b): Sends an HTTP GET request using java.net.URL.
     *
     * <p>Example:
     * <pre>
     * UrlClient.request("https://google.com/");
     * </pre>
     *
     * @throws IOException if the HTTP request fails
     */
    static void urlClient() throws IOException {
        UrlClient.request("http://" + HOST + ":" + PORT + PATH); //200
        //    UrlClient.request("http://" + HOST + ":" + PORT + "/../ex2/HttpServer.java"); // 403
        // UrlClient.request("http://" + HOST + ":" + PORT + PATH + "1"); // 404
        // UrlClient.request("http://stud.fh-wedel.de:80/");
        //   UrlClient.request("http://example.com:80/");
    }

    /**
     * Exercise 1(c): Sends an HTTP GET request through a proxy server.
     *
     * <p>Example:
     * <pre>
     * ProxyClient.request(
     *     "stud.fh-wedel.de:80/",
     *     "127.0.0.1",
     *     3128
     * );
     * </pre>
     *
     * @throws IOException if proxy communication fails
     */
    static void proxyClient() throws IOException {
        ProxyClient.request(
                "http://" + HOST + ":" + PORT + PROXY_PATH,
                PROXY_HOST,
                PROXY_PORT
        );
    }
}
