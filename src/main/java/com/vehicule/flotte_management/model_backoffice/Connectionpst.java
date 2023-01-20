/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vehicule.flotte_management.model_backoffice;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author sitraka
 */
public class Connectionpst {
    public Connection connecterPostgres()  {
        String url = "jdbc:postgresql://surus.db.elephantsql.com:5432/ppfymlkm";
        String username = "ppfymlkm";
        String password = "k1oexEH6sCabH6ptIGwwlePcoW7fY8Z1";
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("you are connected in postgres wsl");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn ;
    }
}
