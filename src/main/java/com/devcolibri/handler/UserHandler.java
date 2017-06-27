package com.devcolibri.handler;


import com.devcolibri.domain.User;

import java.sql.*;

public class UserHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/chat?autoReconnect=true&useSSL=false&" +
            "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String INSERT_NEW_USER = "INSERT INTO users VALUES(?,?)";
    private static final String GET_USER = "SELECT * FROM users WHERE nickname = ?";
    private Connection connection;
    private PreparedStatement preparedStatement;

    public UserHandler() {

    }

    public void addUser(String nickname, String password) {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(INSERT_NEW_USER);
            preparedStatement.setString(1, nickname);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String nickname) {
        User user = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(GET_USER);
            preparedStatement.setString(1, nickname);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getString("nickname"), resultSet.getString("password"));
            }
            connection.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}