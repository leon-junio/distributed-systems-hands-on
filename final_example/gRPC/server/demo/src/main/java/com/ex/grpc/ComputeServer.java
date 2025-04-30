package com.ex.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.ex.rmi.server.FibonacciInterface;

public class ComputeServer {
    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new ComputeFiboImpl())
                .build()
                .start();
        System.out.println("gRPC Server started");
        server.awaitTermination();
    }

    static class ComputeFiboImpl extends ComputeFiboGrpc.ComputeFiboImplBase {
        @Override
        public void fibonacci(Number request, StreamObserver<Number> responseObserver) {
            try {
                int v = request.getValue();
                Registry registry = LocateRegistry.getRegistry("localhost");

                FibonacciInterface stub = (FibonacciInterface) registry.lookup("FibonacciService");

                long result = stub.calculateFibonacci(v);
                Number reply = Number.newBuilder().setValue((int) result).build();
                responseObserver.onNext(reply);
                responseObserver.onCompleted();
            } catch (Exception e) {
                e.printStackTrace();
                responseObserver.onError(e);
            }
        }
    }
}
