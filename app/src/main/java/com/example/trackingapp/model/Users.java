package com.example.trackingapp.model;

import java.io.Serializable;

public class Users implements Serializable {
    String benutzer;
    String mail;
    String password;

    public Users() {
    }

    public Users(String benutzer, String mail, String password) {
        this.benutzer = benutzer;
        this.mail = mail;
        this.password = password;
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
