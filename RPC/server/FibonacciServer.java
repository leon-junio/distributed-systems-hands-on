package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.FibonacciInterface;

public class FibonacciServer implements FibonacciInterface {

    public FibonacciServer() {}

    @Override
    public long calculateFibonacci(int n) throws RemoteException {
        if (n <= 1) return n;
        long a = 0, b = 1, c;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {
        try {
            FibonacciServer obj = new FibonacciServer();
            FibonacciInterface stub = (FibonacciInterface) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("FibonacciService", stub);

            System.out.println("Servidor pronto.");
        } catch (Exception e) {
            System.err.println("Exceção no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
