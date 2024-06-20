package ru.Desktop.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.bson.types.ObjectId;
import ru.Desktop.models.Invoice;
import ru.Desktop.models.PaymentOrder;
import ru.Desktop.models.PaymentRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

class FieldDescriptionPrinterTest {

    @Test
    void testInvoice() throws IllegalAccessException {
        Invoice model = new Invoice(new ObjectId(), DOCUMENT_TYPE.INVOICE,
                "number", new Date(), "user", 12.0,
                "currency", 2.0, "product", 1);

        String description = FieldDescriptionPrinter.getDescriptions(model);
        System.out.println(description);

        Assertions.assertTrue(description.contains("Номер: number"));
        Assertions.assertTrue(description.contains("Дата: " + new SimpleDateFormat("dd.MM.yyyy").format(model.getDate())));
        Assertions.assertTrue(description.contains("Пользователь: user"));
        Assertions.assertTrue(description.contains("Сумма: 12.0"));
        Assertions.assertTrue(description.contains("Валюта: currency"));
        Assertions.assertTrue(description.contains("Курс валюты: 2.0"));
        Assertions.assertTrue(description.contains("Товар: product"));
        Assertions.assertTrue(description.contains("Количество: 1"));
    }

    @Test
    void testPaymentOrder() throws IllegalAccessException {
        PaymentOrder model = new PaymentOrder(new ObjectId(), DOCUMENT_TYPE.PAYMENT_ORDER,
                "number", new Date(), "user", 12.0, "employee");

        String description = FieldDescriptionPrinter.getDescriptions(model);
        System.out.println(description);

        Assertions.assertTrue(description.contains("Номер: number"));
        Assertions.assertTrue(description.contains("Дата: " + new SimpleDateFormat("dd.MM.yyyy").format(model.getDate())));
        Assertions.assertTrue(description.contains("Пользователь: user"));
        Assertions.assertTrue(description.contains("Сумма: 12.0"));
        Assertions.assertTrue(description.contains("Сотрудник: employee"));
    }

    @Test
    void testPaymentRequest() throws IllegalAccessException {
        PaymentRequest model = new PaymentRequest(new ObjectId(), DOCUMENT_TYPE.PAYMENT_REQUEST,
                "number", new Date(), "user", 12.0, "counterParty",
                "currency", 2.0, 0);

        String description = FieldDescriptionPrinter.getDescriptions(model);
        System.out.println(description);

        Assertions.assertTrue(description.contains("Номер: number"));
        Assertions.assertTrue(description.contains("Дата: " + new SimpleDateFormat("dd.MM.yyyy").format(model.getDate())));
        Assertions.assertTrue(description.contains("Пользователь: user"));
        Assertions.assertTrue(description.contains("Сумма: 12.0"));
        Assertions.assertTrue(description.contains("Контрагент: counterParty"));
        Assertions.assertTrue(description.contains("Валюта: currency"));
        Assertions.assertTrue(description.contains("Курс валюты: 2.0"));
        Assertions.assertTrue(description.contains("Комиссия: 0"));
    }
}
