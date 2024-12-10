package Model;

public class LoggedEmp {
    private static LoggedEmp instance;

    private String userName;
    private String password;
    private String designation;
    private String branch;
    private int firstTimePasswordChange;

    private LoggedEmp() {}

    public static LoggedEmp getInstance() {
        if (instance == null) {
            instance = new LoggedEmp();
        }
        return instance;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public static void setInstance(LoggedEmp instance) {
        LoggedEmp.instance = instance;
    }

    public int getFirstTimePasswordChange() {
        return firstTimePasswordChange;
    }

    public void setFirstTimePasswordChange(int firstTimePasswordChange) {
        this.firstTimePasswordChange = firstTimePasswordChange;
    }
}
