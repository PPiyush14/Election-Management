import java.util.*;

class Election implements VotingOperations {
    private DatabaseManager dbManager = new DatabaseManager();

    // Voting operation: checks authentication and vote status
    public void vote(int voterId, int candidateId, int password) throws InvalidVoterException, AlreadyVotedException {
        dbManager.authenticateVoter(voterId, password);

        if (dbManager.hasVoted(voterId)) {
            throw new AlreadyVotedException("Voter has already cast their vote");
        }

        dbManager.recordVote(voterId, candidateId);
        dbManager.markVoted(voterId); // Optional, can be kept for logic separation
        System.out.println(" Vote successfully cast!");
    }

    // Displaying the election result
    public void displayResults() {
        System.out.println("Election Results:");
        Map<Integer, Integer> voteCounts = dbManager.getVoteCounts();
        List<Candidate> candidates = dbManager.getAllCandidates();

        for (Candidate c : candidates) {
            int count = voteCounts.getOrDefault(c.candidateId, 0);
            System.out.println(c.name + " received " + count + " votes.");
        }
    }

    // Helper method to get candidate list (used in Main.java to show candidates)
    public List<Candidate> getCandidates() {
        return dbManager.getAllCandidates();
    }
}
