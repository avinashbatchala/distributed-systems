
package ex3;

import ex3.generated.LogMessage;
import java.io.IOException;
import java.net.Socket;

public class LogClient {

    public static void send(
            String host, int port,
            LogMessage.Severity severity,
            String source,
            String text) throws IOException {

        if (text.contains("\n") || text.contains("\r")) {
            throw new IllegalArgumentException(
                    "Log text must be a single line");
        }

        LogMessage message = LogMessage.newBuilder()
                .setTimestamp(System.currentTimeMillis())
                .setSource(source)
                .setSeverity(severity)
                .setMessage(text)
                .build();

        try (Socket socket = new Socket(host, port)) {
            message.writeDelimitedTo(socket.getOutputStream());
            socket.getOutputStream().flush();
        }

        System.out.println("Log message sent: " + text);
        System.out.println(message.getTimestamp());
    }
}
