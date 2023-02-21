package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentHashMap;

public class CASMap {
    public static void main(String[] args) {
        Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
        String name = "weather";

        queue.putIfAbsent(name, new ConcurrentLinkedQueue<>());

        queue.get(name).add("value");

        var text = queue.getOrDefault(name, new ConcurrentLinkedQueue<>()).poll();
    }
}
