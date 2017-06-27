package com.devcolibri;

import com.devcolibri.engine.Server;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        new Server().run();
    }
}
