package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics =
            new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "";
        final String NOT_FOUND = "404";
        final String SUCCESSFUL_REQUEST = "200";
        if ("POST".equals(req.httpRequestType())) {
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> subscribersQueue = topics.get(req.getSourceName());
            if (subscribersQueue.equals(topics.get(req.getSourceName()))) {
                for (String keys : subscribersQueue.keySet()) {
                    subscribersQueue.get(keys).add(req.getParam());
                }
            }
        } else {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> subscribersQueue = topics.get(req.getSourceName());
            if (subscribersQueue.get(req.getParam()) != null) {
                text = subscribersQueue.get(req.getParam()).poll();
                status = "".equals(text) ? NOT_FOUND : SUCCESSFUL_REQUEST;
            }
            subscribersQueue.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
        }
        return new Resp(text, status);
    }
}
