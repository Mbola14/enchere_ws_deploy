package com.vehicule.flotte_management.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String driver = "org.postgresql.Driver"; 
    private static final String url = "jdbc:postgresql://containers-us-west-194.railway.app:8057/railway"; 
    private static final String username = "postgres";
    private static final String pwd = "C4sXBmKyMzFdE6NoB56p";

    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url,username,pwd);
        
        return con;
    }
}
