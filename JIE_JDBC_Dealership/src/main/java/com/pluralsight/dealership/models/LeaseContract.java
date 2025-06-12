package com.pluralsight.dealership.models;

public class LeaseContract extends Contract {

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
    }

    @Override
    public double getTotalPrice() {
        double price = getVehicleSold().getPrice();
        double endingValue = price * 0.50;
        double leaseFee = price * 0.07;

        return endingValue + leaseFee;
    }

    public double getMonthlyPayment() {
        double total = getTotalPrice();
        double monthlyRate = 0.04 / 12.0;
        int term = 36;
        return (total * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -term));
    }
}
