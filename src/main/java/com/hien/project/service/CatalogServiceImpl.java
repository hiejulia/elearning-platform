package com.hien.project.service;


import com.hien.project.domain.Catalog;
import com.hien.project.domain.User;
import com.hien.project.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private CatalogRepository catalogRepository;


    @Override
    public Catalog saveCatalog(Catalog catalog) {

        List<Catalog> list=catalogRepository.findByUserAndName(catalog.getUser(),catalog.getName());
        if(list!=null&&list.size()>0){
            throw new IllegalArgumentException("Error when saving catalog ");
        }
        return catalogRepository.save(catalog);
    }


    @Override
    public void removeCatalog(Long id) {
        catalogRepository.delete(id);
    }


    @Override
    public Catalog getCatalogById(Long id) {
        return catalogRepository.findOne(id);
    }


    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }
}