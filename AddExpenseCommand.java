public class AddExpenseCommand implements Command {
    private BudgetManager manager;
    private double amount;

    public AddExpenseCommand(BudgetManager manager, double amount) {
        this.manager = manager;
        this.amount = amount;
    }

    @Override
    public void execute() {
        manager.addExpense(amount);
    }

    @Override
    public void undo() {
        manager.removeExpense(amount);
    }
}