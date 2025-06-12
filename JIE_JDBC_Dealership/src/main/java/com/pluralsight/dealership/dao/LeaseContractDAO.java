package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.db.DataSourceManager;
import com.pluralsight.dealership.models.LeaseContract;

import  java.sql.*;

public class LeaseContractDAO {
    public boolean saveLeaseContract(LeaseContract contract) {
        String query = "INSERT INTO lease_contracts (date, customer_name, customer_email, vehicle_id, total_price, monthly_payment) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, contract.getDate());
            statement.setString(2, contract.getCustomerName());
            statement.setString(3, contract.getCustomerEmail());
            statement.setInt(4, contract.getVehicleSold().getVehicle_id());
            statement.setDouble(5, contract.getTotalPrice());
            statement.setDouble(6, contract.getMonthlyPayment());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

