package ex2;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

/**
 * Sends HTTP GET requests through an HTTP proxy and prints the response.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
public class ProxyClient {

    /**
     * Requests a URL through a proxy and prints its status and response body.
     *
     * @param urlString the URL to request
     * @param proxyHost the proxy hostname or IP address
     * @param proxyPort the proxy port
     * @throws IOException if the connection or request fails
     */
    public static void request(
            String urlString,
            String proxyHost,
            int proxyPort) throws IOException {

        URL url = URI.create(urlString).toURL();

        Proxy proxy = new Proxy(
                Proxy.Type.HTTP,
                new InetSocketAddress(proxyHost, proxyPort)
        );

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection(proxy);

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try {
            int status = connection.getResponseCode();
            System.out.println("HTTP Status: " + status);

            InputStream stream = status >= 400
                    ? connection.getErrorStream()
                    : connection.getInputStream();

            if (stream != null) {
                try (InputStream in = stream) {
                    in.transferTo(System.out);
                }
            }

            System.out.println();

        } finally {
            connection.disconnect();
        }
    }
}
