package budget;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in);

        String input = "";

        BudgetInterface budget = new BudgetManager();

        do {
            // Print menu.
            System.out.println("Choose your action:\n" +
                    "1) Add income\n" +
                    "2) Add purchase\n" +
                    "3) Show list of purchases\n" +
                    "4) Balance\n" +
                    "5) Save\n" +
                    "6) Load\n" +
                    "7) Analyze (Sort)\n" +
                    "0) Exit");
            // Get input.
            input = sc.nextLine();

            // Spacing after input.
            System.out.println();

            // Controller.
            switch (input) {
                case "1": budget.addIncome();break;
                case "2": budget.addPurchase();break;
                case "3": budget.showPurchases();break;
                case "4": budget.showBalance();break;
                case "5": budget.save();break;
                case "6": budget.load();break;
                case "7": budget.analyze(); break;
                case "0":
                default: break;
            }

            // User can terminate through 0
        } while (!input.equals("0"));

        // User has exited the program.
        System.out.println("Bye!");
    }
}
