package com.votingsystem.nativeintegration;

public class VotingSystemCWrapper {
    static {
        System.loadLibrary("voting"); // Load compiled C library (voting.so)
    }

    public native void castVote(int voterID, int candidateID);
    public native void computeResults();
}
