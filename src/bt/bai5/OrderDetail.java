package bt.bai5;

public class OrderDetail {
    private int detailId;
    private String productName;
    private int quantity;

    public OrderDetail(int detailId, String productName, int quantity) {
        this.detailId = detailId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public int getDetailId() {
        return detailId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "detailId=" + detailId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
