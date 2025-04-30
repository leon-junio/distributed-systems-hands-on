package com.demo.gateway;

import java.util.concurrent.TimeoutException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/fibonacci")
@RequiredArgsConstructor
public class FibonacciController {

    private final FibonacciService fibonacciService;

    @GetMapping("/{n}")
    public ResponseEntity<Integer> getFibonacci(@PathVariable int n) {
        try {
            return ResponseEntity.ok(fibonacciService.fibonacci(n));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (TimeoutException e) {
            return ResponseEntity.status(504).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}