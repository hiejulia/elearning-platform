package com.hien.project.service;



import com.hien.project.domain.Authority;
import com.hien.project.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthorityServiceImpl implements AuthorityService{

    // AuthorityRepository:
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public Authority getAuthorityById(Long id) {
        return authorityRepository.findOne(id);
    }
}