package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.db.DataSourceManager;
import com.pluralsight.dealership.models.LeaseContract;

import  java.sql.*;

public class LeaseContractDAO {
    public boolean saveLeaseContract(LeaseContract contract) {
        String query = "INSERT INTO lease_contracts (lease_start, lease_end, customer_name, vehicle_id) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, contract.getDate());
            statement.setString(2, contract.getLeaseEndDate());
            statement.setString(3, contract.getCustomerName());
            statement.setInt(4, contract.getVehicleSold().getVehicle_id());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

