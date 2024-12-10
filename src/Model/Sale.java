package Model;

public class Sale {
    private int saleId;
    private int prodId;
    private String prodName;
    private double price;
    private int quantity;
    private double totalPrice;
    private int invoiceNumber;
    private int branchID;


    public Sale(int saleId, int prodId, String prodName, double price, int quantity, double totalPrice, int invoiceNumber, int branchID) {
        this.saleId = saleId;
        this.prodId = prodId;
        this.prodName = prodName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.invoiceNumber = invoiceNumber;
        this.branchID = branchID; // Initialize branchID
    }

    // Default constructor
    public Sale() {
    }

    // Getters and Setters
    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }
}
