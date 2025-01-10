public class ExpenseEntry {
    private String description;
    private double originalAmount;
    private String currency;
    private double convertedAmount;
    private String category; // Added category field

    public ExpenseEntry(String description, double originalAmount, String currency, double convertedAmount, String category) {
        this.description = description;
        this.originalAmount = originalAmount;
        this.currency = currency;
        this.convertedAmount = convertedAmount;
        this.category = category; // Initialize category
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

    public String getCategory() {
        return category; // Getter for category
    }

    @Override
    public String toString() {
        return description + " (" + currency + " " + originalAmount + " -> USD $" + convertedAmount + ", Category: " + category + ")";
    }
}