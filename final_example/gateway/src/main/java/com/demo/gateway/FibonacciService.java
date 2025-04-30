package com.demo.gateway;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FibonacciService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final Map<String, CompletableFuture<Integer>> pendingResponses = new ConcurrentHashMap<>();

    @Cacheable(value = "fibonacci", key = "#n")
    public int fibonacci(int n) throws Exception {
        String correlationId = UUID.randomUUID().toString();
        CompletableFuture<Integer> future = new CompletableFuture<>();
        pendingResponses.put(correlationId, future);

        kafkaTemplate.send("fibo-1", correlationId + ":" + n);

        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            pendingResponses.remove(correlationId);
            throw new RuntimeException("Timeout esperando resposta do Kafka");
        }
    }

    @KafkaListener(topics = "fibo-2", groupId = "fibonacci-service")
    public void listen(ConsumerRecord<String, String> record) {
        String[] parts = record.value().split(":");
        if (parts.length == 2) {
            String correlationId = parts[0];
            int result = Integer.parseInt(parts[1]);

            CompletableFuture<Integer> future = pendingResponses.remove(correlationId);
            if (future != null) {
                future.complete(result);
            }
        }
    }
}