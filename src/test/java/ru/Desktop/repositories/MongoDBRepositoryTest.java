package ru.Desktop.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import ru.Desktop.models.Invoice;
import ru.Desktop.models.PaymentOrder;
import ru.Desktop.models.PaymentRequest;
import ru.Desktop.utils.DOCUMENT_TYPE;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;
import static ru.Desktop.utils.Convert.convertModelDocumentToBsonDocument;

public class MongoDBRepositoryTest {
    private MongoDBRepository mongoDBRepository;
    private MongoCollection<org.bson.Document> collection;

    @Before
    public void setUp() throws IOException {
        mongoDBRepository = MongoDBRepository.getMongoDBRepository();
        collection = mongoDBRepository.getCollection();
    }

    @Test
    public void testGetAllDocuments() throws ParseException, RuntimeException, JsonProcessingException {
        // Arrange
        Invoice invoice = new Invoice(new ObjectId(), DOCUMENT_TYPE.INVOICE,
                "number", new Date(), "user", 12.0,
                "currency", 2.0, "product", 1);
        PaymentOrder paymentOrder = new PaymentOrder(new ObjectId(), DOCUMENT_TYPE.PAYMENT_ORDER,
                "number", new Date(), "user", 12.0, "employee");
        PaymentRequest paymentRequest = new PaymentRequest(new ObjectId(), DOCUMENT_TYPE.PAYMENT_REQUEST,
                "number", new Date(), "user", 12.0, "counterParty",
                "currency", 2.0, 0);
        collection.insertOne(convertModelDocumentToBsonDocument(invoice));
        collection.insertOne(convertModelDocumentToBsonDocument(paymentOrder));
        collection.insertOne(convertModelDocumentToBsonDocument(paymentRequest));

        // Act
        ArrayList<ru.Desktop.models.Document> documents = mongoDBRepository.getAllDocuments();

        // Assert
        assertFalse(documents.isEmpty());
        boolean foundDocument = false;
        for (ru.Desktop.models.Document doc : documents) {
            if (doc.get_id().equals(invoice.get_id())) {
                foundDocument = true;
                break;
            }
        }
        foundDocument = false;
        for (ru.Desktop.models.Document doc : documents) {
            if (doc.get_id().equals(paymentOrder.get_id())) {
                foundDocument = true;
                break;
            }
        }
        foundDocument = false;
        for (ru.Desktop.models.Document doc : documents) {
            if (doc.get_id().equals(paymentRequest.get_id())) {
                foundDocument = true;
                break;
            }
        }
        assertTrue(foundDocument);
        collection.deleteOne(convertModelDocumentToBsonDocument(invoice));
        collection.deleteOne(convertModelDocumentToBsonDocument(paymentOrder));
        collection.deleteOne(convertModelDocumentToBsonDocument(paymentRequest));

    }


    @Test
    public void testDeleteById() throws JsonProcessingException, ParseException {

        Invoice invoice = new Invoice(new ObjectId(), DOCUMENT_TYPE.INVOICE,
                "number", new Date(), "user", 12.0,
                "currency", 2.0, "product", 1);
        PaymentOrder paymentOrder = new PaymentOrder(new ObjectId(), DOCUMENT_TYPE.PAYMENT_ORDER,
                "number", new Date(), "user", 12.0, "employee");
        PaymentRequest paymentRequest = new PaymentRequest(new ObjectId(), DOCUMENT_TYPE.PAYMENT_REQUEST,
                "number", new Date(), "user", 12.0, "counterParty",
                "currency", 2.0, 0);
        collection.insertOne(convertModelDocumentToBsonDocument(invoice));
        collection.insertOne(convertModelDocumentToBsonDocument(paymentOrder));
        collection.insertOne(convertModelDocumentToBsonDocument(paymentRequest));


        // Act

        collection.deleteOne(convertModelDocumentToBsonDocument(invoice));
        collection.deleteOne(convertModelDocumentToBsonDocument(paymentOrder));
        collection.deleteOne(convertModelDocumentToBsonDocument(paymentRequest));


        // Assert
        ArrayList<ru.Desktop.models.Document> documents = mongoDBRepository.getAllDocuments();
        assertFalse(documents.isEmpty());

        boolean foundDocument = false;
        for (ru.Desktop.models.Document doc : documents) {
            if (doc.get_id().equals(invoice.get_id())) {
                foundDocument = true;
                break;
            }
        }
        assertFalse(foundDocument);

        foundDocument = false;
        for (ru.Desktop.models.Document doc : documents) {
            if (doc.get_id().equals(paymentOrder.get_id())) {
                foundDocument = true;
                break;
            }
        }
        assertFalse(foundDocument);

        foundDocument = false;
        for (ru.Desktop.models.Document doc : documents) {
            if (doc.get_id().equals(paymentRequest.get_id())) {
                foundDocument = true;
                break;
            }
        }
        assertFalse(foundDocument);

    }


}
