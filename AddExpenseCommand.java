public class AddExpenseCommand implements Command {
    private BudgetManager manager;
    private double amount;
    private String description;
    private String currency;
    private double convertedAmount;
    private String category; // Added category

    public AddExpenseCommand(BudgetManager manager, double amount, String description, String currency, double convertedAmount, String category) {
        this.manager = manager;
        this.amount = amount;
        this.description = description;
        this.currency = currency;
        this.convertedAmount = convertedAmount;
        this.category = category; // Initialize category
    }

    @Override
    public void execute() {
        manager.addExpense(amount, description, currency, convertedAmount, category); // Pass category
    }

    @Override
    public void undo() {
        boolean success = manager.removeExpense(description, convertedAmount);
        if (!success) {
            System.out.println("Undo failed: Expense not found.");
        }
    }
}