package com.vehicule.flotte_management.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.vehicule.flotte_management.database.DatabaseConnection;
import com.vehicule.flotte_management.model.Recharge;
import com.vehicule.flotte_management.model.User;

@Service
public class CompteService {
    public Recharge demande_recharge(Recharge recharge) throws Exception {
        DatabaseConnection dbc = new DatabaseConnection();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = dbc.connect();
            stmt = connection.prepareStatement("insert into demande_recharge(idutilisateur,montant) values(?,?)");
            stmt.setInt(1, recharge.getUser().getIdUser());
            stmt.setDouble(2, recharge.getMontant());

            stmt.executeUpdate();

            stmt = connection.prepareStatement("select * from demande_recharge order by id desc limit 1");
            rs = stmt.executeQuery();
            if(rs.next()) {
                recharge.setId(rs.getInt("id"));
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        } finally {
            if(connection != null && !connection.isClosed()) connection.close();
            if(stmt != null && !stmt.isClosed()) stmt.close();
            if(rs != null && !rs.isClosed()) rs.close();
        }

        return recharge;
    }
}
