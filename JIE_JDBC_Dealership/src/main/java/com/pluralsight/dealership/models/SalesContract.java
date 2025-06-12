package com.pluralsight.dealership.models;

public class SalesContract extends Contract {
    protected boolean isFinanced;

    public SalesContract(String date, String customerName, Vehicle vehicleSold) {
        super(date, customerName, vehicleSold);
    }


    @Override
    public double getTotalPrice() {
        double price = getVehicleSold().getPrice();
        double tax = price * 0.05;
        double recordingFee = 100;
        double processingFee = (price < 10000) ? 295.00 : 495.00;

        return price + tax + recordingFee + processingFee;
    }

    /*@Override
    public double getMonthlyPayment() {
        if (!isFinanced) return 0.0;

        double totalPrice = getTotalPrice();
        double interestRate;
        int loanTerm;

        if (getVehicleSold().getPrice() >= 10000) {
            interestRate = 0.0425;
            loanTerm = 48;
        } else {
            interestRate = 0.0525;
            loanTerm = 24;
        }

        double monthlyRate = interestRate / 12.0;
        return (totalPrice * monthlyRate) / (1- Math.pow(1 + monthlyRate, -loanTerm));
    }*/

}
