package Controller;

import Model.LoginDAO;
import Model.PayDao;

import java.math.BigDecimal;
import java.sql.SQLException;

public class PayController {
    public LoginDAO loginDAO=new LoginDAO();
    public PayDao payDao=new PayDao();


    public boolean redirect_validateUser(String name,String password,String designation,String branch) throws SQLException {
        return loginDAO.validateUserPay(name,password,designation,branch);

    }
    public BigDecimal redirect_getPay(String name, String password, String designation, String branch) throws SQLException {
        return payDao.return_pay(name,password,designation,branch);

    }
   public boolean redirect_getStatus(String name, String password, String designation, String branch)
   {
       return payDao.getStatus(name,password,designation,branch);
   }
   public boolean redirect_updateStatus(String name, String password, String designation, String branch)
   {
       return payDao.setPaidStatus(name,password,designation,branch);
   }



}
