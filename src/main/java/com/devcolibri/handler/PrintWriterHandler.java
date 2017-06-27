package com.devcolibri.handler;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class PrintWriterHandler {
    private ConcurrentHashMap<String, PrintWriter> printWriters;

    public PrintWriterHandler() {
        printWriters = new ConcurrentHashMap<>();
    }

    public ConcurrentHashMap<String, PrintWriter> getMap() {
        return printWriters;
    }
}
