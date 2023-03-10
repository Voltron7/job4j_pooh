package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "";
        final String NOT_FOUND = "404";
        final String SUCCESSFUL_REQUEST = "200";
        if ("POST".equals(req.httpRequestType())) {
            queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            queue.get(req.getSourceName()).add(req.getParam());
        } else {
            ConcurrentLinkedQueue<String> queueOrDefault = queue.getOrDefault(req.getSourceName(),
                    new ConcurrentLinkedQueue<>());
            text = queueOrDefault.poll();
            status = "".equals(text) ? NOT_FOUND : SUCCESSFUL_REQUEST;
        }
        return new Resp(text, status);
    }
}
