package Model;

import Connection.ConnectionConfigurator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PaymentReset {

    private static boolean isScheduled = false; // Flag to prevent re-scheduling

    // Private constructor to prevent instantiation
    private PaymentReset() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    public static void scheduleMonthlyPaymentReset() {
        if (isScheduled) {
            System.out.println("Task is already scheduled.");
            return; // Prevent multiple scheduling
        }

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Calculate the initial delay to the first day of the next month
        long initialDelay = calculateInitialDelay();

        // Schedule the reset task to run monthly
        scheduler.scheduleAtFixedRate(() -> {
            try {
                resetPaymentStatus();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, initialDelay, 30L, TimeUnit.DAYS); // Repeats approximately every 30 days

        isScheduled = true; // Mark as scheduled
        System.out.println("Payment reset task scheduled successfully.");
    }

    private static void resetPaymentStatus() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionConfigurator.getConnection();
            System.out.println("Query\n");
            // SQL query to reset the `paid` status
            String query = "UPDATE Employee SET paid = FALSE, lastPaidDate = NULL";
            ps = conn.prepareStatement(query);

            int rowsUpdated = ps.executeUpdate();
            System.out.println("Payment status reset for " + rowsUpdated + " employees.");
        } catch (SQLException e) {
            throw new RuntimeException("Error while resetting payment status.", e);
        } finally {
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }

    private static long calculateInitialDelay() {
        LocalDate now = LocalDate.now();
        LocalDate firstDayOfNextMonth = now.with(TemporalAdjusters.firstDayOfNextMonth());

        // Convert LocalDate to Date for delay calculation
        Date nextMonthDate = Date.from(firstDayOfNextMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        long currentTimeMillis = System.currentTimeMillis();
        long nextMonthTimeMillis = nextMonthDate.getTime();

        // Calculate delay in milliseconds and convert to seconds
        return (nextMonthTimeMillis - currentTimeMillis) / 1000;
    }

    public static void main(String[] args) {
        // Call the function to start the scheduler
        scheduleMonthlyPaymentReset();
    }
}
