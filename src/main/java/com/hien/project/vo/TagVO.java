package com.hien.project.vo;


import lombok.Data;

import java.io.Serializable;

@Data
public class TagVO implements Serializable {
    // Tag : name, count
    private static final long serialVersionUID = -7768902253044026997L;

    private String            name;

    private Long              count;


}