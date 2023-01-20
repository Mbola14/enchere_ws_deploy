package com.vehicule.flotte_management.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.vehicule.flotte_management.model.User;
import com.vehicule.flotte_management.repository.UsersRepository;
import com.vehicule.flotte_management.database.DatabaseConnection;
import com.vehicule.flotte_management.exceptions.AuthenticationException;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public User authenticate(User auth) throws AuthenticationException {
        User curr_user = usersRepository.findUserByEmailAndAndPassword(auth.getUsername(), DigestUtils.md5DigestAsHex(auth.getPassword().getBytes()));
        if(curr_user == null) {
            throw new AuthenticationException("username or password incorrect");
        }

        return curr_user;
    }

    public User sign_up(User newUser) throws Exception {
        newUser.setPassword(DigestUtils.md5DigestAsHex(newUser.getPassword().getBytes()));
        DatabaseConnection dbc = new DatabaseConnection();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
			connection = dbc.connect();
            connection.setAutoCommit(false);

            stmt = connection.prepareStatement("insert into utilisateur(nom,prenom,datenaissance,username,password) values(?,?,?,?,?)");
            stmt.setString(1, newUser.getNom());
            stmt.setString(2, newUser.getPrenom());
            stmt.setDate(3, newUser.getDatenaissance());
            stmt.setString(4, newUser.getUsername());
            stmt.setString(5, newUser.getPassword());
            stmt.executeUpdate();

            stmt = connection.prepareStatement("select * from utilisateur order by idutilisateur desc limit 1");
            rs = stmt.executeQuery();
            int lastUserId = 0;
            if(rs.next()) {
                lastUserId = rs.getInt("idutilisateur");
                newUser.setIdUser(lastUserId);
                newUser.setPassword(rs.getString("password"));
            }

            stmt = connection.prepareStatement("insert into compte(idutilisateur,solde) values(?,?)");
            stmt.setInt(1, lastUserId);
            stmt.setDouble(2, 0.0);
            stmt.executeUpdate();

            connection.commit();
		} catch (ClassNotFoundException | SQLException e) {
            if(connection != null) connection.rollback();
			throw e;
		} finally {
            if(connection != null && !connection.isClosed()) connection.close();
            if(stmt != null && !stmt.isClosed()) stmt.close();
            if(rs != null && !rs.isClosed()) rs.close();
        }

        return newUser;
    }
}