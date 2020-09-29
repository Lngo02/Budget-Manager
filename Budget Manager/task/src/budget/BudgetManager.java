package budget;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Scanner;


public class BudgetManager implements BudgetInterface{
    Scanner sc = new Scanner(System.in);

    static Double balance = 0.0;

    enum CATEGORIES {FOOD, CLOTHES, ENTERTAINMENT, OTHER, ALL}
    //List<String>[] purchaseList = new ArrayList[CATEGORIES.values().length];
    Purchases[] purchasesList = new Purchases[CATEGORIES.values().length];

    // Create file.
    File file = new File("purchases.txt");
    boolean isFileCreated = false;

    public BudgetManager() {
        // Initialize array lists.
        for (int i = 0; i < CATEGORIES.values().length; i++){
            // Use array list because dont have to add in middle or start.
            //purchaseList[i] = new ArrayList<String>();

            purchasesList[i] = new Purchases(i);
        }
    }

    // Add income to balance.
    public void addIncome(){
        Double income = 0.0;
        String input = "";

        // User enters value.
        System.out.println("Enter income: ");
        input = sc.nextLine();
        income = Double.parseDouble(input);

        // Add amount to balance.
        balance += income;
        System.out.println("Income was added!\n");
    }

    // Add purchase to list.
    public void addPurchase(){
        String purchase = "";
        String purchaseName = "";
        Double purchaseCost = 0.0;

        String purchaseType = "";
        int category = 0;

        do {
            // Choose type of category.
            System.out.println("Choose the type of purchase\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) Back");
            purchaseType = sc.nextLine();
            System.out.println();

            switch(purchaseType) {
                case "1": getPurchase(0); break;
                case "2": getPurchase(1); break;
                case "3": getPurchase(2); break;
                case "4": getPurchase(3); break;
            }

        } while (!purchaseType.equals("5"));
    }

    private void getPurchase(int category){
        String purchaseName = "";
        Double purchaseCost = 0.0;
        String purchase = "";

        // Get purchase name.
        System.out.println("Enter purchase name:");
        purchaseName = sc.nextLine();

        // Get purchase price.
        System.out.println("Enter its price:");
        purchaseCost = Double.parseDouble(sc.nextLine());

        storePurchaseInfo(category, purchaseName, purchaseCost);

        // Update balance.
        balance -= purchaseCost;

        // Complete.
        System.out.println("Purchase was added!\n");
    }

    public void showPurchases(){
        Double cost, total = 0.0;
        String item = "";

        String purchaseType = "";
        int category = 0;

        do {
            // Menu.
            System.out.println("Choose the type of purchases\n" +
                    "1) Food\n" +
                    "2) Clothes\n" +
                    "3) Entertainment\n" +
                    "4) Other\n" +
                    "5) All\n" +
                    "6) Back");
            // Get input.
            purchaseType = sc.nextLine();
            System.out.println();

            switch(purchaseType){
                case "1": printPurchaseList(0); break;
                case "2": printPurchaseList(1); break;
                case "3": printPurchaseList(2); break;
                case "4": printPurchaseList(3); break;
                case "5": printPurchaseList(4); break;
            }
        } while (!purchaseType.equals("6"));

    }

    public void showBalance(){
        balance = Math.max(0, balance);
        //System.out.println(balance);
        System.out.println(String.format("Balance: $%.2f\n", balance));
    }

