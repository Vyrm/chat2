package com.devcolibri.handler;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PrintWriterHandler {
    private static volatile PrintWriterHandler printWriterHandler;
    private static Map<String, PrintWriter> printWriters;

    private PrintWriterHandler() {
        printWriters = new HashMap<>();
    }

    public static PrintWriterHandler getInstance() {
        if (printWriterHandler == null) {
            synchronized (PrintWriterHandler.class) {
                if (printWriterHandler == null) {
                    printWriterHandler = new PrintWriterHandler();
                }
            }
        }

        return printWriterHandler;
    }

    public Map<String, PrintWriter> getMap() {
        return printWriters;
    }
}

