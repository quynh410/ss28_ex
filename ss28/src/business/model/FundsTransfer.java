package business.model;

import java.util.Date;

public class FundsTransfer {
    private int transferId;
    private int senderAccountId;
    private int receiverAccountId;
    private double amount;
    private Date transferDate;
    private String status;

    public FundsTransfer() {
    }

    public FundsTransfer(int transferId, int senderAccountId, int receiverAccountId, double amount, Date transferDate, String status) {
        this.transferId = transferId;
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.transferDate = transferDate;
        this.status = status;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(int senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public int getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(int receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FundsTransfer{" +
                "transferId=" + transferId +
                ", senderAccountId=" + senderAccountId +
                ", receiverAccountId=" + receiverAccountId +
                ", amount=" + amount +
                ", transferDate=" + transferDate +
                ", status='" + status + '\'' +
                '}';
    }
}