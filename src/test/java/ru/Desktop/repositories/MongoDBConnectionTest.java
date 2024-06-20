package ru.Desktop.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class MongoDBConnectionTest {
    private MongoDBConnection mongoDBConnection;

    @BeforeEach
    void setUp() throws IOException {
        mongoDBConnection = MongoDBConnection.getMongoDBConnection();
    }

    @AfterEach
    void tearDown() {
        mongoDBConnection.closeConnection();
    }

    @Test
    void testGetMongoDBConnection() throws IOException {
        // Проверяем, что мы получаем один и тот же экземпляр
        MongoDBConnection sameConnection = MongoDBConnection.getMongoDBConnection();
        Assertions.assertSame(mongoDBConnection, sameConnection);
    }

    @Test
    void testGetCollection() {
        // Получаем коллекцию и проверяем, что она не null
        MongoCollection<Document> collection = mongoDBConnection.getCollection();
        Assertions.assertNotNull(collection);
    }
}
