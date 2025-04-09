package bt.bai7;

import java.sql.*;

public class IsolationLevelTest {

    public static void main(String[] args) throws Exception {
        testIsolationLevel(Connection.TRANSACTION_READ_UNCOMMITTED, "READ_UNCOMMITTED");
        testIsolationLevel(Connection.TRANSACTION_READ_COMMITTED, "READ_COMMITTED");
        testIsolationLevel(Connection.TRANSACTION_REPEATABLE_READ, "REPEATABLE_READ");
        testIsolationLevel(Connection.TRANSACTION_SERIALIZABLE, "SERIALIZABLE");
    }

    public static void testIsolationLevel(int isolationLevel, String levelName) throws Exception {
        System.out.println("\n==== Testing Isolation Level: " + levelName + " ====");

        Connection conn1 = ConnectionDB.getConnection(); // Reader
        Connection conn2 = ConnectionDB.getConnection(); // Writer

        try {
            conn1.setAutoCommit(false);
            conn1.setTransactionIsolation(isolationLevel);

            conn2.setAutoCommit(false);

            // 1. Writer chèn hoặc cập nhật nhưng chưa commit
            String insertSQL = "INSERT INTO orders (order_id, customer_name, status) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn2.prepareStatement(insertSQL)) {
                stmt.setInt(1, 100);
                stmt.setString(2, "Khách hàng test");
                stmt.setString(3, "Chờ duyệt");
                stmt.executeUpdate();
                System.out.println("→ Writer: INSERT chưa commit");
            }

            // 2. Reader thực hiện SELECT
            String selectSQL = "SELECT * FROM orders WHERE order_id = 100";
            try (PreparedStatement stmt = conn1.prepareStatement(selectSQL)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Reader đọc được: " + rs.getString("customer_name") + ", " + rs.getString("status"));
                } else {
                    System.out.println("Reader KHÔNG đọc được dữ liệu chưa commit.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn2.rollback();
            conn1.rollback();

            conn1.close();
            conn2.close();
        }
    }
}

