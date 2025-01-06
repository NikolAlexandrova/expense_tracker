import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private static BudgetManager instance;
    private double budget;
    private double totalExpenses;
    private ArrayList<Double> expenses;
    private ArrayList<BudgetObserver> observers;

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
    }

    // Add an expense
    public void addExpense(double amount) {
        totalExpenses += amount;
        expenses.add(amount);
        notifyObservers();
    }

    // Get remaining budget
    public double getRemainingBudget() {
        return budget - totalExpenses;
    }

    // Get the list of expenses
    public List<Double> getExpenses() {
        return new ArrayList<>(expenses);
    }

    // Register an observer
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
