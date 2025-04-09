package bt;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;


public class Bai1 {
    public static void main(String[] args) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            if (conn != null) {
                conn.setAutoCommit(false);

                String sql = "{call crease_user(?, ?)}";
                callSt = conn.prepareCall(sql);
                callSt.setString(1, "Quynh");
                callSt.setString(2, "quynh@gmail.com");

                callSt.execute();
                conn.commit();
                System.out.println("Người dùng đã được thêm thành công");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã rollback thay đổi do lỗi.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
    }
}
