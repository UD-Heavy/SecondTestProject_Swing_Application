package ru.Desktop.Models;

public class PaymentRequest extends Document {
    private String Counterparty;
    private String currency;
    private double currencyRate;
    private double commission;

    public String getCounterparty() {
        return Counterparty;
    }

    public void setCounterparty(String counterparty) {
        Counterparty = counterparty;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }
}