    public void load(){
        int category = 0;
        String input = "";
        try {
            Scanner fsc = new Scanner(file);
            while (fsc.hasNextLine()){
                input = fsc.nextLine();
                switch (input){
                    case "Food:":
                        category = 0;
                        break;
                    case "Clothes:":
                        category = 1;
                        break;
                    case "Entertainment:":
                        category = 2;
                        break;
                    case "Other:":
                        category = 3;
                        break;
                    default:
                        // If its a balance line.
                        if (input.contains("Balance:")){
                            balance = Double.parseDouble(input.substring(input.lastIndexOf("$")+1));
                            break;
                        }
                        storePurchaseInfo(category, input);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Purchases were loaded!\n");
    }

    public void save(){

        if (!isFileCreated){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // Write to file.
            FileWriter write = new FileWriter("purchases.txt");

            String str = "";
            String item = "";

            for (int i = 0; i < 4; i++){
                str += purchasesList[i].toString();
            }

            str += String.format("Balance: $%.2f", balance);
            write.write(str);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Purchases were saved!\n");
    }

    // Analysis of purchases.
    public void analyze(){

        String sortCommand = "";

        do {
            System.out.println("How do you want to sort?\n" +
                    "1) Sort all purchases\n" +
                    "2) Sort by type\n" +
                    "3) Sort certain type\n" +
                    "4) Back");
            sortCommand = sc.nextLine();
            System.out.println();

            switch(sortCommand){
                case "1":
                    sortAllPurchases();
                    break;
                case "2":
                    sortByType();
                    break;
                case "3":
                    sortCertainType();
                    break;
            }
        } while (!sortCommand.equals("4"));
    }

    // PRIVATE FUNCTIONS.
    private void sortAllPurchases(){
        if (purchasesList[4].isEmpty()){
            System.out.println("Purchase list is empty!\n");
            return;
        }
        System.out.println(purchasesList[4].toStringSorted());
    }

    private void sortByType(){
        LinkedList<Purchases> copy = new LinkedList<>();
        for (int i = 0; i < 4; i++){
            copy.add(purchasesList[i]);
        }

        System.out.println("Types:");
        Collections.sort(copy, new Comparator<Purchases>() {
            @Override
            public int compare(Purchases purchases, Purchases t1) {
                return t1.getTotalSum().compareTo(purchases.getTotalSum());
            }
        });

        for (int i = 0; i < 4; i++){
            System.out.println(String.format("%s - $%.2f",
                    copy.get(i).getCategoryString(),
                    copy.get(i).getTotalSum()));
        }
        System.out.println(String.format("Total sum: $%.2f\n", purchasesList[4].getTotalSum()));
    }

    private void sortCertainType(){
        int sortType = -1;

        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other");
        sortType = Integer.parseInt(sc.nextLine()) - 1;
        System.out.println();

        if (purchasesList[sortType].isEmpty()){
            System.out.println("Purchase list is empty!\n");
            return;
        }
        System.out.println(purchasesList[sortType].toStringSorted());
    }

    // Store purchase information.
    private void storePurchaseInfo(int category, String name, double cost){
        String purchase = String.format("%s $%.2f", name, cost);
        //purchaseList[category].add(purchase);
        //purchaseList[4].add(purchase);

        Item item = new Item(name, cost);
        purchasesList[category].addPurchase(item);
        purchasesList[4].addPurchase(item);
    }

    private void storePurchaseInfo(int category, String purchase){
        String name = purchase.substring(0, purchase.lastIndexOf("$")-1).trim();
        Double cost = Double.parseDouble(purchase.substring(purchase.lastIndexOf("$")+1));

        //purchaseList[category].add(purchase);
        //purchaseList[4].add(purchase);

        Item item = new Item(name, cost);
        purchasesList[category].addPurchase(item);
        purchasesList[4].addPurchase(item);
    }

    private void storePurchaseInfo(int category, Item item){
        purchasesList[category].addPurchase(item);
        purchasesList[4].addPurchase(item);
    }

    private void printPurchaseList(int category){
        String item = "";
        Double cost, total = 0.0;

        if (purchasesList[category].isEmpty()){
            System.out.println("Purchase list is empty\n");
            return;
        }

        String str = purchasesList[category].toString();
        System.out.println(str.substring(0, str.length()-1));
        System.out.println(String.format("Total sum: $%.2f\n", purchasesList[category].getTotalSum()));
    }
}
