import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Election election = new Election();
        DatabaseManager db = new DatabaseManager();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Register Voter");
            System.out.println("2. Update Voter Password");
            System.out.println("3. Delete Voter");
            System.out.println("4. View All Voters");
            System.out.println("5. Vote");
            System.out.println("6. Show Results");
            System.out.println("7. Manage Candidates");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Voter ID: ");
                    int voterId = scanner.nextInt();
                    System.out.print("Enter Password: ");
                    int password = scanner.nextInt();
                    db.createVoter(voterId, password);
                    break;

                case 2:
                    System.out.print("Enter Voter ID: ");
                    int voterToUpdate = scanner.nextInt();
                    System.out.print("Enter New Password: ");
                    int newPassword = scanner.nextInt();
                    db.updateVoterPassword(voterToUpdate, newPassword);
                    break;

                case 3:
                    System.out.print("Enter Voter ID to delete: ");
                    int voterToDelete = scanner.nextInt();
                    db.deleteVoter(voterToDelete);
                    break;

                case 4:
                    List<Voter> voters = db.getAllVoters();
                    for (Voter v : voters) {
                        System.out.println("Voter ID: " + v.voterId + ", Password: " + v.password);
                    }
                    break;

                case 5:
                    System.out.print("Enter Voter ID: ");
                    int voterID = scanner.nextInt();
                    System.out.print("Enter Password: ");
                    int voterPassword = scanner.nextInt();

                    System.out.println("Available Candidates:");
                    for (Candidate c : election.getCandidates()) {
                        System.out.println("ID: " + c.candidateId + ", Name: " + c.name);
                    }

                    System.out.print("Enter Candidate ID: ");
                    int candidateID = scanner.nextInt();

                    try {
                        election.vote(voterID, candidateID, voterPassword);
                    } catch (InvalidVoterException e) {
                        System.out.println("Invalid credentials: " + e.getMessage());
                    } catch (AlreadyVotedException e) {
                        System.out.println("You have already voted.");
                    }
                    break;

                case 6:
                    election.displayResults();
                    break;

                case 7:
                    manageCandidatesMenu(scanner, db);
                    break;

                case 8:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void manageCandidatesMenu(Scanner scanner, DatabaseManager db) {
        while (true) {
            System.out.println("\nCandidate Management Menu:");
            System.out.println("1. Add Candidate");
            System.out.println("2. View All Candidates");
            System.out.println("3. Update Candidate Name");
            System.out.println("4. Delete Candidate");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Candidate Name: ");
                    String name = scanner.nextLine();
                    db.addCandidate(name);
                    break;

                case 2:
                    List<Candidate> candidates = db.getAllCandidates();
                    for (Candidate c : candidates) {
                        System.out.println("ID: " + c.candidateId + ", Name: " + c.name);
                    }
                    break;

                case 3:
                    System.out.print("Enter Candidate ID: ");
                    int idToUpdate = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine();
                    db.updateCandidateName(idToUpdate, newName);
                    break;

                case 4:
                    System.out.print("Enter Candidate ID to delete: ");
                    int idToDelete = scanner.nextInt();
                    db.deleteCandidate(idToDelete);
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
