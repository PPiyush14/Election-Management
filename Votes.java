import java.util.Map;
import java.util.HashMap;

class Votes {
    private static Map<Integer, Integer> voteRecords = new HashMap<>();

    public static void storeVote(int voterId, int candidateId) {
        voteRecords.put(voterId, candidateId);
    }
}