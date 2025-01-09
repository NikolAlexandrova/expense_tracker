// CurrencyAdapterImpl.java
public class CurrencyAdapterImpl implements CurrencyAdapter {
    private ExternalCurrencyService externalService;

    public CurrencyAdapterImpl(ExternalCurrencyService service) {
        this.externalService = service;
    }

    @Override
    public double convertToBaseCurrency(double amount, String currencyCode) {
        double rate = externalService.getExchangeRate(currencyCode);
        return amount * rate;
    }
}
