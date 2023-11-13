package users;

import java.util.ArrayList;
import java.util.List;

import camdate.CAMDate;
import camps.Camp;

public class CampCommitteeRole {
    private Camp camp;
    private int points;
    // private ArrayList<Suggestion> suggestions = new ArrayList<Suggestion>();

    public CampCommitteeRole(Camp cmp, int pts) {
        this.camp = cmp;
        points = pts;
    }

    // getters for all camp fields only (no setters)
    public String getCampName() {
        return camp.getCampName();
    }

    public ArrayList<CAMDate> getCampDates() {
        return camp.getDates();
    }

    public CAMDate getCampRegistrationClosingDate() {
        return camp.getRegistrationClosingDate();
    }

    public String getCampAvailability() {
        return camp.getCampAvailability();
    }

    public String getCampLocation() {
        return camp.getLocation();
    }

    public int getCampTotalSlots() {
        return camp.getTotalSlots();
    }

    public int getCampCommitteeMembersSlots() {
        return camp.getCommitteeMembersSlots();
    }

    public String getCampDescription() {
        return camp.getCampDescription();
    }

    public String getCampStaffInCharge() {
        return camp.getStaffInCharge();
    }

    public void displayCampInfo() {
        camp.displayCampInfo();
    }

    // Method to get the list of attendees
    public List<String> getCampAttendees() {
        return camp.getAttendees();
    }

    // Method to get the list of Committee Members
    public List<String> getCampCommitteeMembers() {
        return camp.getCommitteeMembers();
    }

    public void addOnePoint() {
        points++;
    }

    public int getPoints() {
        return points;
    }

}
