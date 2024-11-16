package Controller;

import Model.LoginDAO;

import java.sql.SQLException;

public class LoginController {
   public LoginDAO loginDAO=new LoginDAO();
    public LoginController() {
    }

    public boolean redirect_validateUser(String name,String password,String designation) throws SQLException {
        return loginDAO.validateUser(name,password,designation);

    }
}
