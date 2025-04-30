package com.votingsystem.models;

public class Party {
    private int partyID;
    private String partyName;

    public Party(int partyID, String partyName) {
        this.partyID = partyID;
        this.partyName = partyName;
    }

    public int getPartyID() { return partyID; }
    public String getPartyName() { return partyName; }
}
