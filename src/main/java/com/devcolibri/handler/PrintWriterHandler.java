package com.devcolibri.handler;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class PrintWriterHandler {
    private static Map<String, PrintWriter> printWriters;

    public PrintWriterHandler() {
        printWriters = new HashMap<>();
    }

    public static Map<String, PrintWriter> getMap() {
        return printWriters;
    }
}

