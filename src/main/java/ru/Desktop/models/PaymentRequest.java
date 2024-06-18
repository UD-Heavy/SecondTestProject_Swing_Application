package ru.Desktop.models;

import org.bson.types.ObjectId;

import java.text.ParseException;
import java.util.Date;

public class PaymentRequest extends Document {
    private String counterparty;
    private String currency;
    private double currencyRate;
    private double commission;

    public PaymentRequest(org.bson.Document document) throws ParseException {
        super(document);
        this.counterparty = document.get("counterparty").toString();
        this.currency = document.get("currency").toString();
        this.currencyRate = Double.parseDouble(document.get("currencyRate").toString());
        this.commission = Double.parseDouble(document.get("commission").toString());
    }

    public PaymentRequest(ObjectId id, DOCUMENT_TYPE documentType, String number, Date date,
                          String user, double amount, String counterparty,
                          String currency, double currencyRate, double commission) {
        super(id, documentType, number, date, user, amount);
        this.counterparty = counterparty;
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.commission = commission;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
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

    @Override
    public String toString() {
        return "PaymentRequest{" + '\'' +
                " Counterparty='" + counterparty + '\'' +
                ", currency='" + currency + '\'' +
                ", currencyRate=" + currencyRate +
                ", commission=" + commission +
                '}';
    }
}
