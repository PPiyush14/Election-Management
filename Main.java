package com.votingsystem;

import com.votingsystem.election.Election;

public class Main {
    public static void main(String[] args) {
        Election election = new Election();
        election.registerVote(101, 5);
        election.calculateResults();
    }
}
