package com.pluralsight.dealership.models;

import java.util.ArrayList;
import java.util.List;

public class Dealership {
    private int dealership_id;
    private String name;
    private String address;
    private String phone;

    public Dealership(int dealership_id, String name, String address, String phone) {
        this.dealership_id = dealership_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getDealership_id() { return dealership_id; }

    @Override
    public String toString() {
        return String.format("Dealership ID: %d | Name: %s | Address: %s | Phone: %s", dealership_id, name, address, phone);
    }
}

