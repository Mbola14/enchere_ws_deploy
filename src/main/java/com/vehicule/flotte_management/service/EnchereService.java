package com.vehicule.flotte_management.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicule.flotte_management.database.DatabaseConnection;
import com.vehicule.flotte_management.exceptions.EmptySearchParamException;
import com.vehicule.flotte_management.exceptions.EmptySearchResultException;
import com.vehicule.flotte_management.model.Categorie;
import com.vehicule.flotte_management.model.Data;
import com.vehicule.flotte_management.model.Enchere;
import com.vehicule.flotte_management.model.SearchModel;
import com.vehicule.flotte_management.model.User;
import com.vehicule.flotte_management.repository.CategorieRepository;
import com.vehicule.flotte_management.repository.EnchereRepository;
import com.vehicule.flotte_management.repository.UsersRepository;


@Service
public class EnchereService {
    @Autowired
    private EnchereRepository enchereRepository;

    @Autowired
    UsersRepository usersRepository;
    
    @Autowired
    CategorieRepository categorieRepository;

    public List<Enchere> fetchAll() {
        return (List<Enchere>) enchereRepository.findAll();
    }

    public Enchere fetchById(int enchereId) {
        return enchereRepository.findById(enchereId).get();
    }

    public Data search(SearchModel searchModel) throws Exception {
        DatabaseConnection dbc = new DatabaseConnection();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Data data = new Data();
        String desc = searchModel.getDescription();
        Integer categorieId = searchModel.getCategorieId();
        Date date = searchModel.getDate();
        Double prix = searchModel.getPrix();
        Integer status = searchModel.getStatut();

        String sql = "select * from enchere where ";
        if(desc != null || date != null ||
            prix != null ||  categorieId != null || status != null) {
            List<String> requests = new ArrayList<>();
            if(desc != null) {
                requests.add("description like '%"+desc+"%'");
            }
            if(date != null) {
                requests.add("(dateheure::date)='"+date+"'");
            }
            if(prix != null) {
                requests.add("prixmiseenchere="+prix);
            }
            if(categorieId != null) {
                requests.add("idcategorie="+categorieId);
            }
            if(status != null) {
                requests.add("status="+status);
            }

            for(int i = 0; i < requests.size(); i += 1) {
                if(i == 0) sql += requests.get(i) + " ";
                else sql += " and " + requests.get(i); 
            }

            System.out.println(sql);

            try {
				connection = dbc.connect();
                stmt = connection.prepareStatement(sql);
                rs = stmt.executeQuery();
                List<Enchere> results = new ArrayList<Enchere>();
                boolean noRes = true;
                while(rs.next()) {
                    noRes = false;
                    Enchere result = new Enchere();
                    result.setId(rs.getInt("idenchere"));
                    User user = new User();
                    user = usersRepository.findById(rs.getInt("idutilisateur")).get();
                    user.setUsername(null);
                    user.setPassword(null);
                    result.setUtilisateur(user);
                    Categorie categorie = new Categorie();
                    categorie = categorieRepository.findById(rs.getInt("idcategorie")).get();
                    result.setCategorie(categorie);
                    result.setBeginning(rs.getTimestamp("dateheure").toLocalDateTime());
                    result.setMise_enchere(rs.getDouble("prixmiseenchere"));
                    result.setDuration(rs.getFloat("duree"));
                    result.setDescription(rs.getString("description"));
                    result.setStatus(rs.getInt("status"));
                    results.add(result);
                } 
                if(noRes) { 
                    EmptySearchResultException esrExc = new EmptySearchResultException("aucun résultat");
                    data.setException(esrExc.getMessage());
                }
                else data.setResult(results);
			} catch (ClassNotFoundException | SQLException e) {
                throw e;
			} finally {
                if(connection != null && !connection.isClosed()) connection.close();
                if(stmt != null && !stmt.isClosed()) stmt.close();
                if(rs != null && !rs.isClosed()) rs.close();
            }
        } else {
            EmptySearchParamException espExc = new EmptySearchParamException("aucun paramètre de recherche");
            data.setException(espExc.getMessage());
        }

        return data;
    }

