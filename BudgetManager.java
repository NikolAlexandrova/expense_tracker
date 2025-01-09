import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private static BudgetManager instance;
    private double budget;
    private double totalExpenses;
    private List<ExpenseEntry> expenses; // List to hold expense entries
    private List<BudgetObserver> observers; // List to hold budget observers

    // Private constructor
    private BudgetManager() {
        expenses = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // Singleton instance retrieval
    public static BudgetManager getInstance() {
        if (instance == null) {
            instance = new BudgetManager();
        }
        return instance;
    }

    // Set the budget
    public void setBudget(double budget) {
        this.budget = budget;
        notifyObservers();
    }

    // Add an expense
    public void addExpense(double amount, String description, String currency, double convertedAmount) {
        totalExpenses += convertedAmount;
        expenses.add(new ExpenseEntry(description, amount, currency, convertedAmount));
        notifyObservers();
    }

    // Remove an expense
    public boolean removeExpense(String description, double convertedAmount) {
        for (ExpenseEntry expense : expenses) {
            if (expense.getDescription().equals(description) && expense.getConvertedAmount() == convertedAmount) {
                expenses.remove(expense);
                totalExpenses -= convertedAmount;
                notifyObservers();
                return true;
            }
        }
        return false;
    }

    // Get remaining budget
    public double getRemainingBudget() {
        return budget - totalExpenses;
    }

    // Get a copy of the expense list
    public List<ExpenseEntry> getExpenses() {
        return new ArrayList<>(expenses);
    }

    // Register a budget observer
    public void registerObserver(BudgetObserver observer) {
        observers.add(observer);
    }

    // Notify all observers
    private void notifyObservers() {
        for (BudgetObserver observer : observers) {
            observer.update(totalExpenses, budget);
        }
    }
}
