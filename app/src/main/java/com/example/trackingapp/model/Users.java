package com.example.trackingapp.model;

public class Users {
    int id;
    String benutzer;
    String mail;
    String password;

    public Users() {
    }

    public Users(int id, String benutzer, String mail, String password) {
        this.id = id;
        this.benutzer = benutzer;
        this.mail = mail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(String benutzer) {
        this.benutzer = benutzer;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
