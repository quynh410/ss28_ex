package business.service.fundstransfer;

import business.dao.fundstransfer.FundsTransferDao;
import business.dao.fundstransfer.FundsTransferDaoImp;
import business.model.FundsTransfer;

import java.util.Date;
import java.util.List;

public class FundsTransferServiceImp implements FundsTransferService {
    private final FundsTransferDao fundsTransferDao;

    public FundsTransferServiceImp() {
        fundsTransferDao = new FundsTransferDaoImp();
    }

    @Override
    public List<FundsTransfer> getTransferHistory() {
        return fundsTransferDao.getTransferHistory();
    }

    @Override
    public double getTotalTransferAmountByDateRange(Date startDate, Date endDate) {
        return fundsTransferDao.getTotalTransferAmountByDateRange(startDate, endDate);
    }

    @Override
    public double getTotalReceivedAmountByAccount(int accountId) {
        return fundsTransferDao.getTotalReceivedAmountByAccount(accountId);
    }

    @Override
    public int getSuccessfulTransferCountByDateRange(Date startDate, Date endDate) {
        return fundsTransferDao.getSuccessfulTransferCountByDateRange(startDate, endDate);
    }
}