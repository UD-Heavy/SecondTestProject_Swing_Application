package ru.Desktop.models;

import org.bson.types.ObjectId;
import ru.Desktop.utils.DOCUMENT_TYPE;
import ru.Desktop.utils.FieldDescription;

import java.text.ParseException;
import java.util.Date;

import static ru.Desktop.utils.Convert.convertDateToFormatString;

public class Invoice extends Document {

    @FieldDescription("Валюта")
    private String currency;
    @FieldDescription("Курс валюты")
    private double currencyRate;
    @FieldDescription("Товар")
    private String product;
    @FieldDescription("Количество")
    private double quantity;

    public Invoice(org.bson.Document document) throws ParseException {
        super(document);
        this.currency = document.get("currency").toString();
        this.currencyRate = Double.parseDouble(document.get("currencyRate").toString());
        this.product = document.get("product").toString();
        this.quantity = Double.parseDouble(document.get("quantity").toString());
    }

    public Invoice(ObjectId id, DOCUMENT_TYPE documentType, String number, Date date,
                   String user, double amount, String currency,
                   double currencyRate, String product, double quantity) {
        super(id, documentType, number, date, user, amount);
        this.currency = currency;
        this.currencyRate = currencyRate;
        this.product = product;
        this.quantity = quantity;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Накладная от " + convertDateToFormatString(getDate()) + " номер " + getNumber();
    }
}