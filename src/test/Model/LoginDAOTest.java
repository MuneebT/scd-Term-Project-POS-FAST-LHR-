package test.Model;

import Connection.ConnectionConfigurator;
import Controller.LoginController;
import Model.EmployeeDao;
import Model.LoggedEmp;
import Model.LoginDAO;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginDAOTest {

    private static LoginDAO loginDAO;

    @BeforeAll
    public static void setup() throws SQLException {
        loginDAO = new LoginDAO();
        EmployeeDao empDAO=new EmployeeDao();
        empDAO.insertEmployee("John", "EMP001", "john@nu.edu.pk", "password123", "1", "50000", "Branch Manager");
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        EmployeeDao empDAO=new EmployeeDao();
        empDAO.deleteEmployee("EMP001");
    }


    @Test
    public void testInvalidUser() throws SQLException {
        boolean isValidUser = loginDAO.validateUser("John", "wrongpassword", "Branch Manager", "1");
        assertFalse(isValidUser, "Expected invalid user due to incorrect password.");
    }

    @Test
    public void testFirstTimeLoginStatus() throws SQLException {
        int firstTimeLoginStatus = loginDAO.getFirstTimeLoginStatus("John", "password123", "Branch Manager");
        assertEquals(0, firstTimeLoginStatus, "Expected first time login status to be 0.");
    }

    @Test
    public void testUpdatePassword() throws SQLException {

        //testing validate user here because otherwise on change password it get failed.
        boolean isValidUser = loginDAO.validateUser("John", "password123", "Branch Manager", "1");

        assertTrue(isValidUser, "Expected valid user for the given credentials.");
        LoginController controller=new LoginController();
        controller.redirect_set_credientials("John","password123","Branch Manager","1");
        String currentPassword = "password123";
        String newPassword = "newpassword123";

        loginDAO.updatePassword(currentPassword, newPassword);

        isValidUser = loginDAO.validateUser("John", newPassword, "Branch Manager", "1");
        assertTrue(isValidUser, "Expected valid user for the updated password.");
    }
}
