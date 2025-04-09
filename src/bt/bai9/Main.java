package bt.bai9;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Bid(1, 1, 150);
        Bid(1, 1, 50);
        Bid(1, 1, 200);
    }

    public static void Bid(int auctionId, int userId, double bidAmount) {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            connection = ConnectionDB.openConnection();
            connection.setAutoCommit(false);

            String procedureCall = "{call placebid(?, ?, ?)}";
            callableStatement = connection.prepareCall(procedureCall);
            callableStatement.setInt(1, auctionId);
            callableStatement.setInt(2, userId);
            callableStatement.setDouble(3, bidAmount);

            callableStatement.executeUpdate();
            System.out.println("Đã đặt giá thầu thành công!");
        } catch (SQLException sqlEx) {
            System.out.println("Lỗi SQL: " + sqlEx.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (callableStatement != null) {
                    callableStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                throw new RuntimeException(closeEx);
            }
        }
    }
}
