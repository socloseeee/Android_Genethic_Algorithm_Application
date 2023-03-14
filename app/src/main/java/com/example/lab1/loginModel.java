package com.example.lab1;

public class loginModel {
    private String login;
    private String password;
    private String botToken = "42botToken42";

    public loginModel(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
    public String getBotToken() {
        return botToken;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
