package com.vehicule.flotte_management.model;

import javax.persistence.*;

import lombok.NoArgsConstructor;

@Entity
@Table(name="surenchere")
@NoArgsConstructor
public class SurEnchere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsurenchere", nullable = false)
    private int id;
    @ManyToOne(targetEntity = Enchere.class)
    @JoinColumn(name = "idenchere",referencedColumnName = "idenchere")
    private Enchere enchere;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "idutilisateur",referencedColumnName = "idutilisateur")
    private User owner;
    @Column(name = "montant")
    private double montant;

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Enchere getEnchere() {
		return enchere;
	}

	public void setEnchere(Enchere enchere) {
		this.enchere = enchere;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
