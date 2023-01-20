package com.vehicule.flotte_management.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String driver = "org.postgresql.Driver"; 
    private static final String url = "jdbc:postgresql://surus.db.elephantsql.com:5432/ppfymlkm"; 
    private static final String username = "ppfymlkm"; 
    private static final String pwd = "k1oexEH6sCabH6ptIGwwlePcoW7fY8Z1";

    public Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url,username,pwd);
        
        return con;
    }
}
