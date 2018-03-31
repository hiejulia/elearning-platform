package com.hien.project.vo;

import lombok.Data;

@Data
public class Response {

    // Response : success, message, body
    private boolean success;

    private String  message;

    private Object  body;


}