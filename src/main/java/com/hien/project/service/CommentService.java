package com.hien.project.service;


import com.hien.project.domain.Comment;

public interface CommentService {

    // get comment by id
    Comment getCommentById(Long id);

    // remove comment by id
    void removeComment(Long id);
}