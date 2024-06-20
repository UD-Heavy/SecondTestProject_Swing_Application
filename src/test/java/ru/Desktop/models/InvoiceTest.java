package ru.Desktop.models;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.Desktop.utils.DOCUMENT_TYPE;

import java.util.Date;

class InvoiceTest {

    @Test
    void testInvoiceCreation() {
        ObjectId id = new ObjectId();
        DOCUMENT_TYPE documentType = DOCUMENT_TYPE.INVOICE;
        String number = "INV-001";
        Date date = new Date();
        String user = "John Doe";
        double amount = 1000.0;
        String currency = "USD";
        double currencyRate = 1.2;
        String product = "Product A";
        double quantity = 10.0;

        Invoice invoice = new Invoice(id, documentType, number, date, user, amount, currency, currencyRate, product, quantity);

        Assertions.assertEquals(id, invoice.get_id());
        Assertions.assertEquals(documentType, invoice.getDocumentType());
        Assertions.assertEquals(number, invoice.getNumber());
        Assertions.assertEquals(date, invoice.getDate());
        Assertions.assertEquals(user, invoice.getUser());
        Assertions.assertEquals(amount, invoice.getAmount());
        Assertions.assertEquals(currency, invoice.getCurrency());
        Assertions.assertEquals(currencyRate, invoice.getCurrencyRate());
        Assertions.assertEquals(product, invoice.getProduct());
        Assertions.assertEquals(quantity, invoice.getQuantity());
    }

    @Test
    void testInvoiceToString() {
        ObjectId id = new ObjectId();
        DOCUMENT_TYPE documentType = DOCUMENT_TYPE.INVOICE;
        String number = "INV-003";
        Date date = new Date();
        String user = "John Doe";
        double amount = 1000.0;
        String currency = "USD";
        double currencyRate = 1.2;
        String product = "Product A";
        double quantity = 10.0;
        Invoice invoice = new Invoice(id, documentType, number, date, user, amount, currency, currencyRate, product, quantity);
    }
}
