package com.hien.project.service;



import com.hien.project.domain.Vote;
import com.hien.project.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VoteServiceImpl implements VoteService{

    @Autowired
    private VoteRepository voteRepository;


    @Override
    public Vote getVoteById(Long id) {
        return voteRepository.findOne(id);
    }


    @Override
    public void removeVote(Long id) {
        voteRepository.delete(id);
    }
}