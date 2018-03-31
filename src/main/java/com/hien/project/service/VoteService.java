package com.hien.project.service;


import com.hien.project.domain.Vote;

public interface VoteService {
    // get vote by id
    Vote getVoteById(Long id);

    // remove vote by di
    void removeVote(Long id);
}