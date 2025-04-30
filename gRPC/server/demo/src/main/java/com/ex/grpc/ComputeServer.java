package com.ex.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class ComputeServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new ComputeImpl())
                .build()
                .start();
        System.out.println("gRPC Server started");
        server.awaitTermination();
    }

    static class ComputeImpl extends ComputeGrpc.ComputeImplBase {
        @Override
        public void square(Number request, StreamObserver<Number> responseObserver) {
            int v = request.getValue();
            Number reply = Number.newBuilder().setValue(v * v).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
