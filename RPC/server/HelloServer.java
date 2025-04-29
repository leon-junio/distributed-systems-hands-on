package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import interfaces.HelloInterface;

public class HelloServer implements HelloInterface {

    public HelloServer() {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Olá, mundo via RMI!";
    }

    public static void main(String[] args) {
        try {
            HelloServer obj = new HelloServer();
            HelloInterface stub = (HelloInterface) UnicastRemoteObject.exportObject(obj, 0);

            Registry registry = LocateRegistry.createRegistry(1099);

            registry.rebind("Hello", stub);

            System.out.println("Servidor pronto.");
        } catch (Exception e) {
            System.err.println("Exceção no servidor: " + e.toString());
            e.printStackTrace();
        }
    }
}
