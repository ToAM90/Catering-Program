package com.techelevator.view;

public class Menu {

public void levelOne(){
    System.out.println("(D) Display caTEring Items\n(P) Purchase\n(E) Exit");
}

public void levelOneSubD(VendingMachine vendingMachine){
    vendingMachine.displayItems();
}

public void levelOneSubP(){
    System.out.println("(M) Feed Money\n(S) Select Item\n(F) Finish Transaction");

}

public void subM(){
    System.out.println("(1) $1\n(2) $5\n(3) $10\n(4) $20\n(B) Back");
}

}
