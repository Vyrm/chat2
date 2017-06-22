package com.devcolibri;

import java.net.Socket;

public class User {
    private Socket socket;
    private String nickname;

    public User(Socket socket, String nickname) {
        this.socket = socket;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return nickname != null ? nickname.equals(user.nickname) : user.nickname == null;
    }

    @Override
    public int hashCode() {
        return nickname != null ? nickname.hashCode() : 0;
    }

}
