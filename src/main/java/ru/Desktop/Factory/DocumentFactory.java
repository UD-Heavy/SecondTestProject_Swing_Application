package ru.Desktop.Factory;

import ru.Desktop.Models.Document;
import ru.Desktop.Models.DocumentType;

import java.util.Date;

public abstract class DocumentFactory {
    public static Document createDocument(DocumentType type, String number, Date date, String user, double amount) {
        Document document = null;

        switch (type) {
            case INVOICE -> {
                document = new InvoiceFactory().createDocument(number, date, user, amount);
            }
            case PAYMENT_ORDER -> {
                document = new PaymentOrderFactory().createDocument(number, date, user, amount);
            }
            case PAYMENT_REQUEST -> {
                document = new PaymentRequestFactory().createDocument(number, date, user, amount);

            }
            default -> throw new IllegalArgumentException("Unsupported document type: " + type);
        }

        return document;
    }

}
