package Model;
import java.time.LocalDateTime;

public class Invoice {
    private int invoiceID;
    private double totalBill;
    private double gst;
    private double amountPaid;
    private double balance;
    private LocalDateTime dateTime;
    private int branchID;

    public Invoice(int invoiceID, double totalBill, double gst, double amountPaid, double balance, LocalDateTime dateTime, int branchID) {
        this.invoiceID = invoiceID;
        this.totalBill = totalBill;
        this.gst = gst;
        this.amountPaid = amountPaid;
        this.balance = balance;
        this.dateTime = dateTime;
        this.branchID = branchID;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(double totalBill) {
        this.totalBill = totalBill;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }
}
