package business;
import business.presentation.AccountUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountUI.displayAccountMenu(scanner);
    }
}