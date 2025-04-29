package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import interfaces.FibonacciInterface;

public class FibonacciClient {

    private FibonacciClient() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");

            FibonacciInterface stub = (FibonacciInterface) registry.lookup("FibonacciService");

            System.out.print("Digite o valor de n para calcular o Fibonacci: ");
            int n = scanner.nextInt();

            long result = stub.calculateFibonacci(n);
            System.out.println("Fibonacci de " + n + " é: " + result);
        } catch (Exception e) {
            System.err.println("Exceção no cliente: " + e.toString());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}
