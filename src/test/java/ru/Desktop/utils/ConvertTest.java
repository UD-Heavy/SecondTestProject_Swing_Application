package ru.Desktop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.Desktop.models.Document;
import ru.Desktop.models.Invoice;
import ru.Desktop.models.PaymentOrder;

import java.text.ParseException;
import java.util.Date;

class ConvertTest {

    @Test
    void testConvertDateToFormatString() {
        // Arrange
        Date date = new Date();

        // Act
        String dateString = Convert.convertDateToFormatString(date);

        // Assert
        Assertions.assertNotNull(dateString);
        Assertions.assertTrue(dateString.matches("\\d{2}\\.\\d{2}\\.\\d{4}"));
    }

    @Test
    void testConvertModelDocumentToBsonDocument() throws JsonProcessingException {
        // Arrange
        Invoice model = new Invoice(new ObjectId(), DOCUMENT_TYPE.INVOICE,
                "number", new Date(), "user", 12.0,
                "currency", 2.0, "product", 1);

        // Act
        org.bson.Document bsonDoc = Convert.convertModelDocumentToBsonDocument(model);

        // Assert
        Assertions.assertNotNull(bsonDoc);
        Assertions.assertEquals(model.get_id().toString(), bsonDoc.getString("_id"));
        Assertions.assertEquals(model.getDocumentType().toString(), bsonDoc.getString("documentType"));
        Assertions.assertEquals(model.getNumber(), bsonDoc.getString("number"));
        Assertions.assertEquals(model.getDate(), new Date(Long.parseLong(String.valueOf(bsonDoc.getLong("date")))));
        Assertions.assertEquals(model.getUser(), bsonDoc.getString("user"));
        Assertions.assertEquals(model.getAmount(), bsonDoc.getDouble("amount"), 0.001);
        Assertions.assertEquals(model.getCurrency(), bsonDoc.getString("currency"));
        Assertions.assertEquals(model.getCurrencyRate(), bsonDoc.getDouble("currencyRate"), 0.001);
        Assertions.assertEquals(model.getProduct(), bsonDoc.getString("product"));
        Assertions.assertEquals(model.getQuantity(), bsonDoc.getDouble("quantity"), 0.001);
    }

    @Test
    void testConvertModelDocumentToJson() throws JsonProcessingException, ParseException {
        // Arrange
        PaymentOrder model = new PaymentOrder(new ObjectId(), DOCUMENT_TYPE.PAYMENT_ORDER,
                "number", new Date(), "user", 12.0, "employee");

        // Act
        String json = Convert.convertModelDocumentToJson(model);

        // Assert
        Assertions.assertNotNull(json);
        JsonNode jsonNode = new ObjectMapper().readTree(json);
        Assertions.assertEquals(model.get_id().toString(), jsonNode.get("_id").asText());
        Assertions.assertEquals(model.getDocumentType().toString(), jsonNode.get("documentType").asText());
        Assertions.assertEquals(model.getNumber(), jsonNode.get("number").asText());
        Assertions.assertEquals(model.getDate().getTime(), jsonNode.get("date").asLong());
        Assertions.assertEquals(model.getUser(), jsonNode.get("user").asText());
        Assertions.assertEquals(model.getAmount(), jsonNode.get("amount").asDouble(), 0.001);
        Assertions.assertEquals(model.getEmployee(), jsonNode.get("employee").asText());
    }

}