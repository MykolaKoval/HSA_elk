package com.course.hsa.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    @Value("${app.processing.concurrency}")
    private final Integer concurrency;

    public void executeConcurrently(Runnable task, Long executeCount) {
        log.info("Execution started, concurrency level: {}", concurrency);
        ExecutorService executor = Executors.newFixedThreadPool(concurrency);
        submit(executor, task, executeCount).run();
    }

    private Runnable submit(ExecutorService executor, Runnable task, Long count) {
        return () -> {
            for (long i = 0; i < count; i++) {
                executor.submit(task);
            }
            log.info("Submitted {} tasks", count);
        };
    }

}
