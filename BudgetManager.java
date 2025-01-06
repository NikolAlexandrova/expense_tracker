import java.util.ArrayList;
import java.util.List;

public class BudgetManager {
    private static BudgetManager instance;
    private double budget;
    private double totalExpenses;
    private List<Double> expenses;

    private BudgetManager() {
        expenses = new ArrayList<>();
    }

    public static BudgetManager getInstance() {
        if (instance == null) {
            instance = new BudgetManager();
        }
        return instance;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void addExpense(double amount) {
        totalExpenses += amount;
        expenses.add(amount);
    }

    public void removeExpense(double amount) {
        if (expenses.remove(amount)) { // Remove only the first occurrence
            totalExpenses -= amount;
        }
    }

    public double getRemainingBudget() {
        return budget - totalExpenses;
    }

    public List<Double> getExpenses() {
        return new ArrayList<>(expenses); // Return a copy of the list
    }
}