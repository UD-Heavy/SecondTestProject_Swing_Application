package ru.Desktop.models;

import org.bson.types.ObjectId;
import ru.Desktop.utils.DOCUMENT_TYPE;
import ru.Desktop.utils.FieldDescription;

import java.text.ParseException;
import java.util.Date;

import static ru.Desktop.utils.Convert.convertDateToFormatString;

public class PaymentRequest extends Document {
    @FieldDescription("Контрагент")
    private String counterparty;
    @FieldDescription("Валюта")
    private String currency;
    @FieldDescription("Курс валюты")
    private double currencyRate;
    @FieldDescription("Комиссия")
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
        return "Заявка на оплату от " + convertDateToFormatString(getDate()) + " номер " + getNumber();
    }
}
