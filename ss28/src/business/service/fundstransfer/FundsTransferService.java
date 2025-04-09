package business.service.fundstransfer;

import business.model.FundsTransfer;
import business.service.AppService;

import java.util.Date;
import java.util.List;

public interface FundsTransferService extends AppService {
    List<FundsTransfer> getTransferHistory();
    double getTotalTransferAmountByDateRange(Date startDate, Date endDate);
    double getTotalReceivedAmountByAccount(int accountId);
    int getSuccessfulTransferCountByDateRange(Date startDate, Date endDate);
}