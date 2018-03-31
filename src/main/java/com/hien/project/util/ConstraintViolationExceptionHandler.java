package com.hien.project.util;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


public class ConstraintViolationExceptionHandler {
    // Constraint violation exception handler
    // get message
    public static String getMessage(ConstraintViolationException e) {

        List<String> msgList = new ArrayList<String>();

        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {

            msgList.add(constraintViolation.getMessage());

        }
        // String message : String utils. join : msg. to Array ,
        String messages= StringUtils.join(msgList.toArray(),";");
        return messages;
    }
}