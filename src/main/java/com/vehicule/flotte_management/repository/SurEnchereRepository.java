package com.vehicule.flotte_management.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vehicule.flotte_management.model.SurEnchere;
@Repository
public interface SurEnchereRepository extends CrudRepository<SurEnchere, Integer>{
    @Query(value = "select * from surenchere where idenchere= :idenchere order by idsurenchere desc limit 1", nativeQuery = true)
    public SurEnchere findLastProp(@Param("idenchere") int idenchere);
}