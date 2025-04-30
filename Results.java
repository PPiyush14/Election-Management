package com.votingsystem.models;

public class Results {
    private int resultID;
    private int candidateID;
    private String resultStatus; // e.g., "Won", "Lost"

    public Results(int resultID, int candidateID, String resultStatus) {
        this.resultID = resultID;
        this.candidateID = candidateID;
        this.resultStatus = resultStatus;
    }

    public int getResultID() { return resultID; }
    public int getCandidateID() { return candidateID; }
    public String getResultStatus() { return resultStatus; }
}
