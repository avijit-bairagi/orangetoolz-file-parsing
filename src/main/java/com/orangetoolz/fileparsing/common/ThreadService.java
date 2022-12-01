package com.orangetoolz.fileparsing.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

@Service
public class ThreadService {

    private final ExecutorService executor;

    public ThreadService(@Value("${thread.size.max}") int maxThreadSize) {
        this.executor = Executors.newFixedThreadPool(maxThreadSize);
    }

    public <T extends Supplier<D>, D> CompletableFuture<D> addTask(T task) {

        return CompletableFuture.supplyAsync(task, executor);

    }
}
