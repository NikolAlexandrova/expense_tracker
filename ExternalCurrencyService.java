// ExternalCurrencyService.java
public class ExternalCurrencyService {
    public double getExchangeRate(String currencyCode) {
        // Mock exchange rates
        switch (currencyCode.toUpperCase()) {
            case "USD":
                return 1.0; // Base currency
            case "EUR":
                return 1.1;
            case "GBP":
                return 1.25;
            default:
                return 1.0; // Default to base currency
        }
    }
}
