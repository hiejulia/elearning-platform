package com.hien.project.service;




import com.hien.project.domain.Catalog;
import com.hien.project.domain.User;

import java.util.List;


public interface CatalogService {

    // create new catalog
    Catalog saveCatalog(Catalog catalog);

    // remove catalog by id
    void removeCatalog(Long id);

    // get catalog by id
    Catalog getCatalogById(Long id);

    // get all catalogs by User
    List<Catalog> listCatalogs(User user);
}