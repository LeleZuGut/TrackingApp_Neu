package com.example.trackingapp;

public class Object
{
    int id;
    String name;
    String InventoryNumber;
    String status;
    String userChanged;

    public Object(int id, String name, String inventoryNumber, String status) {
        this.id = id;
        this.name = name;
        InventoryNumber = inventoryNumber;
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
        return InventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        InventoryNumber = inventoryNumber;
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

    @Override
    public String toString() {
        return id + "->" + name;
    }
}
