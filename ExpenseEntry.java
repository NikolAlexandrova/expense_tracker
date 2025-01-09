public class ExpenseEntry {
    private String description;
    private double originalAmount;
    private String currency;
    private double convertedAmount;

    public ExpenseEntry(String description, double originalAmount, String currency, double convertedAmount) {
        this.description = description;
        this.originalAmount = originalAmount;
        this.currency = currency;
        this.convertedAmount = convertedAmount;
    }

    public String getDescription() {
        return description;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public double getConvertedAmount() {
        return convertedAmount;
    }

    @Override
    public String toString() {
        return description + " (" + currency + " " + originalAmount + " -> USD $" + convertedAmount + ")";
    }
}
