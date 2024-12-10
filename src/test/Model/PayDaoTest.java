package test.Model;

import Connection.ConnectionConfigurator;
import Model.PayDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class PayDaoTest {
    private PayDao payDao;
    private Connection connection;

    @BeforeAll
    static void setupDatabaseDriver() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC driver", e);
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        payDao = new PayDao();
        connection = ConnectionConfigurator.getConnection();
        //createTestData(); // Insert initial test data
    }

    @AfterEach
    void tearDown() throws Exception {
       // cleanTestData(); // Remove all test data
        if (connection != null) connection.close();
    }

    @Test
    void testReturnPay_ValidEmployee() throws SQLException {
        BigDecimal salary = payDao.return_pay("John", "password123", "Branch Manager", "001");
        assertEquals(5000.00, salary);
    }

    @Test
    void testReturnPay_InvalidEmployee() throws SQLException {
        BigDecimal salary = payDao.return_pay("InvalidUser", "wrongPassword", "Staff", "002");
        assertEquals(BigDecimal.ZERO, salary);
    }

    @Test
    void testGetStatus_ValidEmployeePaid() throws SQLException {
        boolean status = payDao.getStatus("Jane", "password123", "Branch Manager", "001");
        assertTrue(status);
    }


    @Test
    void testSetPaidStatus_InvalidEmployee() throws SQLException {
        boolean statusUpdated = payDao.setPaidStatus("InvalidUser", "wrongPassword", "Staff", "002");
        assertFalse(statusUpdated);
    }

    // Insert test data into the database
    private void createTestData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(
                    "INSERT INTO employee (id, emp_no, name, password, email, branch_code, salary, designation, firstPasswordChangedStatus, paid) VALUES " +
                            "(3, 'EMP003', 'John', 'password123', 'john@example.com', '001', 5000.00, 'Manager', TRUE, FALSE), " +
                            "(4, 'EMP004', 'Jane', 'password123', 'jane@example.com', '001', 6000.00, 'Manager', TRUE, TRUE), " +
                            "(5, 'EMP005', 'Alice', 'alicepass', 'alice@example.com', '002', 4000.00, 'Staff', FALSE, FALSE)"
            );
        }
    }

    // Remove test data from the database
    private void cleanTestData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM employee WHERE name IN ('John', 'Jane', 'Alice')");
        }
    }
}
