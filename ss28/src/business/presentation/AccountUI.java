package business.presentation;
import business.model.Account;
import business.model.AccountStatus;
import business.service.account.AccountService;
import business.service.account.AccountServiceImp;

import java.util.List;
import java.util.Scanner;

public class AccountUI {

    public static void displayAccountMenu(Scanner scanner) {
        AccountService accountService = new AccountServiceImp();
        do {
            System.out.println("***************ACCOUNT MENU**************");
            System.out.println("1. Danh sách tài khoản");
            System.out.println("2. Tạo tài khoản");
            System.out.println("3. Cập nhật tài khoản"); //Tên + trạng thái
            System.out.println("4. Xóa tài khoản");//Cập nhật trạng thái là inactive
            System.out.println("5. Chuyển khoản");
            System.out.println("6. Tra cứu số dư tài khoản");
            System.out.println("7. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    displayAllAccounts(accountService);
                    break;
                case 2:
                    createAccount(scanner, accountService);
                    break;
                case 3:
                    updateAccount(scanner, accountService);
                    break;
                case 4:
                    deleteAccount(scanner, accountService);
                    break;
                case 5:
                    fundsTransfer(scanner, accountService);
                    break;
                case 6:
                    checkBalance(scanner, accountService);
                    break;
                case 7:
                    System.out.println("Cảm ơn bạn đã sử dụng dịch vụ!");
                    System.exit(0);
                    break;
                case 8:
                    // Menu phụ cho chức năng quản lý giao dịch
                    FundsTransferUI.displayFundsTransferMenu(scanner);
                    break;
                default:
                    System.err.println("Vui lòng chọn từ 1-7");
            }
        } while (true);
    }

