package com.vehicule.flotte_management.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.*;

import org.springframework.util.DigestUtils;

import lombok.NoArgsConstructor;

@Entity
@Table(name="utilisateur")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idutilisateur", nullable = false)
    private Integer idUser;
	@Column(name = "nom")
    private String nom;
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "datenaissance")
    private Date datenaissance;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

	public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Date getDatenaissance() {
        return datenaissance;
    }
    public void setDatenaissance(Date datenaissance) {
        this.datenaissance = datenaissance;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

    public Compte getAccount(Connection connection, PreparedStatement stmt, ResultSet rs) throws SQLException {
        Compte compte = new Compte();

        stmt = connection.prepareStatement("select * from compte where idutilisateur=?");
        stmt.setInt(1, idUser);
        rs = stmt.executeQuery();

        if(rs.next()) {
            compte.setId(rs.getInt("idcompte"));
            compte.setUtilisateur(this);
            compte.setSolde(rs.getDouble("solde"));
        }
        return compte;
    }
}