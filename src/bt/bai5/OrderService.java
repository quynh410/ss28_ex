package bt.bai5;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    public void createOrderWithDetails(Connection conn, int orderId, String customerName, Date orderDate,
                                       List<OrderDetail> details) throws SQLException {
        // 1. Thêm đơn hàng
        String insertOrderSQL = "insert into orders (order_id, customer_name, order_date) values (?, ?, ?)";
        try (PreparedStatement orderStmt = conn.prepareStatement(insertOrderSQL)) {
            orderStmt.setInt(1, orderId);
            orderStmt.setString(2, customerName);
            orderStmt.setDate(3, orderDate);
            orderStmt.executeUpdate();
        }

        // 2. Thêm các chi tiết đơn hàng
        String insertDetailSQL = "insert into order_details (detail_id, order_id, product_name, quantity) values (?, ?, ?, ?)";
        try (PreparedStatement detailStmt = conn.prepareStatement(insertDetailSQL)) {
            for (OrderDetail detail : details) {
                if (detail.getQuantity() < 0) {
                    throw new SQLException("Số lượng không được âm: " + detail.getProductName());
                }
                detailStmt.setInt(1, detail.getDetailId());
                detailStmt.setInt(2, orderId);
                detailStmt.setString(3, detail.getProductName());
                detailStmt.setInt(4, detail.getQuantity());
                detailStmt.executeUpdate();
            }
        }
    }
}

