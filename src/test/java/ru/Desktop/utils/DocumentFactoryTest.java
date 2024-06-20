package ru.Desktop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.Desktop.models.Invoice;
import ru.Desktop.models.PaymentOrder;
import ru.Desktop.models.PaymentRequest;

import java.text.ParseException;
import java.util.Date;

class DocumentFactoryTest {
    @Test
    void testCreateInvoice() throws ParseException, JsonProcessingException {
        Invoice model = new Invoice(new ObjectId(), DOCUMENT_TYPE.INVOICE,
                "number", new Date(), "user", 12.0,
                "currency", 2.0, "product", 1);
        Document bsonDo = Convert.convertModelDocumentToBsonDocument(model);
        ru.Desktop.models.Document document = DocumentFactory.createDocument(DOCUMENT_TYPE.INVOICE, bsonDo);
        Assertions.assertTrue(document instanceof Invoice);
    }

    @Test
    void testCreatePaymentOrder() throws ParseException, JsonProcessingException {
        PaymentOrder model = new PaymentOrder(new ObjectId(), DOCUMENT_TYPE.PAYMENT_ORDER,
                "number", new Date(), "user", 12.0, "employee");
        Document bsonDo = Convert.convertModelDocumentToBsonDocument(model);
        ru.Desktop.models.Document document = DocumentFactory.createDocument(DOCUMENT_TYPE.PAYMENT_ORDER, bsonDo);
        Assertions.assertTrue(document instanceof PaymentOrder);
    }

    @Test
    void testCreatePaymentRequest() throws ParseException, JsonProcessingException {
        PaymentRequest model = new PaymentRequest(new ObjectId(), DOCUMENT_TYPE.PAYMENT_REQUEST,
                "number", new Date(), "user", 12.0, "counterParty",
                "currency", 2.0, 0);
        Document bsonDo = Convert.convertModelDocumentToBsonDocument(model);

        ru.Desktop.models.Document document = DocumentFactory.createDocument(DOCUMENT_TYPE.PAYMENT_REQUEST, bsonDo);
        Assertions.assertTrue(document instanceof PaymentRequest);
    }
}