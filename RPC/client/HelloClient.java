package client;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.HelloInterface;

public class HelloClient {

    private HelloClient() {
    }

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");

            HelloInterface stub = (HelloInterface) registry.lookup("Hello");

            String response = stub.sayHello();
            System.out.println("Resposta do servidor: " + response);
        } catch (Exception e) {
            System.err.println("Exceção no cliente: " + e.toString());
            e.printStackTrace();
        }
    }
}
