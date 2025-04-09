package bt.bai4;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false); // Tắt auto-commit

            BankService service = new BankService();
            service.transfer(conn, 1, 2, 1000.0);

            conn.commit();
            System.out.println("Chuyển tiền thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Lỗi xảy ra. Đã rollback giao dịch.");
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