    public List<Enchere> findByUser(int userId) throws Exception {
        List<Enchere> history_results = new ArrayList<>();
        DatabaseConnection dbc = new DatabaseConnection();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = dbc.connect();
            stmt = connection.prepareStatement("select * from enchere_global where idutilisateur=?");
            stmt.setInt(1, userId);

            rs = stmt.executeQuery();

            while(rs.next()) {
                Enchere e = new Enchere();
                e.setId(rs.getInt("idenchere"));
                e.setBeginning(rs.getTimestamp("dateheure").toLocalDateTime());
                e.setMise_enchere(rs.getDouble("prixmiseenchere"));
                e.setDuration(rs.getFloat("duree"));
                e.setDescription(rs.getString("description"));
                e.setStatus(rs.getInt("status"));
                User user = new User();
                user.setIdUser(rs.getInt("idutilisateur"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                e.setUtilisateur(user);
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("idcategorie"));
                categorie.setNom(rs.getString("nomcategorie"));
                e.setCategorie(categorie);
                history_results.add(e);
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw e;
        } finally {
            if(connection != null && !connection.isClosed()) connection.close();
            if(stmt != null && !stmt.isClosed()) stmt.close();
            if(rs != null && !rs.isClosed()) rs.close();
        }
        return history_results;
    }

    public Enchere saveEnchere(Enchere newEnchere) throws Exception {
        newEnchere.setBeginning(LocalDateTime.now());
        newEnchere.setStatus(1);
        return enchereRepository.save(newEnchere);
    }

    public List<Enchere> getExpiredByUser(int userId) throws Exception {
        List<Enchere> expired = new ArrayList<>();
        DatabaseConnection dbc = new DatabaseConnection();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
			connection = dbc.connect();
            verify_expiration(connection, userId);
            connection.setAutoCommit(true);
            stmt = connection.prepareStatement("select * from enchere_global where status=2 and idutilisateur=?");
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Enchere e = new Enchere();
                e.setId(rs.getInt("idenchere"));
                e.setBeginning(rs.getTimestamp("dateheure").toLocalDateTime());
                e.setMise_enchere(rs.getDouble("prixmiseenchere"));
                e.setDuration(rs.getFloat("duree"));
                e.setDescription(rs.getString("description"));
                e.setStatus(rs.getInt("status"));
                User user = new User();
                user.setIdUser(rs.getInt("idutilisateur"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                e.setUtilisateur(user);
                Categorie categorie = new Categorie();
                categorie.setId(rs.getInt("idcategorie"));
                categorie.setNom(rs.getString("nomcategorie"));
                e.setCategorie(categorie);
                expired.add(e);
            }
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
		} finally {
            if(connection != null && !connection.isClosed()) connection.close();
            if(stmt != null && !stmt.isClosed()) stmt.close();
            if(rs != null && !rs.isClosed()) rs.close();
        }

        return expired;
    }

    private void verify_expiration(Connection connection, int userId) throws Exception {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement("select * from enchere_expiration");
            rs = stmt.executeQuery();

            while(rs.next()) {
                int enchereId = rs.getInt("idenchere");
                LocalDateTime expirationTime = rs.getTimestamp("ending").toLocalDateTime();
                if(verify(expirationTime)) {
                    expire(connection, stmt, rs, enchereId, userId);
                }
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if(stmt != null && !stmt.isClosed()) stmt.close();
            if(rs != null && !rs.isClosed()) rs.close();
        }
    }

    private void expire(Connection connection, PreparedStatement stmt, ResultSet rs, int enchereId, int userId) throws SQLException {
        try {
            connection.setAutoCommit(false);
            
            stmt = connection.prepareStatement("update enchere set status=2 where idenchere=?");
            stmt.setInt(1, enchereId);
            stmt.executeUpdate();

            stmt = connection.prepareStatement("select idmouvement, montant from preview_transact where idenchere=? order by montant desc limit 1");
            stmt.setInt(1, enchereId);
            rs = stmt.executeQuery();
            int to_block_mvt_id = 0;
            double to_credit_sum = 0.0;
            if(rs.next()) { 
               to_credit_sum = rs.getDouble("montant");
               to_block_mvt_id = rs.getInt("idmouvement");
            }

            stmt = connection.prepareStatement("update mouvement set status=2 where idmouvement=?");
            stmt.setInt(1, to_block_mvt_id);
            stmt.executeUpdate();

            stmt = connection.prepareStatement("update compte set solde = solde + ? where idutilisateur = ?");
            stmt.setDouble(1, to_credit_sum);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if(connection != null) connection.rollback();
            throw e;
        }
    }

    private boolean verify(LocalDateTime expTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if(currentDateTime.equals(expTime) || currentDateTime.isAfter(expTime)) return true;

        return false;
    }
}
