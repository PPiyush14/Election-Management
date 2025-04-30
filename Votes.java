package com.votingsystem.models;

public class Votes {
    private int voteID;
    private int candidateID;
    private int totalVotes;
    private double percentageVotes;
    private int evmVotes;
    private int postalVotes;

    public Votes(int voteID, int candidateID, int totalVotes, double percentageVotes, int evmVotes, int postalVotes) {
        this.voteID = voteID;
        this.candidateID = candidateID;
        this.totalVotes = totalVotes;
        this.percentageVotes = percentageVotes;
        this.evmVotes = evmVotes;
        this.postalVotes = postalVotes;
    }

    public int getVoteID() { return voteID; }
    public int getCandidateID() { return candidateID; }
    public int getTotalVotes() { return totalVotes; }
    public double getPercentageVotes() { return percentageVotes; }
    public int getEvmVotes() { return evmVotes; }
    public int getPostalVotes() { return postalVotes; }
}
