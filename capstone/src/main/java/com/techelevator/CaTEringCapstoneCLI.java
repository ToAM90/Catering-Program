package com.techelevator;

import com.techelevator.view.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class CaTEringCapstoneCLI {

    private Menu menu;
    private Scanner inputScanner;
    private VendingMachine vendingMachine;
    private Funds balance;
    private AuditLog auditLog;

    public CaTEringCapstoneCLI(Menu menu) {
        this.menu = menu;
        this.inputScanner = new Scanner(System.in);
        this.balance = new Funds();
        this.vendingMachine = new VendingMachine();
        this.auditLog = new AuditLog();
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        CaTEringCapstoneCLI cli = new CaTEringCapstoneCLI(menu);
        cli.run();
    }

    public void run() {
        boolean keepGoing = true;

        do {
            //  main menu
            this.menu.levelOne();
            String level1Input = inputScanner.nextLine().toUpperCase();

            if (level1Input.equals("D")) {
                // display items
                this.menu.levelOneSubD(this.vendingMachine);
                System.out.println("");
            } else if (level1Input.equals("P")) {
                // make a purchase
                subP();
            } else if (level1Input.equals("E")) {
                // exit
                keepGoing = false;
            }else {
                System.out.println("\nInvalid selection. Try again.\n");
            }
        } while (keepGoing);
    }

    public void subP() {
        // purchase menu
        boolean keepGoing = true;
        System.out.println("\nCurrent Money Provided: $" + balance.getBalance() + "\n");
        do {
            this.menu.levelOneSubP();
            String levelPInput = inputScanner.nextLine().toUpperCase();


            if (levelPInput.equals("M")) {
                subM();
            } else if (levelPInput.equals("S")) {
                subS();
            } else if (levelPInput.equals("F")) {
                // exit
                subF();
                keepGoing = false;
            }else {
                System.out.println("\nInvalid selection. Try again.\n");
            }


        } while (keepGoing);
    }

    public void subM() {
        // add money
        boolean keepGoing = true;
        System.out.println("\nCurrent Money Provided: $" + balance.getBalance() + "\n");
        do {
            this.menu.subM();
            String levelMInput = inputScanner.nextLine().toUpperCase();

            if (levelMInput.equals("1")) {
                // Add $1
                balance.deposit(new BigDecimal("1.00"));
            } else if (levelMInput.equals("2")) {
                // Add $5
                balance.deposit(new BigDecimal("5.00"));
            } else if (levelMInput.equals("3")) {
                // Add $10
                balance.deposit(new BigDecimal("10.00"));
            } else if (levelMInput.equals("4")) {
                // Add $20
                balance.deposit(new BigDecimal("20.00"));
            } else if (levelMInput.equals("B")) {
                keepGoing = false;
            } else {
                System.out.println("\nInvalid selection. Try again.\n");
            }
            System.out.println("\nCurrent Money Provided: $" + balance.getBalance() + "\n");

        } while (keepGoing);


    }

    public void subS() {
        // select food
        boolean keepGoing = true;
        this.menu.levelOneSubD(this.vendingMachine);
        System.out.println("\nCurrent Money Provided: $" + balance.getBalance() + "\n");
        System.out.println("\nPress (B) to go back.");

        do {
            String levelSInput = inputScanner.nextLine().toUpperCase();
            Food item = vendingMachine.getMenu().get(levelSInput);

            if (levelSInput.equals("B")){
                keepGoing = false;
            } else if (!vendingMachine.getMenu().containsKey(levelSInput)){
                System.out.println("\nInvalid selection. Returning to Purchase menu...\n");
                keepGoing = false;
            } else if (item.getStock() == 0){
                System.out.println("\n" + item + " NO LONGER AVAILABLE\n");
                keepGoing = false;
            } else if (balance.getBalance().compareTo(item.getPrice()) == -1) {
                System.out.println("\nNot enough funds.\n");
                keepGoing = false;
            }else if (item.getStock() > 0 && balance.getBalance().compareTo(item.getPrice()) >= 0){
                // dispensing item
                vendingMachine.dispenseItem(item);
                BigDecimal oldBalance = balance.getBalance();
                balance.withdrawal(item.getPrice());

                // sending info to Audit file
                auditLog.auditItemPurchased(item.getName(), item.getSlot(), oldBalance, balance.getBalance());

                // printing message
                System.out.println("\nSelected: " + item.getName() + " $" + item.getPrice() + " Remaining Balance: $" + balance.getBalance());
                System.out.println(item.message());
            } else {
                keepGoing = false;
            }

        } while (keepGoing);
    }

    public void subF() {
        // finish transaction
        System.out.println(balance.refund());

    }


}
