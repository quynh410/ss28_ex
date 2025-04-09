package business.service.account;

import business.dao.account.AccountDao;
import business.dao.account.AccountDaoImp;
import business.model.Account;

import java.util.List;

public class AccountServiceImp implements AccountService {
    private final AccountDao accountDao;

    public AccountServiceImp() {
        accountDao = new AccountDaoImp();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Override
    public Account getAccountById(int accountId) {
        return accountDao.getAccountById(accountId);
    }

    @Override
    public boolean createAccount(Account account) {
        return accountDao.createAccount(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }

    @Override
    public boolean deleteAccount(int accountId) {
        return accountDao.deleteAccount(accountId);
    }

    @Override
    public double checkBalance(int accountId, String accountName) {
        return accountDao.checkBalance(accountId, accountName);
    }

    @Override
    public int fundsTransfer(int accSenderId, String accSenderName, int accReceiverId, String accReceiverName, double amount) {
        return accountDao.fundsTransfer(accSenderId, accSenderName, accReceiverId, accReceiverName, amount);
    }
}