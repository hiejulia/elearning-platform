package com.hien.project.vo;


import com.hien.project.domain.Catalog;
import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogVO implements Serializable{

    // Catalog : username, catalog 
    private static final long serialVersionUID = -4451826914315048160L;

    private String username;

    private Catalog catalog;


}