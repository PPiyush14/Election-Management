import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface DatabaseOperations {
    boolean authenticateVoter(int voterId, int password) throws InvalidVoterException;

    boolean hasVoted(int voterId);

    void markVoted(int voterId);  // Required method

    void recordVote(int voterId, int candidateId);

    Map<Integer, Integer> getVoteCounts();

    void createVoter(int voterId, int password);

    void updateVoterPassword(int voterId, int newPassword);

    void deleteVoter(int voterId);

    List<Voter> getAllVoters();

    Connection getConnection();  // Add this so Main can call db.getConnection()
}
