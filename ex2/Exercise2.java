package ex2;

public class Exercise2 {

    private static final String HOST = "localhost";
    private static final int PORT = 8080;
    private static final String PATH = "/index.html";

    private static final String PROXY_HOST = "127.0.0.1";
    private static final int PROXY_PORT = 3128;

    static void main() throws Exception {

        // Exercise 1(a): Raw socket client
        HttpClient.request(HOST, PORT, PATH);

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
