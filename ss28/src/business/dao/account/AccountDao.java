package business.dao.account;

import business.dao.AppDao;
import business.model.Account;
import business.model.AccountStatus;

import java.util.List;

public interface AccountDao extends AppDao {
    // Phương thức chuyển khoản hiện tại
    int fundsTransfer(int accSenderId, String accSenderName, int accReceiverId, String accReceiverName, double amount);

    // Thêm các phương thức mới
    List<Account> getAllAccounts();
    Account getAccountById(int accountId);
    Account getAccountByIdAndName(int accountId, String accountName);
    boolean createAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(int accountId);
    double checkBalance(int accountId, String accountName);
}