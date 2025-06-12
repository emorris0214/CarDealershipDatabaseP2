package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.db.DataSourceManager;
import com.pluralsight.dealership.models.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    public List<Vehicle> getAllVehicles() {
        String query = """
            SELECT i.vehicle_id, i.vin, i.dealership_id,
                   v.year, v.make, v.model,
                   v.color, v.price
            FROM inventory i
            JOIN vehicles v USING (vehicle_id, vin)
        """;
        return executeQuery(query);
    }

    public List<Vehicle> getVehiclesByPrice(double minPrice, double maxPrice) {
        String query = """
            SELECT i.vehicle_id, i.vin, i.dealership_id,
                   v.year, v.make, v.model,
                   v.color, v.price
            FROM inventory i
            JOIN vehicles v USING (vehicle_id, vin)
            WHERE v.price BETWEEN ? AND ?
        """;
        return executeQueryWithDoubleRange(query, minPrice, maxPrice);
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        String query = """
            SELECT i.vehicle_id, i.vin, i.dealership_id,
                   v.year, v.make, v.model,
                   v.color, v.price
            FROM inventory i
            JOIN vehicles v USING (vehicle_id, vin)
            WHERE v.make = ? AND v.model = ?
        """;
        return executeQueryWithStrings(query, make, model);
    }

    public List<Vehicle> getVehiclesByYear(int minYear, int maxYear) {
        String query = """
            SELECT i.vehicle_id, i.vin, i.dealership_id,
                   v.year, v.make, v.model,
                   v.color, v.price
            FROM inventory i
            JOIN vehicles v USING (vehicle_id, vin)
            WHERE v.year BETWEEN ? AND ?
        """;
        return executeQueryWithIntRange(query, minYear, maxYear);
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        String query = """
            SELECT i.vehicle_id, i.vin, i.dealership_id,
                   v.year, v.make, v.model,
                   v.color, v.price
            FROM inventory i
            JOIN vehicles v USING (vehicle_id, vin)
            WHERE v.color = ?
        """;
        return executeQueryWithString(query, color);
    }

    public List<Vehicle> getVehiclesByMileage(int minMileage, int maxMileage) {
        String query = """
            SELECT i.vehicle_id, i.vin, i.dealership_id,
                   v.year, v.make, v.model,
                   v.color, v.price
            FROM inventory i
            JOIN vehicles v USING (vehicle_id, vin)
            WHERE v.odometer BETWEEN ? AND ?
        """;
        return executeQueryWithIntRange(query, minMileage, maxMileage);
    }

    public List<Vehicle> getVehiclesByType(String type) {
        String query = """
            SELECT i.vehicle_id, i.vin, i.dealership_id,
                   v.year, v.make, v.model,
                   v.color, v.price
            FROM inventory i
            JOIN vehicles v USING (vehicle_id, vin)
            WHERE v.vehicle_type = ?
        """;
        return executeQueryWithString(query, type);
    }

    private List<Vehicle> executeQuery(String query) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                vehicles.add(mapRowToVehicle(set));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private List<Vehicle> executeQueryWithDoubleRange(String query, double min, double max) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDouble(1, min);
            statement.setDouble(2, max);

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    vehicles.add(mapRowToVehicle(set));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private List<Vehicle> executeQueryWithIntRange(String query, int min, int max) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, min);
            statement.setInt(2, max);

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    vehicles.add(mapRowToVehicle(set));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private List<Vehicle> executeQueryWithString(String query, String value) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, value);

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    vehicles.add(mapRowToVehicle(set));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private List<Vehicle> executeQueryWithStrings(String query, String value1, String value2) {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, value1);
            statement.setString(2, value2);

            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    vehicles.add(mapRowToVehicle(set));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private Vehicle mapRowToVehicle(ResultSet set) throws SQLException {
        return new Vehicle(
                set.getInt("vehicle_id"),
                set.getString("vin"),
                set.getInt("year"),
                set.getString("make"),
                set.getString("model"),
                set.getString("color"),
                set.getDouble("price"),
                set.getInt("dealership_id")
        );
    }
}
