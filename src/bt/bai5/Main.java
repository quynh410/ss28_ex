package bt.bai5;

import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false);
            OrderService orderService = new OrderService();

            int orderId = 1001;
            String customerName = "Nguyen Van A";
            Date orderDate = new Date(System.currentTimeMillis());

            List<OrderDetail> details = Arrays.asList(
                    new OrderDetail(1, "Chuột không dây", 2),
                    new OrderDetail(2, "Bàn phím cơ", 1),
                    new OrderDetail(3, "Tai nghe gaming", -5)
            );

            orderService.createOrderWithDetails(conn, orderId, customerName, orderDate, details);

            conn.commit();
            System.out.println("Đơn hàng và chi tiết đã được thêm thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Lỗi xảy ra. Đã rollback toàn bộ giao dịch.");
                }
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}

