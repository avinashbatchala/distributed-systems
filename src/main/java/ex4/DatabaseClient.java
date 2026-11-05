package ex4;

import ex4.generated.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.TimeUnit;

public final class DatabaseClient {

    static String host = "localhost";

    static ManagedChannel channel = ManagedChannelBuilder.forAddress(host, 50051)
            .usePlaintext()
            .build();

    static DataBaseGrpc.DataBaseBlockingStub stub = DataBaseGrpc.newBlockingStub(channel)
            .withDeadlineAfter(5, TimeUnit.SECONDS);

    static void main() throws InterruptedException {


        try {
            int[] keys = {4101, 4102, 4103, 4104, 4105, 4106};
            String[] values = {"Appen", "Ahrensburg", "Wedel", "Aumühle", "Seevetal", "Quickborn"};
            for (int i = 0; i < keys.length; i++) {
                AddRecordResponse result = stub.addRecord(AddRecordRequest.newBuilder()
                        .setIndex(keys[i]).setRecord(values[i]).build());

                System.out.printf("addRecord(%d, %s): %b%n", keys[i], values[i], result.getSuccess());
            }

            for (int key : new int[]{4103, 4107}) {
                GetRecordResponse result = stub.getRecord(GetRecordRequest.newBuilder()
                        .setIndex(key).build());
                System.out.printf("getRecord(%d): %s%n", key,
                        result.getFound() ? result.getRecord() : "NOT FOUND");
            }
            int size = stub.getSize(GetSizeRequest.newBuilder().build()).getSize();
            System.out.println("getSize(): " + size);
        } finally {
            channel.shutdown();
            channel.awaitTermination(5, TimeUnit.SECONDS);
        }
    }

    static String getRecord(int key) {

        GetRecordResponse result = stub.getRecord(GetRecordRequest.newBuilder()
                .setIndex(key).build());

        return result.getFound() ? result.getRecord() : "NOT FOUND";
    }

    static int getSize() {
        return stub.getSize(GetSizeRequest.newBuilder().build()).getSize();
    }

    static boolean addRecord(int key, String value) {
        AddRecordResponse result = stub.addRecord(AddRecordRequest.newBuilder()
                .setIndex(key).setRecord(value).build());

        return result.getSuccess();
    }


}
