public class BasicExpense implements Expense {
    private String description;
    private double amount;

    public BasicExpense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String getDetails() {
        return description;
    }

    @Override
    public double getAmount() {
        return amount;
    }
}
