package ru.Desktop.repositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.Properties;

public class MongoDBConnection { // класс, устанавливающий соединение с mongodb
    private static volatile MongoDBConnection mongoDBConnection;
    private final MongoClient mongoClient;
    private final MongoCollection<Document> collection;

    private MongoDBConnection() throws IOException {
        // устанавливаем зависимости из файла
        Properties properties = new Properties();
        properties.load(MongoDBConnection.class.getResourceAsStream("/mongodb.properties"));

        String connectionString = properties.getProperty("connectionString");
        String dbName = properties.getProperty("dbName");
        String collectionName = properties.getProperty("collectionName");

        // создаем объекты для работы с бд
        mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(collectionName);
    }

    // паттерн singleton
    public static synchronized MongoDBConnection getMongoDBConnection() throws IOException {
        if (mongoDBConnection == null) {
            mongoDBConnection = new MongoDBConnection();
        }
        return mongoDBConnection;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
