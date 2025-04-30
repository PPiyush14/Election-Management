import java.sql.*;
import java.util.*;

public class CandidateManager {
    private Connection conn;

    public CandidateManager(Connection conn) {
        this.conn = conn;
    }

    public void candidateMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\nCandidate Management Menu:");
            System.out.println("1. Add Candidate");
            System.out.println("2. View All Candidates");
            System.out.println("3. Update Candidate Name");
            System.out.println("4. Delete Candidate");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Candidate Name: ");
                    String name = sc.nextLine();
                    addCandidate(name);
                    break;
                case 2:
                    viewCandidates();
                    break;
                case 3:
                    System.out.print("Enter Candidate ID to update: ");
                    int idToUpdate = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Candidate Name: ");
                    String newName = sc.nextLine();
                    updateCandidate(idToUpdate, newName);
                    break;
                case 4:
                    System.out.print("Enter Candidate ID to delete: ");
                    int idToDelete = sc.nextInt();
                    deleteCandidate(idToDelete);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    public void addCandidate(String name) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO candidates (candidate_name) VALUES (?)");
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("Candidate added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding candidate: " + e.getMessage());
        }
    }

    public void viewCandidates() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM candidates");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("candidate_id") + ", Name: " + rs.getString("candidate_name"));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing candidates: " + e.getMessage());
        }
    }

    public void updateCandidate(int id, String newName) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE candidates SET candidate_name = ? WHERE candidate_id = ?");
            stmt.setString(1, newName);
            stmt.setInt(2, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Candidate updated.");
            } else {
                System.out.println("Candidate not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating candidate: " + e.getMessage());
        }
    }

    public void deleteCandidate(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM candidates WHERE candidate_id = ?");
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Candidate deleted.");
            } else {
                System.out.println("Candidate not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting candidate: " + e.getMessage());
        }
    }
}
