package com.devcolibri;

public class User {
    private String nickname;
    private String password;
    private boolean loggined;

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        loggined = false;
    }

    public String getNickname() {
        return nickname;
    }


    public String getPassword() {
        return password;
    }

    public boolean isLoggined() {
        return loggined;
    }

    public void setLoggined(boolean loggined) {
        this.loggined = loggined;
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
