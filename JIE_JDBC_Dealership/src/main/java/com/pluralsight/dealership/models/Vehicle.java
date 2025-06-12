package com.pluralsight.dealership.models;

public class Vehicle {
    private int vehicle_id; // primary key in DB
    private String vin;
    private int year;
    private double price;
    private String make;
    private String model;
    private String color;
    private int dealership_id; // foreign key


    public Vehicle(int vehicle_id, String vin, int year, String make, String model, String color, double price, int dealership_id) {
        this.vehicle_id = vehicle_id;
        this.vin = vin;
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
        this.price = price;
        this.dealership_id = dealership_id;
    }

    public Vehicle(String vin, int year, String make, String model, String color, double price, int dealership_id) {
        this(0, vin, year, make, model, color, price, dealership_id);
    }


    public int getVehicle_id() { return vehicle_id; }

    public void setVehicle_id(int vehicleId) { this.vehicle_id = vehicleId; }

    public String getVin() {
        return vin;
    }
    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) { this.year = year; }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) { this.price = price; }

    public String getMake() {
        return make;
    }
    public void setMake(String make) { this.make = make; }

    public String getModel() {
        return model;
    }
    public void setModel(String model) { this.model = model; }

    public String getColor() {
        return color;
    }
    public void setColor(String color) { this.color = color; }

    public int getDealership_id() {
        return dealership_id;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | VIN: %s | Year: %d | Make: %s | Model: %s | Color: %s | Price: $%.2f | Dealership ID: %d",
                vehicle_id, vin, year, make, model, color, price, dealership_id);
    }
}