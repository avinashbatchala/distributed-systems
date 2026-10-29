
package ex3;

import ex3.generated.*;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class DatabaseClient {

    private final String host;
    private final int port;

    public DatabaseClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private DatabaseResponse call(DatabaseRequest request)
            throws IOException {

        try (Socket socket = new Socket()) {

            socket.connect(
                    new InetSocketAddress(host, port), 5000);
            socket.setSoTimeout(10000);

            request.writeDelimitedTo(socket.getOutputStream());
            socket.getOutputStream().flush();

            DatabaseResponse response =
                    DatabaseResponse.parseDelimitedFrom(
                            socket.getInputStream());

            if (response == null) {
                throw new EOFException("No RPC response");
            }

            return response;
        }
    }

    public void addRecord(int index, String value)
            throws IOException {

        DatabaseRequest request =
                DatabaseRequest.newBuilder()
                        .setOperation(
                                DatabaseRequest.Operation.ADD_RECORD)
                        .setAddRecord(
                                AddRecordRequest.newBuilder()
                                        .setIndex(index)
                                        .setValue(value))
                        .build();

        DatabaseResponse response = call(request);

        if (!response.getSuccess()) {
            throw new IOException(response.getError());
        }

        System.out.println("Added: " + index + " -> " + value);
    }

    public String getRecord(int index) throws IOException {

        DatabaseRequest request =
                DatabaseRequest.newBuilder()
                        .setOperation(
                                DatabaseRequest.Operation.GET_RECORD)
                        .setGetRecord(
                                GetRecordRequest.newBuilder()
                                        .setIndex(index))
                        .build();

        DatabaseResponse response = call(request);

        if (!response.getSuccess()) {
            if (response.getError().startsWith("Record not found:")) {
                return null;
            }
            throw new IOException(response.getError());
        }

        return response.hasRecord() ? response.getRecord() : null;
    }

    public int getSize() throws IOException {

        DatabaseRequest request =
                DatabaseRequest.newBuilder()
                        .setOperation(
                                DatabaseRequest.Operation.GET_SIZE)
                        .setGetSize(GetSizeRequest.getDefaultInstance())
                        .build();

        DatabaseResponse response = call(request);

        if (!response.getSuccess()) {
            throw new IOException(response.getError());
        }

        return response.getSize();
    }
}
