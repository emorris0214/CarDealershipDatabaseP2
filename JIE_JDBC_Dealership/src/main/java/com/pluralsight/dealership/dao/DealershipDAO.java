package com.pluralsight.dealership.dao;

import com.pluralsight.dealership.db.DataSourceManager;
import com.pluralsight.dealership.models.Dealership;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class DealershipDAO {

        public List<Dealership> getAllDealerships() {
            List<Dealership> dealerships = new ArrayList<>();
            String query = "SELECT * FROM dealership";

            try (Connection connection = DataSourceManager.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    dealerships.add(mapRowToDealership(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return dealerships;
        }

        public Dealership getDealershipById(int id) {
            Dealership dealership = null;
            String query = "SELECT * FROM dealership WHERE dealership_id = ?";

            try (Connection connection = DataSourceManager.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, id);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        dealership = mapRowToDealership(resultSet);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return dealership;
        }

        public void addDealership(Dealership dealership) {
            String query = "INSERT INTO dealership (name, address, phone) VALUES (?, ?, ?)";

            try (Connection connection = DataSourceManager.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, dealership.getName());
                statement.setString(2, dealership.getAddress());
                statement.setString(3, dealership.getPhone());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void updateDealership(Dealership dealership) {
            String query = "UPDATE dealership SET name = ?, address = ?, phone = ? WHERE dealership_id = ?";

            try (Connection connection = DataSourceManager.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setString(1, dealership.getName());
                statement.setString(2, dealership.getAddress());
                statement.setString(3, dealership.getPhone());
                statement.setInt(4, dealership.getDealership_id());

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void deleteDealershipById(int id) {
            String query = "DELETE FROM dealership WHERE dealership_id = ?";

            try (Connection connection = DataSourceManager.getDataSource().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Helper method to map a result row to a Dealership object
        private Dealership mapRowToDealership(ResultSet resultSet) throws SQLException {
            return new Dealership(
                    resultSet.getInt("dealership_id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("phone")
            );
        }
    }
