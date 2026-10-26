package ex2;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class ProxyClient {

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
