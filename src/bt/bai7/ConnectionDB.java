package bt.bai7;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/TransactionJDBC";
        String user = "root";
        String password = "YES";
        return DriverManager.getConnection(url, user, password);
    }
}
