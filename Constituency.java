package com.votingsystem.models;

public class Constituency {
    private int constituencyID;
    private String constituencyName;
    private String state;

    public Constituency(int constituencyID, String constituencyName, String state) {
        this.constituencyID = constituencyID;
        this.constituencyName = constituencyName;
        this.state = state;
    }

    public int getConstituencyID() { return constituencyID; }
    public String getConstituencyName() { return constituencyName; }
    public String getState() { return state; }
}
