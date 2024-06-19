package ru.Desktop.utils;

import java.util.HashMap;
import java.util.Map;

public enum DOCUMENT_TYPE {
    INVOICE("Накладная"),
    PAYMENT_ORDER("Платёжка"),
    PAYMENT_REQUEST("Заявка на оплату");

    private final String docName;

    private static final Map<DOCUMENT_TYPE, String> stringValuesMap = new HashMap<>();

    static {
        for (DOCUMENT_TYPE documentType : values()) {
            stringValuesMap.put(documentType, documentType.docName);
        }
    }

    DOCUMENT_TYPE(final String docName) {
        this.docName = docName;
    }

    public static String getString(DOCUMENT_TYPE documentType) {
        return stringValuesMap.get(documentType);
    }
}
