package com.hien.project.service;


import com.hien.project.domain.User;
import com.hien.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{
//    public class UserServiceImpl implements UserService,UserDetailsService {

    // Autowired : UserRepository
    @Autowired
    private UserRepository userRepository;

    // save or update user
    @Transactional
    @Override
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }
    // register user : save user
    @Transactional
    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }
    // delete user by di
    @Transactional
    @Override
    public void removeUser(Long id) {
        User user = userRepository.findByUserId(id);
        userRepository.delete(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findByUserId(id);
    }

    @Override
    public Page<User> listUsersByNameLike(String name, Pageable pageable) {
        name = "%"+name+"%";
        Page<User> users=userRepository.findByNameLike(name,pageable);
        return users;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username);
//    }


    @Override
    public List<User> listUsersByUsernames(Collection<String> usernames) {
        return userRepository.findByUsernameIn(usernames);
    }
}