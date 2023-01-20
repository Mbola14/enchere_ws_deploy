package com.vehicule.flotte_management.controllers_backoffice;

import com.vehicule.flotte_management.model_backoffice.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enchere/")
public class RechargeController {
    @GetMapping("recharge/{id}")
    public boolean recharger (@PathVariable(value = "id") int idrecharge)
    {
        boolean ret = true ;
        Fonction f = new Fonction() ;
        f.valider_recharge (idrecharge);
        return ret ;
    }

    @GetMapping("refuser/{id}")
    public boolean refuser (@PathVariable(value = "id") int idrecharge)
    {
        boolean ret = true ;
        Fonction f = new Fonction() ;
        f.refuser_recharge(idrecharge);
        return ret ;
    }

    @GetMapping("archive")
    public Archive[] liste_archive ()
    {
        Fonction f = new Fonction() ;
        Archive[] ar = f.archiv () ;
        return ar ;
    }
    //Recharge[] demande ()

    @GetMapping("liste_demande")
    public Recharge[] liste_demande ()
    {
        Fonction f = new Fonction() ;
        Recharge[] ar = f.demande () ;
        return ar ;
    }

}
