package bt.bai6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DepartmentService {
    public void createDepartmentWithEmployees(Connection conn, int deptId, String deptName, List<Employee> employees)
            throws SQLException {
        // 1. Thêm phòng ban
        String insertDept = "INSERT INTO departments (id, name) VALUES (?, ?)";
        try (PreparedStatement deptStmt = conn.prepareStatement(insertDept)) {
            deptStmt.setInt(1, deptId);
            deptStmt.setString(2, deptName);
            deptStmt.executeUpdate();
        }

        // 2. Thêm nhân viên
        String insertEmp = "INSERT INTO employees (id, name, department_id) VALUES (?, ?, ?)";
        try (PreparedStatement empStmt = conn.prepareStatement(insertEmp)) {
            for (Employee emp : employees) {
                if (emp.getName() == null || emp.getName().isEmpty()) {
                    throw new SQLException("Tên nhân viên không được để trống");
                }
                empStmt.setInt(1, emp.getId());
                empStmt.setString(2, emp.getName());
                empStmt.setInt(3, deptId);
                empStmt.executeUpdate();
            }
        }
    }
}
