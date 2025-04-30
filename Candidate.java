package com.votingsystem.models;

public class Candidate {
    private int candidateID;
    private String candidateName;
    private int partyID;
    private int constituencyID;

    public Candidate(int candidateID, String candidateName, int partyID, int constituencyID) {
        this.candidateID = candidateID;
        this.candidateName = candidateName;
        this.partyID = partyID;
        this.constituencyID = constituencyID;
    }

    public int getCandidateID() { return candidateID; }
    public String getCandidateName() { return candidateName; }
    public int getPartyID() { return partyID; }
    public int getConstituencyID() { return constituencyID; }
}
