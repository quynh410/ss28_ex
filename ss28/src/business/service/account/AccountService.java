package business.service.account;

import business.model.Account;
import business.service.AppService;

import java.util.List;

public interface AccountService extends AppService {
    // Phương thức chuyển khoản hiện tại
    int fundsTransfer(int accSenderId, String accSenderName, int accReceiverId, String accReceiverName, double amount);

    // Thêm các phương thức mới
    List<Account> getAllAccounts();
    Account getAccountById(int accountId);
    boolean createAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(int accountId);
    double checkBalance(int accountId, String accountName);
}