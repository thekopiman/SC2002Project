package camps;

import java.util.ArrayList;
import java.util.Scanner;

import users.Staff;
import users.Users;
import users.Student;

public class CampArray {
    private ArrayList<Camp> camps = new ArrayList<Camp>();
    private Scanner scanner = new Scanner(System.in);

    public void createCamp(Camp newCamp) {
        camps.add(newCamp);
    }

    public void editCamp(String campName) {
        Camp targetCamp = null;

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName() == campName) {
                targetCamp = camps.get(i);
                break;
            }
        }
        if (targetCamp == null) {
            System.out.println("Camp not found");
            return;
        }

        // Edit Scanner
        // Example: Editing camp description
        System.out.println("Enter new description for the camp:");
        String newDescription = scanner.nextLine();
        // Set the new description for the targetCamp
        targetCamp.setCampDescription(newDescription);

        System.out.println("Camp edited successfully");
    }

    public void deleteCamp(String campName) {

        for (int i = 0; i < camps.size(); i++) {
            if (camps.get(i).getCampName() == campName) {
                camps.remove(i);
            }
        }
    }

    // Fugerson help - This one quite troublesome as there are different visibility
    // requirements. Need to refer to Student and Staff.
    // For Staff - I wan to filter by staffInCharge. Help me to do this.

    public void viewCamps(Users user) {
        // Display camps based on different visibility requirements
        // For Staff - No filters since every staff can view every camp
        // For Students - filter by committeeMembers

        for (Camp camp : camps) {
            // Check if the user is a staff member
            if (user instanceof Staff) {
                System.out.println(camp.toString());
            }

            // Check if the user is a student and is in the committeeMembers list
            // Can edit when y'all make the Student class
            if (user instanceof Student && isStudentInCommittee((Student) user, camp)) {
                System.out.println(camp.toString());
            }
        }
    }

    // Helper method to check if a student is in the committeeMembers list
    private boolean isStudentInCommittee(Student student, Camp camp) {
        // Retrieve the committee members of the camp
        String[] committeeMembers = camp.getCommitteeMembers();

        // Check if the student is in the committee members list
        for (String committeeMember : committeeMembers) {
            if (committeeMember != null && committeeMember.equals(student.getID())) {
                return true;
            }
        }
        return false;
    }

}
