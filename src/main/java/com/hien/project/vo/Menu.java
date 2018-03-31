package com.hien.project.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Menu implements Serializable {

    // Menu : name, url
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;


}