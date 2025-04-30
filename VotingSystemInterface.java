package com.votingsystem.interfaces;

public interface VotingSystemInterface {
    void registerVote(int voterID, int candidateID);
    void calculateResults();
}
