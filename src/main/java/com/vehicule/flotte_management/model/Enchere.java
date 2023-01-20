package com.vehicule.flotte_management.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name="enchere")
@NoArgsConstructor
public class Enchere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idenchere", nullable = false)
    private int id;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "idutilisateur",referencedColumnName = "idutilisateur")
    private User utilisateur;
    @ManyToOne(targetEntity = Categorie.class)
    @JoinColumn(name = "idcategorie",referencedColumnName = "idcategorie")
    private Categorie categorie;
    @Column(name = "dateheure")
    private LocalDateTime beginning;
    @Column(name = "prixmiseenchere")
    private double mise_enchere;
	@Column(name = "duree")
    private float duration;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public double getMise_enchere() {
        return mise_enchere;
    }

    public void setMise_enchere(double mise_enchere) {
        this.mise_enchere = mise_enchere;
    }

    public LocalDateTime getBeginning() {
        return beginning;
    }

    public void setBeginning(LocalDateTime beginning) {
        this.beginning = beginning;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public User getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(User utilisateur) {
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
