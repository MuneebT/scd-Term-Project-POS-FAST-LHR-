package Model;

import java.time.LocalDateTime;

public class Invoice {
    private int invoiceNumber;
    private double totalBill;
    private double gst;
    private double amountPaid;
    private double balance;
    private LocalDateTime dateTime; // Added attribute

    public Invoice(int invoiceNumber, double totalBill, double gst, double amountPaid, double balance, LocalDateTime dateTime) {
        this.invoiceNumber = invoiceNumber;
        this.totalBill = totalBill;
        this.gst = gst;
        this.amountPaid = amountPaid;
        this.balance = balance;
        this.dateTime = dateTime;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public double getGst() {
        return gst;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
