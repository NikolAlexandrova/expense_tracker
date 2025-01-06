public class BudgetAlert implements BudgetObserver {
    @Override
    public void update(double totalExpenses, double budget) {
        if (totalExpenses > budget) {
            System.out.println("Warning: You have exceeded your budget!");
        } else if (totalExpenses > 0.9 * budget) {
            System.out.println("Alert: You are approaching your budget limit!");
        }
    }
}
