package ru.Desktop.models;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.Desktop.utils.DOCUMENT_TYPE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class PaymentRequestTest {
    private static final String COUNTERPARTY = "Acme Corp";
    private static final String CURRENCY = "USD";
    private static final double CURRENCY_RATE = 1.2;
    private static final double COMMISSION = 50.0;
    private static final String NUMBER = "PR-001";
    private static final String USER = "user@example.com";
    private static final double AMOUNT = 1000.0;
    private static final String DATE_STRING = "2023-05-01";

    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() throws ParseException {
        ObjectId id = new ObjectId();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(DATE_STRING);
        paymentRequest = new PaymentRequest(id, DOCUMENT_TYPE.PAYMENT_REQUEST, NUMBER, date, USER, AMOUNT, COUNTERPARTY, CURRENCY, CURRENCY_RATE, COMMISSION);
    }

    @Test
    void testGetCounterparty() {
        Assertions.assertEquals(COUNTERPARTY, paymentRequest.getCounterparty());
    }

    @Test
    void testSetCounterparty() {
        String newCounterparty = "Globex Inc";
        paymentRequest.setCounterparty(newCounterparty);
        Assertions.assertEquals(newCounterparty, paymentRequest.getCounterparty());
    }

    @Test
    void testGetCurrency() {
        Assertions.assertEquals(CURRENCY, paymentRequest.getCurrency());
    }

    @Test
    void testSetCurrency() {
        String newCurrency = "EUR";
        paymentRequest.setCurrency(newCurrency);
        Assertions.assertEquals(newCurrency, paymentRequest.getCurrency());
    }

    @Test
    void testGetCurrencyRate() {
        Assertions.assertEquals(CURRENCY_RATE, paymentRequest.getCurrencyRate());
    }

    @Test
    void testSetCurrencyRate() {
        double newCurrencyRate = 1.5;
        paymentRequest.setCurrencyRate(newCurrencyRate);
        Assertions.assertEquals(newCurrencyRate, paymentRequest.getCurrencyRate());
    }

    @Test
    void testGetCommission() {
        Assertions.assertEquals(COMMISSION, paymentRequest.getCommission());
    }

    @Test
    void testSetCommission() {
        double newCommission = 75.0;
        paymentRequest.setCommission(newCommission);
        Assertions.assertEquals(newCommission, paymentRequest.getCommission());
    }
}