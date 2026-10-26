package ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer {
    private static final int PORT = 8080;
    private static final Path ROOT =
            Path.of("ex2/www").toAbsolutePath().normalize();

    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Listening on port " + PORT);

            while (true) {
                Socket client = server.accept();

                new Thread(() -> handleClient(client)).start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        try (socket) {
            socket.setSoTimeout(60000);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(),
                            StandardCharsets.US_ASCII));

            OutputStream out = socket.getOutputStream();

            String requestLine = reader.readLine();
            if (requestLine == null) return;

            System.out.println(
                    Thread.currentThread().getName()
                            + ": " + requestLine);

            String line;
            while ((line = reader.readLine()) != null
                    && !line.isEmpty()) {
                // Consume HTTP request headers
            }

            String[] parts = requestLine.split(" ");
            if (parts.length != 3 ||
                    !parts[0].equals("GET")) {
                send(out, 400, "Bad Request",
                        "Invalid GET request");
                return;
            }

            String path = parts[1].split("\\?", 2)[0];
            if (path.equals("/")) path = "/index.html";

            Path file = ROOT.resolve(
                    path.substring(1)).normalize();

            if (!file.startsWith(ROOT)) {
                send(out, 403, "Forbidden", "Access denied");
                return;
            }

            if (!Files.isRegularFile(file)) {
                send(out, 404, "Not Found", "File not found");
                return;
            }

            byte[] body = Files.readAllBytes(file);

            String type = Files.probeContentType(file);
            if (type == null) {
                type = "application/octet-stream";
            }

            String headers =
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: " + type + "\r\n" +
                            "Content-Length: " + body.length + "\r\n" +
                            "Connection: close\r\n\r\n";

            out.write(headers.getBytes(
                    StandardCharsets.US_ASCII));
            out.write(body);
            out.flush();

        } catch (IOException e) {
            System.err.println("Client error: " + e);
        }
    }

    private static void send(
            OutputStream out, int status,
            String reason, String message) throws IOException {

        byte[] body = message.getBytes(
                StandardCharsets.UTF_8);

        String headers =
                "HTTP/1.1 " + status + " " + reason + "\r\n" +
                        "Content-Type: text/plain; charset=utf-8\r\n" +
                        "Content-Length: " + body.length + "\r\n" +
                        "Connection: close\r\n\r\n";

        out.write(headers.getBytes(
                StandardCharsets.US_ASCII));
        out.write(body);
        out.flush();
    }
}
