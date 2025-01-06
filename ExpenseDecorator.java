public abstract class ExpenseDecorator implements Expense {
    protected Expense decoratedExpense;

    public ExpenseDecorator(Expense expense) {
        this.decoratedExpense = expense;
    }

    @Override
    public String getDetails() {
        return decoratedExpense.getDetails();
    }

    @Override
    public double getAmount() {
        return decoratedExpense.getAmount();
    }
}
