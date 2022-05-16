package com.techelevator.view;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
    private String currentTimeFormatted = "";

    public void printToAuditLog(String message) {
        File newFile = new File("Audit.txt");
        PrintWriter writer = null;
        LocalDateTime currentTime = LocalDateTime.now();
        currentTimeFormatted = currentTime.format(formatter);
        try {
            if (newFile.exists()) {
                writer = new PrintWriter(new FileOutputStream(newFile.getAbsoluteFile(), true));
            }
            else {
                writer = new PrintWriter(newFile.getAbsoluteFile());
            }
            writer.append(currentTimeFormatted + message);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file.");
        }
    }

    public void auditMoneyDeposit(BigDecimal amountToDeposit, BigDecimal currentBalance) {
        // local date time, money deposited, total balance
        String moneyDeposited = " MONEY FED: $" + amountToDeposit + " $" + currentBalance + "\n";
        printToAuditLog(moneyDeposited);
    }

    public void auditItemPurchased(String food, String slot, BigDecimal oldBalance, BigDecimal newBalance) {
        String itemPurchased = " " + food + " " + slot + " $" + oldBalance + " $" + newBalance + "\n";
        printToAuditLog(itemPurchased);
    }

    public void auditChangeGiven(BigDecimal change, BigDecimal newBalance) {
        String changeGiven = " CHANGE GIVEN: $" + change + " $" + newBalance + "\n";
        printToAuditLog(changeGiven);
    }

}


