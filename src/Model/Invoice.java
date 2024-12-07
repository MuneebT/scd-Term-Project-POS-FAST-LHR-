package Model;

public class Invoice {
    private int invoiceId;
    private double totalBill;
    private double gst;
    private double amountPaid;
    private double balance;


    public Invoice(int invoiceId, double totalBill, double gst, double amountPaid, double balance) {
        this.invoiceId = invoiceId;
        this.totalBill = totalBill;
        this.gst = gst;
        this.amountPaid = amountPaid;
        this.balance = balance;
    }

    // Getters and Setters
    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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
}
