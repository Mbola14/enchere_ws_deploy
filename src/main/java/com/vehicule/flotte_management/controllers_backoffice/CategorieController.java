package com.vehicule.flotte_management.controllers_backoffice;

import com.vehicule.flotte_management.model_backoffice.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enchere/")
public class CategorieController {
    @PostMapping("/newcategorie")
    public void newcategorie (@RequestBody Categorie categorie) {
        Fonction f = new Fonction() ;
        f.insertcat(categorie.getNomcategorie());
    }
    //listecategorie ()

    @GetMapping("listecategorie")
    public Categorie[] listecategorie ()
    {
        Fonction f = new Fonction() ;
        Categorie[] ar = f.listecategorie() ;
        return ar ;
    }

    @GetMapping("supprimer/{id}")
    public void supprimer (@PathVariable(value = "id") int idcategorie)
    {
        Fonction f = new Fonction() ;
        f.deletecat(idcategorie);
    }

}
