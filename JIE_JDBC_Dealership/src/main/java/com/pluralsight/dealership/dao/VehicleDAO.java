package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.db.DataSourceManager;
import com.pluralsight.dealership.models.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class VehicleDAO {

    public Vehicle getVehicleById(int vehicleId) {
        String sql = "SELECT v.vehicle_id, v.vin, v.year, v.make, v.model, v.color, v.price, i.dealership_id " +
                "FROM inventory i " +
                "JOIN vehicles v ON i.vehicle_id = v.vehicle_id " +
                "WHERE v.vehicle_id = ?";

        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vehicleId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRowToVehicle(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Vehicle getVehicleByVin(int vin) {
        String query = "SELECT * FROM inventory WHERE vin = ?";
        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, vin);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    return mapRowToVehicle(set);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByPrice(int minPrice, int maxPrice) {
        String query = "SELECT * FROM inventory WHERE price BETWEEN ? AND ?";
        return executeQueryWithDoubleRange(query, minPrice, maxPrice);
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        String query = "SELECT * FROM inventory WHERE make = ? AND model = ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, make);
            statement.setString(2, model);

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

    public List<Vehicle> getVehiclesByYear(int minYear, int maxYear) {
        String query = "SELECT * FROM inventory WHERE year BETWEEN ? AND ?";
        return executeQueryWithIntRange(query, minYear, maxYear);
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        String query = "SELECT * FROM inventory WHERE color = ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, color);

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

    public List<Vehicle> getVehiclesByMileage(int minMileage, int maxMileage) {
        String query = "SELECT * FROM inventory WHERE odometer BETWEEN ? AND ?";
        return executeQueryWithIntRange(query, minMileage, maxMileage);
    }

    public List<Vehicle> getVehiclesByType(String type) {
        String query = "SELECT * FROM inventory WHERE vehicle_type = ?";
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, type);

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

    public void addVehicle(Vehicle vehicle) {
        String query = "INSERT INTO inventory (vin, year, make, model, vehicle_type, color, price, dealership_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, vehicle.getVin());
            statement.setInt(2, vehicle.getYear());
            statement.setString(3, vehicle.getMake());
            statement.setString(4, vehicle.getModel());
            statement.setString(5, vehicle.getColor());
            statement.setDouble(6, vehicle.getPrice());
            statement.setInt(7, vehicle.getDealership_id());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeVehicleById(int vehicleId) {
        String sql = "DELETE FROM inventory WHERE vehicle_id = ?";

        try (Connection connection = DataSourceManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, vehicleId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Helper Methods

    private List<Vehicle> executeQueryWithDoubleRange(String query, int min, int max) {
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

    private List<Vehicle> executeQueryWithIntRange(String query, double min, double max) {
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
