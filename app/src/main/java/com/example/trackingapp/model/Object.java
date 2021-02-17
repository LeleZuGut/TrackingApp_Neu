package com.example.trackingapp.model;

import java.io.Serializable;

public class Object implements Serializable
{
    int id;
    String name;
    String inventoryNumber;
    String status;
    String userChanged;
    String repairmessage;

    public Object(int id, String name, String inventoryNumber, String status) {
        this.id = id;
        this.name = name;
        this.inventoryNumber = inventoryNumber;
        this.status = status;

    }



    public Object() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserChanged() {
        return userChanged;
    }

    public void setUserChanged(String userChanged) {
        this.userChanged = userChanged;
    }

    public String getRepairmessage() {
        return repairmessage;
    }

    public void setRepairmessage(String repairmessage) {
        this.repairmessage = repairmessage;
    }

    @Override
    public String toString() {
        return name;
    }
}
