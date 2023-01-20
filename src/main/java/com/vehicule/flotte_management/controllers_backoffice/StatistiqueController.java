package com.vehicule.flotte_management.controllers_backoffice;

import com.vehicule.flotte_management.model_backoffice.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enchere/")
public class StatistiqueController {

    @GetMapping("listestat")
    public Statistique[] listestat ()
    {
        Fonction f = new Fonction() ;
        Statistique[] ar = f.listestat() ;
        return ar ;
    }

}
