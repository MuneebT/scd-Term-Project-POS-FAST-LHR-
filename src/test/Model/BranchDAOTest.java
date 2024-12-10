package test.Model;
import static org.junit.jupiter.api.Assertions.*;
import Controller.BranchManagementController;
import Model.Branch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class BranchDAOTest {
    private BranchManagementController bmc=new BranchManagementController();

    @Test
    public void testGetAllBranchesReturnsExpectedValues() {
        // Call the method to get all branches
        LinkedList<Branch> branches = bmc.returnAllBranches();

        // Ensure the list is not null
        Assertions.assertNotNull(branches, "The branch list should not be null.");

        // Ensure the list is not empty
        Assertions.assertFalse(branches.isEmpty(), "The branch list should not be empty.");

        // Check the first branch's details
        Branch firstBranch = branches.get(0);
        Assertions.assertEquals(1, firstBranch.getBranchID(), "Branch ID should match the expected value.");
        Assertions.assertEquals("Model Town", firstBranch.getBranchName(), "Branch name should match the expected value.");
        Assertions.assertEquals("Kandiaro", firstBranch.getBranchCity(), "Branch city should match the expected value.");
        Assertions.assertEquals("Active", firstBranch.getBranchStatus(), "Branch status should match the expected value.");
        Assertions.assertEquals("Ferozpur road",firstBranch.getBranchAddress());
        Assertions.assertEquals("03338222333",firstBranch.getBranchPhone());
        Assertions.assertEquals(1,firstBranch.getNoOfEmployees());
    }





}
