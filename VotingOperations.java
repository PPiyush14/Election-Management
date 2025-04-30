interface VotingOperations {
    void vote(int voterId, int candidateId, int password) throws AlreadyVotedException, InvalidVoterException;
    void displayResults();
}
