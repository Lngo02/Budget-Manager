package budget;

public interface BudgetInterface {
    static Double balance = 0.0;

    public void addIncome();
    public void addPurchase();
    public void showPurchases();
    public void showBalance();
    public void save();
    public void load();
    public void analyze();
}
