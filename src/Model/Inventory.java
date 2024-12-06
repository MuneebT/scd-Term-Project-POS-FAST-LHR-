package Model;

public class Inventory {
    private int productId;
    private String productName;
    private int productQuantity;
    private String productCategory;
    private int costPrice;
    private int salePrice;


    public Inventory() {
    }


    public Inventory(int productId, String productName, int productQuantity, String productCategory, int costPrice, int salePrice) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productCategory = productCategory;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public int getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(int costPrice) {
        this.costPrice = costPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productQuantity=" + productQuantity +
                ", productCategory='" + productCategory + '\'' +
                ", costPrice=" + costPrice +
                ", salePrice=" + salePrice +
                '}';
    }
}
