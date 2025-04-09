package business.dao.fundstransfer;

import business.dao.AppDao;
import business.model.FundsTransfer;

import java.util.Date;
import java.util.List;

public interface FundsTransferDao extends AppDao {
    List<FundsTransfer> getTransferHistory();
    double getTotalTransferAmountByDateRange(Date startDate, Date endDate);
    double getTotalReceivedAmountByAccount(int accountId);
    int getSuccessfulTransferCountByDateRange(Date startDate, Date endDate);
}
