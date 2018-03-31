package com.hien.project.domain;

//import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "authority")
public class Authority {
    private static final long serialVersionUID = -9095654476789768244L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name;
}
