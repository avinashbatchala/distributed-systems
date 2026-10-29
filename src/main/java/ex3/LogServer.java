package ex3;

import ex3.generated.LogMessage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

/**
 * Receives log messages and appends them to {@code server.log}.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
public class LogServer {

    private static final Path LOG_FILE = Path.of("server.log");

    /**
     * Starts the server on port 9090 or the port given as the first argument.
     *
     * @param args optional port number
     * @throws IOException if the server cannot listen for connections
     */
    static void main(String[] args) throws IOException {
        int port = args.length > 0
                ? Integer.parseInt(args[0]) : 9090;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Log server on port " + port);

            while (true) {
                Socket client = server.accept();

                new Thread(() -> handle(client)).start();
            }
        }
    }

    /**
     * Handles one client log message.
     *
     * @param socket the connected client socket
     */
    private static void handle(Socket socket) {
        try (socket) {
            socket.setSoTimeout(10000);

            LogMessage message = LogMessage.parseDelimitedFrom(
                    socket.getInputStream());

            if (message == null) return;

            if (message.getMessage().contains("\n")
                    || message.getMessage().contains("\r")
                    || message.getSource().contains("\n")
                    || message.getSource().contains("\r")) {
                throw new IOException("Invalid multiline log entry");
            }

            String entry = "%s [%s] [%s] %s%n".formatted(
                    Instant.ofEpochMilli(message.getTimestamp()),
                    message.getSeverity(),
                    message.hasSource() ? message.getSource() : "unknown",
                    message.getMessage());

            synchronized (LogServer.class) {
                Files.writeString(
                        LOG_FILE,
                        entry,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }

            System.out.print(entry);

        } catch (IOException e) {
            System.err.println("Log error: " + e.getMessage());
        }
    }
}
