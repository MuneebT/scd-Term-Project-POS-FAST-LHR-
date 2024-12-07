package Model;

import java.util.LinkedList;

public class Branch
{

    int branchID;
    String branchName;
    String branchCity;
    String branchStatus;
    String branchAddress;
    String branchPhone;
    int noOfEmployees;
    public Branch(){

    }
    public Branch(int id,String name,String city,String status,String address,String phoneno,int noofemployees){
      branchID=id;
      branchName=name;
      branchCity=city;
      branchStatus=status;
      branchAddress=address;
      branchPhone=phoneno;
      this.noOfEmployees=noofemployees;
    }

  public int getNoOfEmployees() {

      return noOfEmployees;
  }

  public void setNoOfEmployees(int noOfEmployees) {
    this.noOfEmployees = noOfEmployees;
  }

  public int getBranchID() {
    return branchID;
  }

  public void setBranchID(int branchID) {
    this.branchID = branchID;
  }

  public String getBranchName() {
    return branchName;
  }

  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }

  public String getBranchCity() {
    return branchCity;
  }

  public void setBranchCity(String branchCity) {
    this.branchCity = branchCity;
  }

  public String getBranchStatus() {
    return branchStatus;
  }

  public void setBranchStatus(String branchStatus) {
    this.branchStatus = branchStatus;
  }

  public String getBranchAddress() {
    return branchAddress;
  }

  public void setBranchAddress(String branchAddress) {
    this.branchAddress = branchAddress;
  }

  public String getBranchPhone() {
    return branchPhone;
  }

  public void setBranchPhone(String branchPhone) {
    this.branchPhone = branchPhone;
  }


public LinkedList<String> concatenateBranchIDandBranchName(){
LinkedList<Branch> branches=BranchDAO.getAllBranches();
LinkedList<String> concatenateddata=new LinkedList<>();
String data;
for(int i=0;i<branches.size();i++){
  data=branches.get(i).getBranchID()+"_"+branches.get(i).getBranchName();
  concatenateddata.add(data);
}

return concatenateddata;
}


}


