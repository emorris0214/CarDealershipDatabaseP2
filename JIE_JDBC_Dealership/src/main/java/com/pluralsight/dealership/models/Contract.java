package com.pluralsight.dealership.models;

public abstract class Contract {
    protected String date;
    protected String leaseEndDate;
    protected String customerName;
    protected Vehicle vehicleSold;

    public Contract(String date, String customerName, String leaseEndDate, Vehicle vehicleSold) {
        this.date = date;
        this.leaseEndDate = leaseEndDate;
        this.customerName = customerName;
        this.vehicleSold = vehicleSold;
    }

    public String getDate() { return date; }
    public String getCustomerName() { return customerName; }
    public Vehicle getVehicleSold() {return vehicleSold; }

    public abstract double getTotalPrice();

}
