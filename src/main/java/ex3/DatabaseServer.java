
package ex3;

import ex3.generated.DatabaseRequest;
import ex3.generated.DatabaseResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseServer {

    private static final Map<Integer, String> records =
            new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        int port = args.length > 0
                ? Integer.parseInt(args[0]) : 9091;

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Database server on port " + port);

            while (true) {
                Socket client = server.accept();
                new Thread(() -> handle(client)).start();
            }
        }
    }

    private static void handle(Socket socket) {
        try (socket) {
            socket.setSoTimeout(10000);

            DatabaseRequest request =
                    DatabaseRequest.parseDelimitedFrom(
                            socket.getInputStream());

            if (request == null) return;

            DatabaseResponse response = execute(request);

            response.writeDelimitedTo(socket.getOutputStream());
            socket.getOutputStream().flush();

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }

    private static DatabaseResponse execute(DatabaseRequest request) {

        DatabaseResponse.Builder response =
                DatabaseResponse.newBuilder();

        switch (request.getOperation()) {

            case ADD_RECORD -> {
                if (!request.hasAddRecord()) {
                    return error("Missing addRecord arguments");
                }

                int index = request.getAddRecord().getIndex();
                String value = request.getAddRecord().getValue();

                records.put(index, value);
                response.setSuccess(true);
            }

            case GET_RECORD -> {
                if (!request.hasGetRecord()) {
                    return error("Missing getRecord arguments");
                }

                int index = request.getGetRecord().getIndex();
                String value = records.get(index);

                if (value != null) {
                    response.setSuccess(true);
                    response.setRecord(value);
                } else {
                    response.setSuccess(false);
                    response.setError("Record not found: " + index);
                }
            }

            case GET_SIZE -> {
                if (!request.hasGetSize()) {
                    return error("Missing getSize arguments");
                }

                response.setSuccess(true);
                response.setSize(records.size());
            }

            default -> {
                return error("Unknown operation");
            }
        }
        return response.build();
    }

    private static DatabaseResponse error(String message) {
        return DatabaseResponse.newBuilder()
                .setSuccess(false)
                .setError(message)
                .build();
    }
}
