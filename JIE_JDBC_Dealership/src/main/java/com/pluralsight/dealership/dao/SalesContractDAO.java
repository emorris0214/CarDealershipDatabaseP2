package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.db.DataSourceManager;
import com.pluralsight.dealership.models.SalesContract;

import java.sql.*;
import java.util.List;

public class SalesContractDAO {

    public boolean saveSalesContract(SalesContract contract) {
        String query = "INSERT INTO sales_contracts (sale_date, sale_price, customer_name, vehicle_id) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, contract.getDate());
            statement.setDouble(2, contract.getTotalPrice());
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