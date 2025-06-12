package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.db.DataSourceManager;
import com.pluralsight.dealership.models.SalesContract;

import java.sql.*;
import java.util.List;

public class SalesContractDAO {

    public boolean saveSalesContract(SalesContract contract) {
        String query = "INSERT INTO sales_contracts (date, customer_name, customer_email, vehicle_id, is_financed, total_price, monthly_payment) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, contract.getDate());
            statement.setString(2, contract.getCustomerName());
            statement.setString(3, contract.getCustomerEmail());
            statement.setInt(4, contract.getVehicleSold().getVehicle_id());
            statement.setBoolean(5, contract.isFinanced());
            statement.setDouble(6, contract.getTotalPrice());
            statement.setDouble(7, contract.getMonthlyPayment());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}