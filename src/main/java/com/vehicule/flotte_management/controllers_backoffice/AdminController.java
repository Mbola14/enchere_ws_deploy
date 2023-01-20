package com.vehicule.flotte_management.controllers_backoffice;

import com.vehicule.flotte_management.model_backoffice.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enchere/")
public class AdminController {

    @PostMapping("/admin")
    public boolean adminlogin (@RequestBody Admin admin) {
        Fonction f = new Fonction() ;
        boolean test = f.login (admin.getUsername(),admin.getPassword()) ;
        return test ;
    }
}
