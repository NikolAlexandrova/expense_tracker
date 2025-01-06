public class RecurringExpense extends ExpenseDecorator {
    public RecurringExpense(Expense expense) {
        super(expense);
    }

    @Override
    public String getDetails() {
        return decoratedExpense.getDetails() + " (Recurring)";
    }
}
