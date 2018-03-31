package com.hien.project.repository;



import com.hien.project.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>{

    // Find User by Name like
    Page<User> findByNameLike(String name, Pageable pageable);

    // Find user by User name
    User findByUsername(String username);

    // Find user by User name in
    List<User> findByUsernameIn(Collection<String> usernames);

    // find user by id
    User findByUserId(Long id);
}

