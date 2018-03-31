package com.hien.project.service;

import com.hien.project.domain.Comment;
import com.hien.project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;


    // get comment by id
    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findOne(id);
    }

    // remove comment by id
    @Override
    public void removeComment(Long id) {
        commentRepository.delete(id);
    }
}