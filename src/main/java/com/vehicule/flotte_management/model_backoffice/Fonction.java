/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vehicule.flotte_management.model_backoffice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 *
 * @author sitraka
 */
public class Fonction {
    public boolean login (String nom,String mdp)
    {
        Statement stmt = null;
        ResultSet rs = null ;
	    String sql ="select * from admin where username='"+nom+"' and password='"+mdp+"'";
        boolean val = false ;
        int isa = 0 ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
		isa++ ;
            }
            if (isa != 0)
            {
                val = true ;
            }
            rs.close();
            stmt.close();
            con.close();
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
        return val ;
    }

    public void insert (String sql)
    {
        Statement stmt = null ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            con.close();
        }

        catch(Exception e ){
            System.out.println("exception due a la requete");
        }

    }

    public String reqcategorie (String nomcategorie)
    {
        String val = null ;
        val="insert into categorie(nomcategorie) values('"+nomcategorie+"')";
        return val ;
    }

    public void insertcat (String nomcat)
    {
        insert(reqcategorie (nomcat));
    }

    public void deletecat (int id)
    {
        insert(reqdeletecat (id));
    }

    public  String reqdeletecat (int id)
    {
        String val = null ;
        val="delete from categorie where idcategorie="+id;
        return val ;
    }

    public String reqcommission (double pourcentage)
    {
        String val = null ;
        val="update commission set pourcentage="+pourcentage;
        return val ;
    }

    public void updatecommission (double pourcentage)
    {
        insert (reqcommission(pourcentage)) ;
    }

    public String reqrecharge(int id)
    {
        String val = null ;
        val="update demande_recharge set etat=1,daty=current_date where id="+id ;
        return val ;
    }

    public String reqrefuser(int id)
    {
        String val = null ;
        val="update demande_recharge set etat=2,daty=current_date where id="+id ;
        return val ;
    }

    public double getcompte (int idutilisateur)
    {
        Statement stmt = null;
        ResultSet rs = null ;
        String sql ="select * from compte where  idutilisateur="+idutilisateur;
        double val = 0 ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                val = rs.getDouble(3);
                break;
            }
            rs.close();
            stmt.close();
            con.close();
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
        return  val ;
    }

    public String reqcompte (int idutilisateur,double solde)
    {
        String val = null ;
        double montant = getcompte (idutilisateur) ;
        montant = montant + solde ;
        val="update compte set solde="+montant+" where idutilisateur="+idutilisateur;
        return val ;
    }


    public void valider_recharge (int id)
    {
        Statement stmt = null;
        ResultSet rs = null ;
        String sql ="select * from demande_recharge where id="+id+" and etat=0" ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                insert(reqcompte (rs.getInt(2),rs.getDouble(3)));
                insert(reqrecharge(id));
                break;
            }
            rs.close();
            stmt.close();
            con.close();
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
    }

    public void refuser_recharge (int id)
    {
        Statement stmt = null;
        ResultSet rs = null ;
        String sql ="select * from demande_recharge where id="+id+" and etat=0" ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
              //  insert(reqcompte (rs.getInt(2),rs.getDouble(3)));
                insert(reqrefuser(id));
                break;
            }
            rs.close();
            stmt.close();
            con.close();
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
    }

    public Archive[] archiv ()
    {
        Statement stmt = null;
        ResultSet rs = null ;
        String sql ="select * from demande_recharge join utilisateur on demande_recharge.idutilisateur=utilisateur.idutilisateur where  etat=1" ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        Vector<Archive> ila = new Vector<Archive>(100) ;
        Archive[] val = null ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Archive ar = new Archive() ;
                ar.setId(rs.getInt(1));
                ar.setDaty(rs.getDate(5));
                ar.setMontant(rs.getDouble(3));
                ar.setNom(rs.getString(7));
                ar.setPrenom(rs.getString(8));
                ila.add(ar) ;
            }
            rs.close();
            stmt.close();
            con.close();
            val = new Archive[ila.size()] ;
            for (int i=0;i<ila.size();i++)
            {
                val[i]=ila.elementAt(i) ;
            }
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
        return  val ;
    }

    public Recharge[] demande ()
    {
        Statement stmt = null;
        ResultSet rs = null ;
        String sql ="select * from demande_recharge join utilisateur on demande_recharge.idutilisateur=utilisateur.idutilisateur where  etat=0" ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        Vector<Recharge> ila = new Vector<Recharge>(100) ;
        Recharge[] val = null ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Recharge re = new  Recharge() ;
                re.setId(rs.getInt(1));
                re.setMontant(rs.getDouble(3));
                re.setNom(rs.getString(7));
                re.setPrenom(rs.getString(8));
                ila.add(re) ;
            }
            rs.close();
            stmt.close();
            con.close();
            val = new Recharge[ila.size()] ;
            for (int i=0;i<ila.size();i++)
            {
                val[i]=ila.elementAt(i) ;
            }
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
        return  val ;
    }

    public Categorie[] listecategorie ()
    {
        Statement stmt = null;
        ResultSet rs = null ;
        String sql ="select * from categorie order  by idcategorie asc" ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        Vector<Categorie> ila = new Vector<Categorie>(100) ;
        Categorie[] val = null ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Categorie re = new Categorie() ;
                re.setIdcategorie(rs.getInt(1));
                re.setNomcategorie(rs.getString(2));
                ila.add(re) ;
            }
            rs.close();
            stmt.close();
            con.close();
            val = new Categorie[ila.size()] ;
            for (int i=0;i<ila.size();i++)
            {
                val[i]=ila.elementAt(i) ;
            }
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
        return  val ;
    }
    public double comm ()
    {
        Statement stmt = null;
        ResultSet rs = null ;
        String sql ="select * from commission ;" ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        double val = 0 ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                val = rs.getDouble(1);
            }
            rs.close();
            stmt.close();
            con.close();
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
        return  val ;
    }

    public Statistique[] listestat ()
    {
        Statement stmt = null;
        ResultSet rs = null ;
        String sql ="select v_statistique.idcategorie,nomcategorie,sum(montant) as montant from v_statistique join categorie on categorie.idcategorie=v_statistique.idcategorie  group by v_statistique.idcategorie,categorie.nomcategorie ;" ;
        Connection con = null ;
        Connectionpst c = new Connectionpst() ;
        Vector<Statistique> ila = new Vector<Statistique>(100) ;
        Statistique[] val = null ;
        try {
            con = c.connecterPostgres() ;
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Statistique re = new Statistique() ;
                re.setNomcategorie(rs.getString(2));
                re.setCoefficient((rs.getDouble(3)*comm ())/100);
                ila.add(re) ;
            }
            rs.close();
            stmt.close();
            con.close();
            val = new Statistique[ila.size()] ;
            for (int i=0;i<ila.size();i++)
            {
                val[i]=ila.elementAt(i) ;
            }
        }catch ( Exception e ) {
            System.out.println("connexion failed");
        }
        return  val ;
    }

}
