package ex4;

import ex4.generated.*;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class DatabaseServer {
    private static final int PORT = 50051;

    private static final class DatabaseService extends DataBaseGrpc.DataBaseImplBase {
        private final ConcurrentMap<Integer, String> records = new ConcurrentHashMap<>();

        @Override
        public void getRecord(GetRecordRequest request,
                              StreamObserver<GetRecordResponse> observer) {
            String value = records.get(request.getIndex());
            GetRecordResponse response = GetRecordResponse.newBuilder()
                    .setFound(value != null)
                    .setRecord(value == null ? "" : value)
                    .build();
            observer.onNext(response);
            observer.onCompleted();
        }

        @Override
        public void addRecord(AddRecordRequest request,
                              StreamObserver<AddRecordResponse> observer) {
            records.put(request.getIndex(), request.getRecord());
            observer.onNext(AddRecordResponse.newBuilder().setSuccess(true).build());
            observer.onCompleted();
        }

        @Override
        public void getSize(GetSizeRequest request,
                            StreamObserver<GetSizeResponse> observer) {
            observer.onNext(GetSizeResponse.newBuilder().setSize(records.size()).build());
            observer.onCompleted();
        }
    }

    static void main() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(PORT)
                .addService(new DatabaseService())
                .build()
                .start();
        System.out.println("Database gRPC server listening on port " + PORT);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdown));
        server.awaitTermination();
    }
}
