package com.pluralsight.dealership.db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DataSourceManager {
    private static BasicDataSource dataSource;

    static {
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASSWORD");

        if (dbUrl == null || dbUser == null || dbPass == null) {
            throw new RuntimeException("Database environment vars not set.");
        }

        dataSource = new BasicDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPass);
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
