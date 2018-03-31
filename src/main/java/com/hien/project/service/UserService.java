package com.hien.project.service;


import com.hien.project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;


public interface UserService {

    // save or update user
    User saveOrUpdateUser(User user);

    // register user
    User registerUser(User user);

    // remove user
    void removeUser(Long id);

    // get user by id
    User getUserById(Long id);

    // list user by name like
    Page<User> listUsersByNameLike(String name, Pageable pageable);

    // list user by user names in
    List<User> listUsersByUsernames(Collection<String> usernames);
}