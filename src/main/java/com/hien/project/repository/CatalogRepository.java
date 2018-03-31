package com.hien.project.repository;


import com.hien.project.domain.Catalog;
import com.hien.project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends JpaRepository<Catalog,Long>{

    // Find catalog by user : User
    List<Catalog> findByUser(User user);

    // find Catalog and name : User and String name
    List<Catalog> findByUserAndName(User user,String name);
}