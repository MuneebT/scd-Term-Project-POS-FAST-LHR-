package Model;

public class Order {
    private int productID;
    private String productName;
    private int productQuantity;
    private int vendorID;
    private String vendorName;
    private int branchID;

    // Default Constructor
    public Order() {
    }

    // Parameterized Constructor
    public Order(int productID, String productName, int productQuantity, int vendorID, String vendorName, int branchID) {
        this.productID = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.branchID = branchID;
    }

    // Getters
    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public int getVendorID() {
        return vendorID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public int getBranchID() {
        return branchID;
    }

    @Override
    public String toString() {
        return "Order{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productQuantity=" + productQuantity +
                ", vendorID=" + vendorID +
                ", vendorName='" + vendorName + '\'' +
                ", branchID=" + branchID +
                '}';
    }
}