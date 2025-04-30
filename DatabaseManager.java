import java.sql.*;
import java.util.*;

class DatabaseManager implements DatabaseOperations {
    private Connection connection;

    public DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/JavaProject", "root", "#enteryourpassword#"
            );
        } catch (Exception e) {
            System.out.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean authenticateVoter(int voterId, int password) throws InvalidVoterException {
        try {
            String query = "SELECT * FROM voters WHERE voter_id = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, voterId);
            stmt.setInt(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new InvalidVoterException("Invalid voter credentials!");
    }

    public boolean hasVoted(int voterId) {
        try {
            String query = "SELECT * FROM votes WHERE voter_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, voterId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void markVoted(int voterId) {
        // Optional: no operation required if vote is recorded in 'votes' table
    }

    public void recordVote(int voterId, int candidateId) {
        try {
            String query = "INSERT INTO votes (voter_id, candidate_id) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, voterId);
            stmt.setInt(2, candidateId);
            stmt.executeUpdate();
            System.out.println(" Vote recorded in DB: Voter " + voterId + " -> Candidate " + candidateId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Integer> getVoteCounts() {
        Map<Integer, Integer> voteCounts = new HashMap<>();
        try {
            String query = "SELECT candidate_id, COUNT(*) as vote_count FROM votes GROUP BY candidate_id";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                voteCounts.put(rs.getInt("candidate_id"), rs.getInt("vote_count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voteCounts;
    }

    public void createVoter(int voterId, int password) {
        try {
            String query = "INSERT INTO voters (voter_id, password) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, voterId);
            stmt.setInt(2, password);
            stmt.executeUpdate();
            System.out.println("✅ Voter created: " + voterId);
        } catch (SQLException e) {
            System.out.println("❌ Error creating voter: " + e.getMessage());
        }
    }

    public void updateVoterPassword(int voterId, int newPassword) {
        try {
            String query = "UPDATE voters SET password = ? WHERE voter_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, newPassword);
            stmt.setInt(2, voterId);
            stmt.executeUpdate();
            System.out.println("✅ Password updated for voter: " + voterId);
        } catch (SQLException e) {
            System.out.println("❌ Error updating password: " + e.getMessage());
        }
    }

    public void deleteVoter(int voterId) {
        String query = "DELETE FROM voters WHERE voter_id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, voterId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Voter deleted: " + voterId);
            } else {
                System.out.println("❌ No such voter found with ID: " + voterId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting voter: " + e.getMessage());
        }
    }

    public List<Voter> getAllVoters() {
        List<Voter> voters = new ArrayList<>();
        try {
            String query = "SELECT * FROM voters";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                voters.add(new Voter(rs.getInt("voter_id"), rs.getInt("password")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return voters;
    }

    public void addCandidate(String name) {
        PreparedStatement pstmt = null;

        try {
            // Ensure connection is open
            if (connection == null || connection.isClosed()) {
                connection = getConnection();  // Reopen connection if it's closed
            }

            // Find the smallest unused candidate_id
            String idQuery = """
        SELECT MIN(t1.candidate_id + 1) AS next_available_id
        FROM candidates t1
        LEFT JOIN candidates t2 ON t1.candidate_id + 1 = t2.candidate_id
        WHERE t2.candidate_id IS NULL
        """;

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(idQuery);

            int newId = 1; // default if table is empty
            if (rs.next() && rs.getInt("next_available_id") > 0) {
                newId = rs.getInt("next_available_id");
            }

            // Now insert new candidate with this candidate_id
            String insertQuery = "INSERT INTO candidates (candidate_id, name) VALUES (?, ?)";
            pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, newId);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

            System.out.println("✅ Candidate added: " + name);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                // Do not close connection here
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Candidate> getAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        try {
            String query = "SELECT * FROM candidates";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("candidate_id");  // Ensuring correct column name 'candidate_id'
                String name = rs.getString("name");
                candidates.add(new Candidate(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidates;
    }

    public void updateCandidateName(int candidateId, String newName) {
        try {
            String query = "UPDATE candidates SET name = ? WHERE candidate_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, newName);
            stmt.setInt(2, candidateId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Candidate name updated.");
            } else {
                System.out.println("❌ No such candidate found with ID: " + candidateId);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating candidate: " + e.getMessage());
        }
    }

    public void deleteCandidate(int candidateId) {
        try {
            String query = "DELETE FROM candidates WHERE candidate_id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, candidateId);
            stmt.executeUpdate();
            System.out.println("✅ Candidate deleted.");
        } catch (SQLException e) {
            System.out.println("❌ Error deleting candidate: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
