package ex2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Sends HTTP GET requests using HttpURLConnection and prints the response.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
public class UrlClient {

    /**
     * Requests a URL and prints its status and response body.
     *
     * @param urlString the URL to request
     * @throws IOException if the connection or request fails
     */
    public static void request(String urlString) throws IOException {

        URL url = URI.create(urlString).toURL();

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try {
            int status = connection.getResponseCode();

            System.out.println("URL CLIENT OUTPUT:: \n");
            System.out.println("HTTP Status: " + status);

            InputStream stream = status >= 400
                    ? connection.getErrorStream()
                    : connection.getInputStream();

            if (stream != null) {
                try (InputStream in = stream) {
                    in.transferTo(System.out);
                    System.out.println("\n-----------------------\n");
                }
            }

            System.out.println();

        } finally {
            connection.disconnect();
        }
    }
}
