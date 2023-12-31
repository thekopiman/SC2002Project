package camsdisplay;

import users.*;

import java.util.ArrayList;
import java.util.Scanner;

import camps.Camp;
import camps.CampArray;
import infoexchange.EnquiriesArray;
import infoexchange.SuggestionArray;
import report.*;
import utils.*;

/**
 * The DisplayStudent class is used for displaying interface for students.
 * 
 * @author Enric Tan
 * @author Tan Jun Kiat
 * @version 1.0
 */
public class DisplayStudent extends DisplayLogin
        implements ViewCampsScreen, ScreenClearFunction, CampsRegisterable, EnquiriesScreen {
    private Scanner sc = new Scanner(System.in);
    private CampArray campArray;
    private EnquiriesArray enquiriesArray;
    private UsersDatabase UserDB;
    private SuggestionArray suggestionArray;

    private Student user;

    /**
     * The constructor takes in several parameters and assigns
     * these objects to instance variables within the class.
     * Finally, it calls the studentScreen method, passing in the user object
     * attribute.
     * 
     * @param user
     * @param campArray
     * @param enquiriesArray
     * @param UserDB
     * @param suggestionArray
     */
    public DisplayStudent(Student user, CampArray campArray, EnquiriesArray enquiriesArray, UsersDatabase UserDB,
            SuggestionArray suggestionArray) {

        // To ensure that the DisplayLogin contain the same Arrays/Databases
        super(campArray, enquiriesArray, UserDB, suggestionArray);

        this.campArray = campArray;
        this.enquiriesArray = enquiriesArray;
        this.UserDB = UserDB;
        this.suggestionArray = suggestionArray;

        this.user = user;

        studentScreen(user);
    }

    /**
     * The `studentScreen` function displays a menu for a student in the CAMS app
     * and allows them to perform various actions based on their role.
     * 
     * @param student The parameter "student" is an object of the class "Student".
     *                It represents the student user accessing the CAMS app.
     */
    private void studentScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        int choice = -1;
        int logout = (student.IsCampComm()) ? 12 : 5;
        Scanner sc = new Scanner(System.in);

        do {
            logout = (student.IsCampComm()) ? 12 : 5;
            // System.out.println(logout);
            // System.out.print("\033[H\033[2J"); // Clear the entire screen
            System.out.println("╔═════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Camp Application & Management System - Home                     ║");
            System.out.println("╚═════════════════════════════════════════════════════════════════╝");
            System.out.println("Welcome back to CAMs, Student " + UsersDatabase.getFirstName(student.getID()) + " of "
                    + student.getFacultyInfo() + "!");
            System.out.println("0. Reset password");
            System.out.println("1. View Available/Registered Camps");
            System.out.println("2. Register for Camp");
            System.out.println("3. Manage your Enquiries");
            System.out.println("4. Withdraw from Camp");
            if (student.IsCampComm()) {
                System.out.println(
                        "\nCommittee Member Options for Camp " + student.getCampCommitteeRole().getCampName() + ":");
                System.out.println("5. View Camp Details");
                System.out.println("6. View Points");
                System.out.println("7. Manage Suggestions");
                System.out.println("8. View Enquiries");
                System.out.println("9. Reply Enquiries");
                System.out.println("10. Generate Camp Report");
                System.out.println("11. Generate Enquiries Report");
                System.out.println("12. Logout");
            } else
                System.out.println("5. Logout");
            System.out.printf("Enter your choice: ");

            choice = InputInt.nextInt(sc);
            sc.nextLine(); // flush nextline char
            System.out.println();
            switch (choice) {
                case 0:
                    resetPasswordScreen(user);
                    System.out.println("Please login again!");
                    return;
                case 1:
                    viewCampsScreen(student);
                    break;
                case 2:
                    registerCampsScreen(student);
                    break;
                case 3:
                    manageEnquiriesScreen(student);
                    break;
                case 4:
                    withdrawFromCampScreen(student);
                    break;

                case 5:
                    if (student.IsCampComm())
                        viewCampDetailsScreen(student);
                    else {
                        System.out.println("Logging out... Thank you!");
                        user = null;
                    }
                    break;
                case 6:
                    if (student.IsCampComm())
                        viewPointsScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 7:
                    if (student.IsCampComm())
                        manageSuggestionsScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 8:
                    if (student.IsCampComm())
                        viewEnquiriesScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 9:
                    if (student.IsCampComm())
                        replyEnquiriesScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 10:
                    if (student.IsCampComm())
                        generateReportScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 11:
                    if (student.IsCampComm())
                        generateEnquiriesReportScreen(student);
                    else
                        System.out.println("Invalid choice");
                    break;
                case 12:
                    if (student.IsCampComm()) {
                        System.out.println("Logging out... Thank you!");
                        this.user = null;
                    } else
                        System.out.println("Invalid choice");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            ScreenClearFn();
        } while (choice != logout);
    }

    /**
     * The function "viewCampsScreen" displays a screen for viewing camps in the
     * CAMS app
     * 
     * @param user The "user" parameter is of type "Users". It could be any type of
     *             user, such as a student or staff
     */
    public void viewCampsScreen(Users user) {

        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Camps                    ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");

        ((Student) user).viewAvailAndRegCamps(campArray);
    }

    /**
     * The function "registerCampsScreen" displays a screen for registering for a
     * camp in the CAMS app
     * 
     * @param user The "user" parameter is of type "Users". It could be any type of
     *             user, such as a student or staff
     */
    public void registerCampsScreen(Users user) {
        if (!(user instanceof Student))
            return;
        Student student = (Student) user;
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Register Camps                ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        student.viewAvailAndRegCamps(campArray);
        String campname;
        System.out.printf("\nWhich camp do you want to register for? ");
        campname = sc.nextLine();
        do {
            if (campArray.checkCampExists(campname))
                break;
            System.out.println("No such Camp! Please try again.");
            System.out.printf("\nWhich camp do you want to register for? ");
            campname = sc.nextLine();
        } while (true);
        System.out.printf("Do you want to register as committee (1) or attendee (2)? ");
        boolean comm = (InputInt.nextInt(sc) == 1) ? true : false;
        sc.nextLine(); // flush nextline char
        Camp campPtr = campArray.getCamp(campname);
        if (comm)
            student.registerCampCommittee(campPtr, campArray);
        else
            student.registerCampAttendee(campPtr, campArray);
        UserDB.updateFile();
    }

    /**
     * The function "manageEnquiriesScreen" displays a screen for a student to
     * manage his/her enquiries in the CAMS app
     * 
     * @param student The "student" parameter is an object of the Student class. It
     *                represents the student who is using the CAMS app to manage
     *                their enquiries.
     */
    private void manageEnquiriesScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Manage Enquiries              ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        System.out.println(
                "W: WRITE new Enquiry\n" +
                        "V: VIEW current Enquiries\n" +
                        "E: EDIT an Enquiry\nD: DELETE an Enquiry\n" +
                        "S: SUBMIT an Enquiry\n" +
                        "R: View submitted Enquiries and REPLIES\n" +
                        "Enter your choice: ");
        String enqChoice = sc.nextLine();
        System.out.println();
        switch (enqChoice) {
            case "W", "w":
                System.out.println("\nFILTERING and SORTING Camps to View.");
                student.viewAvailAndRegCamps(campArray);
                System.out.println();
                String enqcamp;
                do {
                    System.out.printf("Write Enquiry under which camp?\nPlease input Camp Name (Key in -1 to leave): ");
                    // sc.nextLine();
                    enqcamp = sc.nextLine();
                    // System.out.println(campArray.checkCampExists(enqcamp) + "
                    // "+campArray.checkEligibleCamp(student, enqcamp));
                    if (enqcamp.equals("-1")) {
                        return;
                    }

                    if (student.IsCampComm() && student.getCampCommitteeRole().getCampName().equals(enqcamp)) {
                        System.out.println("Unable to enquiry for this camp as you are the camp committee member\n");
                        continue;
                    }

                    if (campArray.checkCampExists(enqcamp)
                            && campArray.checkEligibleCamp(student, enqcamp)) {
                        break;
                    } else {
                        System.out.println("You are not eligible for that camp or camp does not exist!\n");
                    }
                } while (true);
                System.out.printf("Please input the Enquiry: ");
                // sc.nextLine();
                String enq = sc.nextLine();
                student.createEnquiry(enq, enqcamp);
                break;
            case "V", "v":
                student.viewEnquiries();
                System.out.println("\n===== END of Enquiries =====\n");
                break;
            case "E", "e":
                if (student.getPendingEnquiriesSize() == 0) {
                    System.out.println("\n=== No Pending Enquiries to EDIT ===");
                    break;
                }
                student.viewEnquiries();
                System.out.println();
                int enqindex1;
                do {
                    System.out.printf("Select Enquiry to edit, input Enquiry index: ");
                    enqindex1 = InputInt.nextInt(sc);
                    sc.nextLine(); // flush nextline char
                    int enqArraySize = student.getPendingEnquiriesSize();
                    if (enqindex1 >= 1 && enqindex1 <= enqArraySize)
                        break;
                    System.out.println("Invalid index! Please try again!");
                } while (true);
                System.out.printf("Please input the edited Enquiry: ");
                // sc.nextLine();
                String newenq = sc.nextLine();
                student.editEnquiry(newenq, enqindex1 - 1);
                break;
            case "D", "d":
                if (student.getPendingEnquiriesSize() == 0) {
                    System.out.println("\n=== No Pending Enquiries to DELETE ===");
                    break;
                }
                student.viewEnquiries();
                System.out.println();
                int enqindex2;
                do {
                    System.out.printf("Select Enquiry to delete, input Enquiry index: ");
                    enqindex2 = InputInt.nextInt(sc);
                    sc.nextLine(); // flush nextline char
                    int enqArraySize = student.getPendingEnquiriesSize();
                    if (enqindex2 >= 1 && enqindex2 <= enqArraySize)
                        break;
                    System.out.println("Invalid index! Please try again!");
                } while (true);
                student.deleteEnquiry(enqindex2 - 1);
                break;
            case "S", "s":
                if (student.getPendingEnquiriesSize() == 0) {
                    System.out.println("\n=== No Pending Enquiries to SUBMIT ===");
                    break;
                }
                student.viewEnquiries();
                System.out.println();
                int enqindex;
                do {
                    System.out.printf("Select Enquiry to submit, input Enquiry index: ");
                    enqindex = InputInt.nextInt(sc);
                    sc.nextLine(); // flush nextline char
                    int enqArraySize = student.getPendingEnquiriesSize();
                    if (enqindex >= 1 && enqindex <= enqArraySize)
                        break;
                    System.out.println("Invalid index! Please try again!");
                } while (true);
                student.submitEnquiry(enquiriesArray, enqindex - 1);
                break;
            case "R", "r":
                student.viewEnquiriesReplies(enquiriesArray);
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    /**
     * The function "withdrawFromCampScreen" displays a screen for a student to
     * withdraw from a camp in the CAMS app
     * 
     * @param student The student object represents a student who wants to withdraw
     *                from a camp.
     */
    private void withdrawFromCampScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Withdraw from Camp            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        ArrayList<String> studentRegCamps = student.getRegCampsArray();
        int remCampIndex;
        for (int i = 0; i < studentRegCamps.size(); i++) {
            System.out.println((i + 1) + ": " + studentRegCamps.get(i));
        }
        System.out.println("0: Exit to menu");
        do {
            System.out.printf("Enter the index of the camp you are withdrawing from: ");
            // sc.nextLine();
            remCampIndex = InputInt.nextInt(sc) - 1;
            sc.nextLine(); // flush nextline char
            if (remCampIndex == -1)
                break;
            if (remCampIndex >= 0 && remCampIndex <= (studentRegCamps.size() - 1))
                break;
            System.out.println("Invalid index! Please try again.");
        } while (true);
        if (remCampIndex == -1)
            return;
        Camp remCamp = campArray.getCamp(studentRegCamps.get(remCampIndex));
        student.withdrawFromCamp(remCamp, campArray);
        UserDB.updateFile();
    }

    /**
     * The function "viewCampDetailsScreen" displays a screen for the camp details
     * of the camps in the CAMS app
     * 
     * @param user The "user" parameter is of type "Users". It could be any type of
     *             user, such as a student or staff
     */
    public void viewCampDetailsScreen(Users user) {
        System.out.print("\033[H\033[2J");
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Camp Details             ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        campArray.viewCampDetails(((Student) user).getCampCommitteeRole().getCampName(), ((Student) user));
    }

    /**
     * The function "viewPointsScreen" displays a screen for the Committee points of
     * a camp committee member in the CAMS app
     * 
     * @param student The student object represents a student who is a camp
     *                committee member
     *                that wants to view his/her points
     */
    private void viewPointsScreen(Student student) {
        System.out.print("\033[H\033[2J");
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Committee Points              ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        System.out.printf("You have %d points!\n", student.getCampCommitteeRole().getPoints());
    }

    /**
     * The function "manageSuggestionsScreen" displays a screen for a camp committee
     * member to manage his/her
     * suggestions in the CAMS app
     * 
     * @param student The student object represents a student who is a camp committe
     *                member
     *                that wants to manage his/her suggestions
     */
    private void manageSuggestionsScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Manage Suggestions            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");

        System.out.print(
                "V: VIEW all Suggestions\nP: view PROCESSED Suggestions\nW: WRITE new Suggestion\nE: EDIT a Suggestion\n"
                        +
                        "D: DELETE a Suggestion\nS: SUBMIT a Suggestion\n\nEnter your choice: ");
        String sugChoice = sc.nextLine();
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Manage Suggestions            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");

        switch (sugChoice) {
            case "V", "v":
                suggestionArray.viewSuggestions(student);
                break;
            case "P", "p":
                suggestionArray.viewProcessedSuggestions(student);
                break;
            case "W", "w":
                System.out.println("Please input the Suggestion: ");
                String sug = sc.nextLine();
                suggestionArray.createSuggestion(sug, student,
                        campArray.getCamp(student.getCampCommitteeRole().getCampName()));
                break;
            case "E", "e":
                if (suggestionArray.viewSuggestions(student)) {
                    System.out.printf("Select Suggestion to edit, input Suggestion index: ");
                    int sugindex1 = InputInt.nextInt(sc);
                    sc.nextLine();
                    if (suggestionArray.suggestionCanEdit(student, sugindex1) == 1) {
                        System.out.printf("Please input the edited Suggestion: ");
                        String newsug = sc.nextLine();
                        suggestionArray.editSuggestion(student, sugindex1, newsug);
                    } else if (suggestionArray.suggestionCanEdit(student, sugindex1) == 0)
                        System.out.println("Suggestion cannot be edited as it has been processed!");
                    else
                        System.out.println("Suggestion does not exist!");
                } else
                    System.out.println("No Suggestions to Edit!");
                break;
            case "D", "d":
                if (suggestionArray.viewSuggestions(student)) {
                    System.out.printf("Select Suggestion to delete, input Suggestion index: ");
                    int delindex = InputInt.nextInt(sc);
                    sc.nextLine();
                    suggestionArray.deleteSuggestion(student, delindex);
                } else
                    System.out.println("No Suggestions to Delete!");
                break;
            case "S", "s":
                if (suggestionArray.viewSuggestions(student)) {
                    System.out.printf("Select Suggestion to submit, input Suggestion index: ");
                    int sugindex2 = InputInt.nextInt(sc);
                    sc.nextLine();
                    suggestionArray.submitSuggestion(student, sugindex2);
                    UserDB.updateFile(); // for change in points
                } else
                    System.out.println("No Suggestions to Submit!");
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    /**
     * The function "viewEnquiriesScreen" displays a screen for a camp committee
     * member to view Enquiries
     * addressed to him/her in the CAMS app
     * 
     * @param user The "user" parameter is of type "Users". It could be any type of
     *             user, such as a student or staff
     */
    public void viewEnquiriesScreen(Users user) {
        if (!(user instanceof Student))
            return;
        Student student = (Student) user;
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - View Enquiries                ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        enquiriesArray.viewEnquiries(student);
    }

    /**
     * The function "replyEnquiriesScreen" displays a screen for a camp committee
     * member to reply to Enquiries
     * addressed to him/her in the CAMS app
     * 
     * @param user The "user" parameter is of type "Users". It could be any type of
     *             user, such as a student or staff
     */
    public void replyEnquiriesScreen(Users user) {
        if (!(user instanceof Student))
            return;
        Student student = (Student) user;
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Reply to Enquiries            ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        enquiriesArray.replyEnquiry(student);
        UserDB.updateFile(); // for change in points
    }

    /**
     * The function "generateReportScreen" displays a screen for a camp committee
     * member to generate
     * a camp report in the CAMS app
     * 
     * @param student The student object represents a student who is a camp committe
     *                member
     *                that wants to generate a camp report
     */
    private void generateReportScreen(Student student) {
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔══════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Camp Report          ║\n" +
                        "╚══════════════════════════════════════════════════════════════════════╝\r\n");
        CampReport campReport = new CampReport(student, campArray);
        campReport.generateReport();
    }

    /**
     * The function displays a screen for a camp committee member to generate
     * an enquiry report in the CAMS app
     * 
     * @param user The "user" parameter is of type "Users". It could be any type of
     *             user, such as a student or staff
     */
    public void generateEnquiriesReportScreen(Users user) {
        if (!(user instanceof Student))
            return;
        Student student = (Student) user;
        System.out.print("\033[H\033[2J"); // Clear the entire screen
        System.out.print(
                "╔════════════════════════════════════════════════════════════════════╗\n" +
                        "║ Camp Application & Management System - Generate Enquiries Report   ║\n" +
                        "╚════════════════════════════════════════════════════════════════════╝\r\n");

        EnquiriesReport enquiriesReport = new EnquiriesReport(student, enquiriesArray);
        enquiriesReport.generateReport();

        return;
    }

}
