package com.votingsystem.election;

import com.votingsystem.interfaces.VotingSystemInterface;

public class Election implements VotingSystemInterface {
    @Override
    public void registerVote(int voterID, int candidateID) {
        // Logic to register a vote
        System.out.println("Vote registered for candidate: " + candidateID);
    }

    @Override
    public void calculateResults() {
        // Logic to calculate election results
        System.out.println("Results calculated!");
    }
}
