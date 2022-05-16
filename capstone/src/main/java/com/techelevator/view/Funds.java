package com.techelevator.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Funds {

    private BigDecimal balance = new BigDecimal("0.00");
    private AuditLog auditLog = new AuditLog();

    int nickels = 5;
    int dimes = 10;
    int quarter = 25;
    int dollar = 100;

    public BigDecimal getBalance() {
        return balance;
    }

    public void deposit(BigDecimal amountToDeposit) {
        balance = balance.add(amountToDeposit);
        auditLog.auditMoneyDeposit(amountToDeposit, this.getBalance());
    }

    public void withdrawal(BigDecimal amountToWithdrawal) {
        BigDecimal oldBalance = balance;
        balance = balance.subtract(amountToWithdrawal);
    }

    public String refund() {
        int dollarAmt = 0;
        int quarterAmt = 0;
        int dimesAmt = 0;
        int nickelAmt = 0;
        int cents;

        BigDecimal balanceInCents = this.balance.multiply(new BigDecimal("100.00"));

        int intBalance = balanceInCents.intValue();
        dollarAmt = intBalance / dollar;
        cents = intBalance % dollar;
        quarterAmt = cents / quarter;
        cents = cents % quarter;
        dimesAmt = cents/ dimes;
        cents = cents % dimes;
        nickelAmt = cents / nickels;

        // remaining change is dispensed and Audited
        BigDecimal change = getBalance();
        withdrawal(getBalance());
        LocalDateTime currentTime = LocalDateTime.now();
        auditLog.auditChangeGiven(change, getBalance());

        return "Your change is: " + dollarAmt + " Dollars " + quarterAmt + " Quarters " + dimesAmt + " Dimes " + nickelAmt + " Nickels\n";

    }

}
