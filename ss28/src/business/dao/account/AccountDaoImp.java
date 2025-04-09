package business.dao.account;

import business.config.ConnectionDB;
import business.model.Account;
import business.model.AccountStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImp implements AccountDao {

    @Override
    public List<Account> getAllAccounts() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Account> accountList = new ArrayList<>();

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_all_accounts()}");
            ResultSet rs = callSt.executeQuery();

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("account_id"));
                account.setName(rs.getString("account_name"));
                account.setBalance(rs.getDouble("balance"));
                account.setStatus(AccountStatus.valueOf(rs.getString("status")));
                accountList.add(account);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách tài khoản: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return accountList;
    }

    @Override
    public Account getAccountById(int accountId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Account account = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_account_by_id(?)}");
            callSt.setInt(1, accountId);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("account_id"));
                account.setName(rs.getString("account_name"));
                account.setBalance(rs.getDouble("balance"));
                account.setStatus(AccountStatus.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin tài khoản: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return account;
    }

    @Override
    public Account getAccountByIdAndName(int accountId, String accountName) {
        Connection conn = null;
        CallableStatement callSt = null;
        Account account = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call get_account_by_id_and_name(?,?)}");
            callSt.setInt(1, accountId);
            callSt.setString(2, accountName);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("account_id"));
                account.setName(rs.getString("account_name"));
                account.setBalance(rs.getDouble("balance"));
                account.setStatus(AccountStatus.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin tài khoản: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return account;
    }

    @Override
    public boolean createAccount(Account account) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call create_account(?,?,?,?)}");
            callSt.setInt(1, account.getId());
            callSt.setString(2, account.getName());
            callSt.setDouble(3, account.getBalance());
            callSt.setString(4, account.getStatus().toString());

            int rowsAffected = callSt.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo tài khoản: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return result;
    }

    @Override
    public boolean updateAccount(Account account) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call update_account(?,?,?)}");
            callSt.setInt(1, account.getId());
            callSt.setString(2, account.getName());
            callSt.setString(3, account.getStatus().toString());

            int rowsAffected = callSt.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật tài khoản: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return result;
    }

    @Override
    public boolean deleteAccount(int accountId) {
        Connection conn = null;
        CallableStatement callSt = null;
        boolean result = false;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call delete_account(?)}");
            callSt.setInt(1, accountId);

            int rowsAffected = callSt.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa tài khoản: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return result;
    }

    @Override
    public double checkBalance(int accountId, String accountName) {
        Connection conn = null;
        CallableStatement callSt = null;
        double balance = -1;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("{call check_balance(?,?,?)}");
            callSt.setInt(1, accountId);
            callSt.setString(2, accountName);
            callSt.registerOutParameter(3, Types.DOUBLE);

            callSt.execute();
            balance = callSt.getDouble(3);
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra số dư: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }

        return balance;
    }

    @Override
    public int fundsTransfer(int accSenderId, String accSenderName, int accReceiverId, String accReceiverName, double amount) {
        /*
         * 1. Khởi tạo đối tượng Connection
         * 2. Khởi tạo đối tượng CallableStatement
         * 3. Set giá trị cho các tham số vào
         * 4. Đăng ký kiểu dữ liệu cho tham số ra
         * 5. Thực hiện gọi procedure: executeQuery(), executeUpdate(), execute()
         * 6. Xử lý các dữ liệu nhận được
         * */
        Connection conn = null;
        CallableStatement callSt = null;
        try {
            conn = ConnectionDB.openConnection();
            //set autoComit là false
            conn.setAutoCommit(false);
            callSt = conn.prepareCall("{call funds_transfer_amount(?,?,?,?,?,?)}");
            callSt.setInt(1, accSenderId);
            callSt.setString(2, accSenderName);
            callSt.setInt(3, accReceiverId);
            callSt.setString(4, accReceiverName);
            callSt.setDouble(5, amount);
            callSt.registerOutParameter(6, Types.INTEGER);
            callSt.execute();
            conn.commit();
            return callSt.getInt(6);
        } catch (SQLException e) {
            System.err.println("Có lỗi xảy ra trong quá trình chuyển khoản, dữ liệu đã được rollback");
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            System.err.println("Có lỗi không xác định khi làm việc với db: " + e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return 0;
    }
}