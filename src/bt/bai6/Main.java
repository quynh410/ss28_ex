package bt.bai6;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection conn = null;

        try {
            conn = ConnectionDB.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            DepartmentService service = new DepartmentService();

            int deptId = 10;
            String deptName = "Phòng Nhân sự";

            List<Employee> employees = Arrays.asList(
                    new Employee(101, "Nguyễn Văn A"),
                    new Employee(102, "Trần Thị B"),
                    new Employee(103, "") // Lỗi: tên rỗng → sẽ rollback toàn bộ
            );

            service.createDepartmentWithEmployees(conn, deptId, deptName, employees);

            conn.commit();
            System.out.println("Thêm phòng ban và nhân viên thành công!");
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

