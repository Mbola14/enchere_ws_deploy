package com.vehicule.flotte_management.controllers_backoffice;

import com.vehicule.flotte_management.model_backoffice.*;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/enchere/")
public class ComissionController {

    @GetMapping("commission/{pourcentage}")
    public boolean upcommission (@PathVariable(value = "pourcentage") double pourcentag)
    {
        boolean ret = true ;
        Fonction f = new Fonction() ;
        f.updatecommission(pourcentag);
        return ret ;
    }

}
