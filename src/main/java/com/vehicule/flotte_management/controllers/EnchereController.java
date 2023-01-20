package com.vehicule.flotte_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vehicule.flotte_management.exceptions.NotEnoughSoldException;
import com.vehicule.flotte_management.exceptions.SelfEnchereException;
import com.vehicule.flotte_management.exceptions.SurEnchereMontantException;
import com.vehicule.flotte_management.model.Enchere;
import com.vehicule.flotte_management.model.SearchModel;
import com.vehicule.flotte_management.model.Data;
import com.vehicule.flotte_management.model.SurEnchere;
import com.vehicule.flotte_management.service.EnchereService;
import com.vehicule.flotte_management.service.SurEnchereService;

import java.sql.SQLException;
import java.util.List;

@RestController
public class EnchereController {
    @Autowired
    private EnchereService enchereService;

    @Autowired
    private SurEnchereService surEnchereService;

    @GetMapping("encheres")
    public List<Enchere> all_enchere() {
        return enchereService.fetchAll();
    }

    @GetMapping("encheres/{enchereid}") 
    public Enchere get_enchere_by_id(@PathVariable("enchereid") int id) {
        return enchereService.fetchById(id);
    }

    @PostMapping("encheres/surencheres")
    public Data rencherir(@RequestBody SurEnchere surEnchere) throws ClassNotFoundException, SQLException {
        Data data = null;
        try {
			surEnchereService.rencherir(surEnchere);
		} catch (ClassNotFoundException | SQLException e) {
			throw e;
		} catch (SurEnchereMontantException | NotEnoughSoldException | SelfEnchereException e) {
            data = new Data();
			data.setException(e.getMessage());
		}

        return data;
    }

    @PostMapping("encheres/search")
    public Data search(@RequestBody SearchModel searchModel) throws Exception {
        return enchereService.search(searchModel);
    }

    @GetMapping("encheres/{userid}/history")
    public List<Enchere> findHistory(@PathVariable("userid") int userId) throws Exception {
        return enchereService.findByUser(userId);
    }

    @PostMapping("encheres")
    public Enchere saveEnchere(@RequestBody Enchere newEnchere) throws Exception {
        return enchereService.saveEnchere(newEnchere);
    }

    @GetMapping("encheres/{userid}/expired")
    public List<Enchere> getExpired(@PathVariable("userid") int userid) throws Exception {
        return enchereService.getExpiredByUser(userid);
    }
}
