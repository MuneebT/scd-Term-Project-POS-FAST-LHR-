package Controller;

import View.BranchManagementView;

public class BranchManagementController {
    private BranchManagementView bmv;

    // Constructor takes the actual BranchManagementView instance
    public BranchManagementController(BranchManagementView bmv) {
        this.bmv = bmv;
    }

    // Return branch ID
    public int return_branch_id() {
        int row = bmv.bmtable.getSelectedRow();
        if (row == -1) return -1; // Handle case where no row is selected
        return Integer.parseInt(bmv.bmtable.getValueAt(row, 0).toString()); // Assuming ID is in the first column
    }

    // Return branch name
    public String return_Branch_name() {
        int row = bmv.bmtable.getSelectedRow();
        if (row == -1) return null;
        return (String) bmv.bmtable.getValueAt(row, 1); // Assuming name is in the second column
    }

    // Return branch city
    public String return_branch_city() {
        int row = bmv.bmtable.getSelectedRow();
        if (row == -1) return null;
        return (String) bmv.bmtable.getValueAt(row, 2); // Assuming city is in the third column
    }

    // Return branch status
    public String return_branch_status() {
        int row = bmv.bmtable.getSelectedRow();
        if (row == -1) return null;
        return (String) bmv.bmtable.getValueAt(row, 3); // Assuming status is in the fourth column
    }

    // Return branch address
    public String return_branch_address() {
        int row = bmv.bmtable.getSelectedRow();
        if (row == -1) return null;
        return (String) bmv.bmtable.getValueAt(row, 4); // Assuming address is in the fifth column
    }

    // Return branch phone number
    public String return_branch_phoneno() {
        int row = bmv.bmtable.getSelectedRow();
        if (row == -1) return null;
        return (String) bmv.bmtable.getValueAt(row, 5); // Assuming phone number is in the sixth column
    }
}
