package com.vehicule.flotte_management.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vehicule.flotte_management.model.User;

@Repository
public interface UsersRepository extends CrudRepository<User,Integer> {
    @Query(value = "select * from utilisateur where username= :usr and password=:pswd",nativeQuery = true)
    public User findUserByEmailAndAndPassword(@Param("usr") String username,@Param("pswd") String password);   
}