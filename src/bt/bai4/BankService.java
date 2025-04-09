package bt.bai4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankService {
    public void transfer(Connection conn, int fromId, int toId, double amount) throws SQLException {
        // Trừ tiền từ tài khoản nguồn
        String deductSQL = "UPDATE bank_accounts SET balance = balance - ? WHERE account_id = ?";
        try (PreparedStatement deductStmt = conn.prepareStatement(deductSQL)) {
            deductStmt.setDouble(1, amount);
            deductStmt.setInt(2, fromId);
            int affected = deductStmt.executeUpdate();
            if (affected != 1) throw new SQLException("Không trừ được tiền.");
        }

        // Cộng tiền vào tài khoản đích
        String addSQL = "UPDATE bank_accounts SET balance = balance + ? WHERE account_id = ?";
        try (PreparedStatement addStmt = conn.prepareStatement(addSQL)) {
            addStmt.setDouble(1, amount);
            addStmt.setInt(2, toId);
            int affected = addStmt.executeUpdate();
            if (affected != 1) throw new SQLException("Không cộng được tiền.");
        }
    }
}

