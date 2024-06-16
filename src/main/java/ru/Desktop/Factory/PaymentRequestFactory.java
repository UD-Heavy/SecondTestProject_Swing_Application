package ru.Desktop.Factory;

import ru.Desktop.Factory.DocumentFactory;
import ru.Desktop.Models.Document;

import java.util.Date;

public class PaymentRequestFactory extends DocumentFactory {
    public Document createDocument(String number, Date date, String user, double amount) {
        return null;
    }
}
