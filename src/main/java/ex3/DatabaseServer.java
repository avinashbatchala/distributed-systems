package ex3;

import ex3.generated.DatabaseRequest;
import ex3.generated.DatabaseResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Serves database requests over TCP using protobuf messages.
 *
 * @author Avinash Batchala
 * @author Bhargav Parvatha
 * @author Nithin Gangavarapu
 */
public class DatabaseServer {

    private static final Map<Integer, String> records =
            new ConcurrentHashMap<>();

    /**
     * Starts the server on port 9091 or the port given as the first argument.
     *
     * @param args optional port number
     * @throws IOException if the server cannot listen for connections
     */
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

    /**
     * Handles one client request.
     *
     * @param socket the connected client socket
     */
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

    /**
     * Executes a request and builds the response.
     *
     * @param request the request to execute
     * @return the response
     */
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

    /**
     * Creates an error response.
     *
     * @param message the error message
     * @return the error response
     */
    private static DatabaseResponse error(String message) {
        return DatabaseResponse.newBuilder()
                .setSuccess(false)
                .setError(message)
                .build();
    }
}
