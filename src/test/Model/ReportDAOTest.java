package test.Model;

import Model.BranchDAO;
import Model.ReportDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

class ReportDAOTest {

    @Test
    void testFetchSalesReportForLastYear() {
        ReportDAO reportDAO = new ReportDAO();
        Map<String, Double> report = reportDAO.fetchData("Sales", "Yearly", "1");
        assertNotNull(report, "Report should not be null.");
        assertTrue(report.size() > 0, "Report should have at least one entry.");
    }


    @Test
    void testFetchSalesReportForNonExistingBranch() {
        ReportDAO reportDAO = new ReportDAO();
        assertThrows(IllegalArgumentException.class, () -> reportDAO.fetchData("Sales", "Monthly", "9999"), "An IllegalArgumentException should be thrown for a non existent branch ID.");

    }



    @Test
    void testFetchRemainingStockForBranchWithMultipleCategories() {
        ReportDAO reportDAO = new ReportDAO();
        Map<String, Double> report = reportDAO.fetchData("Remaining Stock", null, "1");
        assertNotNull(report, "Report should not be null.");
        assertTrue(report.size() > 0, "Report should have entries for a branch with multiple categories.");
    }

    @Test
    void testFetchProfitReportForCurrentWeek() {
        ReportDAO reportDAO = new ReportDAO();
        Map<String, Double> report = reportDAO.fetchData("Profit", "Weekly", "1");
        assertNotNull(report, "Report should not be null.");
        assertEquals(7, report.size(), "Report should have 7 entries for a weekly profit report.");
    }



    @Test
    void testInvalidTimeRange() {
        ReportDAO reportDAO = new ReportDAO();
        Map<String, Double> report = reportDAO.fetchData("Sales", "Invalid", "1");
        assertNotNull(report, "Report should not be null.");
        assertTrue(report.isEmpty(), "Report should be empty for an invalid time range.");
    }

    @Test
    void testInvalidReportType() {
        ReportDAO reportDAO = new ReportDAO();
        Map<String, Double> report = reportDAO.fetchData("Unknown", "Yearly", "1");
        assertNotNull(report, "Report should not be null.");
        assertTrue(report.isEmpty(), "Report should be empty for an unknown report type.");
    }

    @Test
    void testNullBranchID() {
        ReportDAO reportDAO = new ReportDAO();
        assertThrows(IllegalArgumentException.class, () -> reportDAO.fetchData("Sales", "Monthly", null), "An IllegalArgumentException should be thrown for a null branch ID.");
    }

    @Test
    void testSimultaneousReportFetchRequests() {
        ReportDAO reportDAO = new ReportDAO();
        Runnable task1 = () -> {
            Map<String, Double> report = reportDAO.fetchData("Sales", "Monthly", "1");
            assertNotNull(report, "Task 1 report should not be null.");
        };
        Runnable task2 = () -> {
            Map<String, Double> report = reportDAO.fetchData("Profit", "Weekly", "1");
            assertNotNull(report, "Task 2 report should not be null.");
        };
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            fail("Threads should not be interrupted.");
        }
    }

    @Test
    void testFetchDataOnBoundaryOfWeeklyRange() {
        ReportDAO reportDAO = new ReportDAO();
        Map<String, Double> report = reportDAO.fetchData("Profit", "Weekly", "1");
        assertNotNull(report, "Report should not be null.");
        assertEquals(7, report.size(), "Report should have 7 entries for a weekly range.");
    }



    @Test
    void testDatabaseConnectionFails() {
        ReportDAO reportDAO = new ReportDAO() {
            @Override
            public LinkedHashMap<String, Double> fetchData(String reportType, String timeRange, String branchID) {
                throw new RuntimeException("Database connection failed");
            }
        };
        assertThrows(RuntimeException.class, () -> reportDAO.fetchData("Sales", "Monthly", "1"), "A RuntimeException should be thrown if the database connection fails.");
    }
}