    private static void displayAllAccounts(AccountService accountService) {
        List<Account> accounts = accountService.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("Không có tài khoản nào trong hệ thống");
        } else {
            System.out.println("***************DANH SÁCH TÀI KHOẢN***************");
            System.out.printf("%-10s %-30s %-15s %-10s%n", "ID", "TÊN TÀI KHOẢN", "SỐ DƯ", "TRẠNG THÁI");
            System.out.println("---------------------------------------------------");
            for (Account acc : accounts) {
                System.out.printf("%-10d %-30s %-15.2f %-10s%n",
                        acc.getId(), acc.getName(), acc.getBalance(), acc.getStatus());
            }
        }
    }

    private static void createAccount(Scanner scanner, AccountService accountService) {
        System.out.println("***************TẠO TÀI KHOẢN MỚI***************");

        System.out.print("Nhập số tài khoản: ");
        int accountId = Integer.parseInt(scanner.nextLine());

        // Kiểm tra xem ID đã tồn tại chưa
        if (accountService.getAccountById(accountId) != null) {
            System.err.println("Số tài khoản đã tồn tại trong hệ thống!");
            return;
        }

        System.out.print("Nhập tên tài khoản: ");
        String accountName = scanner.nextLine();

        System.out.print("Nhập số dư ban đầu: ");
        double balance = Double.parseDouble(scanner.nextLine());

        // Mặc định tài khoản mới sẽ có trạng thái ACTIVE
        Account newAccount = new Account(accountId, accountName, balance, AccountStatus.ACTIVE);

        boolean result = accountService.createAccount(newAccount);
        if (result) {
            System.out.println("Tạo tài khoản thành công!");
        } else {
            System.err.println("Tạo tài khoản thất bại!");
        }
    }

    private static void updateAccount(Scanner scanner, AccountService accountService) {
        System.out.println("***************CẬP NHẬT TÀI KHOẢN***************");

        System.out.print("Nhập số tài khoản cần cập nhật: ");
        int accountId = Integer.parseInt(scanner.nextLine());

        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            System.err.println("Không tìm thấy tài khoản với số tài khoản " + accountId);
            return;
        }

        System.out.println("Thông tin tài khoản hiện tại:");
        System.out.printf("ID: %d, Tên: %s, Số dư: %.2f, Trạng thái: %s%n",
                account.getId(), account.getName(), account.getBalance(), account.getStatus());

        System.out.print("Nhập tên tài khoản mới (Enter để giữ nguyên): ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            account.setName(newName);
        }

        System.out.println("Chọn trạng thái mới:");
        System.out.println("1. ACTIVE");
        System.out.println("2. INACTIVE");
        System.out.println("3. BLOCKED");
        System.out.print("Lựa chọn của bạn (Enter để giữ nguyên): ");
        String statusChoice = scanner.nextLine();

        if (!statusChoice.isEmpty()) {
            switch (Integer.parseInt(statusChoice)) {
                case 1:
                    account.setStatus(AccountStatus.ACTIVE);
                    break;
                case 2:
                    account.setStatus(AccountStatus.INACTIVE);
                    break;
                case 3:
                    account.setStatus(AccountStatus.BLOCKED);
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ, giữ nguyên trạng thái cũ");
            }
        }

        boolean result = accountService.updateAccount(account);
        if (result) {
            System.out.println("Cập nhật tài khoản thành công!");
        } else {
            System.err.println("Cập nhật tài khoản thất bại!");
        }
    }

    private static void deleteAccount(Scanner scanner, AccountService accountService) {
        System.out.println("***************XÓA TÀI KHOẢN***************");

        System.out.print("Nhập số tài khoản cần xóa: ");
        int accountId = Integer.parseInt(scanner.nextLine());

        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            System.err.println("Không tìm thấy tài khoản với số tài khoản " + accountId);
            return;
        }

        System.out.println("Thông tin tài khoản cần xóa:");
        System.out.printf("ID: %d, Tên: %s, Số dư: %.2f, Trạng thái: %s%n",
                account.getId(), account.getName(), account.getBalance(), account.getStatus());

        System.out.print("Bạn có chắc chắn muốn xóa tài khoản này? (Y/N): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("Y")) {
            boolean result = accountService.deleteAccount(accountId);
            if (result) {
                System.out.println("Xóa tài khoản thành công!");
            } else {
                System.err.println("Xóa tài khoản thất bại!");
            }
        } else {
            System.out.println("Đã hủy thao tác xóa tài khoản.");
        }
    }

    private static void checkBalance(Scanner scanner, AccountService accountService) {
        System.out.println("***************TRA CỨU SỐ DƯ TÀI KHOẢN***************");

        System.out.print("Nhập số tài khoản: ");
        int accountId = Integer.parseInt(scanner.nextLine());

        System.out.print("Nhập tên tài khoản: ");
        String accountName = scanner.nextLine();

        double balance = accountService.checkBalance(accountId, accountName);

        if (balance >= 0) {
            System.out.printf("Số dư tài khoản %d - %s là: %.2f%n", accountId, accountName, balance);
        } else {
            System.err.println("Không tìm thấy thông tin tài khoản hoặc thông tin không khớp.");
        }
    }

    public static void fundsTransfer(Scanner scanner, AccountService accountService) {
        System.out.println("***************CHUYỂN KHOẢN***************");
        System.out.println("Nhập số tài khoản người gửi:");
        int accSenderId = Integer.parseInt(scanner.nextLine());
        System.out.println("Nhập tên tài khoản người gửi:");
        String accSenderName = scanner.nextLine();
        System.out.println("Nhập số tài khoản người nhận:");
        int accReceiverId = Integer.parseInt(scanner.nextLine());
        System.out.println("Nhập tên tài khoản người nhận:");
        String accReceiverName = scanner.nextLine();
        System.out.println("Nhập số tiền chuyển:");
        double amount = Double.parseDouble(scanner.nextLine());
        int result = accountService.fundsTransfer(accSenderId, accSenderName, accReceiverId, accReceiverName, amount);
        switch (result) {
            case 1:
                System.err.println("Thông tin tài khoản người gửi không chính xác");
                break;
            case 2:
                System.err.println("Thông tin tài khoản người nhận không chính xác");
                break;
            case 3:
                System.err.println("Số dư tài khoản không đủ để chuyển khoản");
                break;
            case 4:
                System.out.println("Chuyển khoản thành công!!!");
                break;
        }
    }
}