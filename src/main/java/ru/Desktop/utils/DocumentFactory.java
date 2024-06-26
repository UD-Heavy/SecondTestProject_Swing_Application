package ru.Desktop.utils;

import ru.Desktop.models.*;

import java.text.ParseException;

public abstract class DocumentFactory { // класс, для реализации паттерна factory
    public static Document createDocument(DOCUMENT_TYPE type, org.bson.Document bson) throws ParseException {
        Document document;

        // выбор типа документа
        switch (type) {
            case INVOICE -> document = new Invoice(bson);
            case PAYMENT_ORDER -> document = new PaymentOrder(bson);
            case PAYMENT_REQUEST -> document = new PaymentRequest(bson);
            default -> throw new IllegalArgumentException("Неподдерживаемый тип документа: " + type);
        }

        return document;
    }
}
