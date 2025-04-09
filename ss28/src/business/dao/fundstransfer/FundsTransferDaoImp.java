package business.dao.fundstransfer;

import business.config.ConnectionDB;
import business.model.FundsTransfer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FundsTransferDaoImp implements FundsTransferDao {

    @Override
    public List<FundsTransfer> getTransferHistory() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<FundsTransfer> transferList = new ArrayList<>();

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_transfer_history()}");
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                FundsTransfer transfer = new FundsTransfer();
                transfer.setTransferId(rs.getInt("transfer_id"));
                transfer.setSenderAccountId(rs.getInt("sender_account_id"));
                transfer.setReceiverAccountId(rs.getInt("receiver_account_id"));
                transfer.setAmount(rs.getDouble("amount"));
                transfer.setTransferDate(rs.getTimestamp("transfer_date"));
                transfer.setStatus(rs.getString("status"));
                transferList.add(transfer);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy lịch sử giao dịch: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return transferList;
    }

    @Override
    public double getTotalTransferAmountByDateRange(Date startDate, Date endDate) {
        Connection conn = null;
        CallableStatement callSt = null;
        double totalAmount = 0;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_total_transfer_amount_by_date_range(?,?,?)}");
            callSt.setDate(1, new java.sql.Date(startDate.getTime()));
            callSt.setDate(2, new java.sql.Date(endDate.getTime()));
            callSt.registerOutParameter(3, Types.DOUBLE);

            callSt.execute();
            totalAmount = callSt.getDouble(3);
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê số tiền chuyển: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return totalAmount;
    }

    @Override
    public double getTotalReceivedAmountByAccount(int accountId) {
        Connection conn = null;
        CallableStatement callSt = null;
        double totalAmount = 0;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_total_received_amount_by_account(?,?)}");
            callSt.setInt(1, accountId);
            callSt.registerOutParameter(2, Types.DOUBLE);

            callSt.execute();
            totalAmount = callSt.getDouble(2);
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê số tiền nhận: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return totalAmount;
    }

    @Override
    public int getSuccessfulTransferCountByDateRange(Date startDate, Date endDate) {
        Connection conn = null;
        CallableStatement callSt = null;
        int count = 0;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_successful_transfer_count_by_date_range(?,?,?)}");
            callSt.setDate(1, new java.sql.Date(startDate.getTime()));
            callSt.setDate(2, new java.sql.Date(endDate.getTime()));
            callSt.registerOutParameter(3, Types.INTEGER);

            callSt.execute();
            count = callSt.getInt(3);
        } catch (SQLException e) {
            System.err.println("Lỗi khi thống kê số giao dịch thành công: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return count;
    }
}