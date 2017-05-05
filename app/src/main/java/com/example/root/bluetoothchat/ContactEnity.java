package com.example.root.bluetoothchat;

import java.io.Serializable;

/**
 * Created by DAN on 29/04/2017.
 */

public class ContactEnity implements Serializable{
    private String Name;
    private String address;
    private boolean connected;
    public ContactEnity(String name,String add, String cnt) {
        this.Name = name;
        this.address = add;
        if (cnt == "true") {
            this.connected = true;
        }
        else {
            this.connected = false;
        }
    }

    public String getName() {
        return Name;
    }

    public String getAddress() { return address;}

    public boolean getConnected(){
        return connected;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public void setAddress(String add) {
        this.address = add;
    }

    public void setConnected(Boolean cnt) {
        this.connected = cnt;
    }
}
