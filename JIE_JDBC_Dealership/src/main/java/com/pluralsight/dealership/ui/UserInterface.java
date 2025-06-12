package com.pluralsight.dealership.ui;

import com.pluralsight.dealership.dao.*;
import com.pluralsight.dealership.models.Vehicle;
import com.pluralsight.dealership.models.SalesContract;
import com.pluralsight.dealership.models.LeaseContract;

import java.util.List;
import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner = new Scanner(System.in);
    private final InventoryDAO inventoryDAO = new InventoryDAO();
    private final VehicleDAO vehicleDAO = new VehicleDAO();
    private final DealershipDAO dealershipDAO = new DealershipDAO();
    private final SalesContractDAO salesContractDAO = new SalesContractDAO();
    private final LeaseContractDAO leaseContractDAO = new LeaseContractDAO();

    public void start() {
        boolean running = true;
        System.out.println("Welcome to the Car Dealership App!");

        while (running) {
            showMainMenu();
            int choice = promptInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    displayAllVehicles();
                    break;
                case 2:
                    recordSalesContract();
                    break;
                case 3:
                    recordLeaseContract();
                    break;
                case 4:
                    System.out.println("Thank you for using the Car Dealership App. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\nMain Menu");
        System.out.println("1. View All Vehicles");
        System.out.println("2. Record Sales Contract");
        System.out.println("3. Record Lease Contract");
        System.out.println("4. Exit");
    }

    private void displayAllVehicles() {
        List<Vehicle> vehicles = inventoryDAO.getAllVehicles();
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found in inventory.");
            return;
        }

        System.out.println("\nInventory Vehicles:");
        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }

    private void recordSalesContract() {
        System.out.println("\nRecord Sales Contract:");

        int vehicleId = promptInt("Enter Vehicle ID to sell: ");
        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

        if (vehicle == null) {
            System.out.println("Vehicle ID not found.");
            return;
        }

        String date = promptString("Enter contract date (YYYY-MM-DD): ");
        String customerName = promptString("Enter customer name: ");

        SalesContract contract = new SalesContract(date, customerName, vehicle);

        boolean saved = salesContractDAO.saveSalesContract(contract);
        if (saved) {
            System.out.println("Sales contract saved successfully.");
            vehicleDAO.removeVehicleById(vehicleId);
        } else {
            System.out.println("Error saving sales contract.");
        }
    }

    private void recordLeaseContract() {
        System.out.println("\nRecord Lease Contract:");

        int vehicleId = promptInt("Enter Vehicle ID to lease: ");
        Vehicle vehicle = vehicleDAO.getVehicleById(vehicleId);

        if (vehicle == null) {
            System.out.println("Vehicle ID not found.");
            return;
        }

        String date = promptString("Enter lease start date (YYYY-MM-DD): ");
        String leaseEndDate = promptString("Enter lease end date (YYYY-MM-DD): ");
        String customerName = promptString("Enter customer name: ");

        LeaseContract contract = new LeaseContract(date, customerName, vehicle, leaseEndDate);

        boolean saved = leaseContractDAO.saveLeaseContract(contract);
        if (saved) {
            System.out.println("Lease contract saved successfully.");
            vehicleDAO.removeVehicleById(vehicleId);
        } else {
            System.out.println("Error saving lease contract.");
        }
    }

    private int promptInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String promptString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private boolean promptYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' or 'n'.");
            }
        }
    }
}
