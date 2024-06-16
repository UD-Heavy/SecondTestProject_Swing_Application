package ru.Desktop.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.util.Properties;

public class MongoDBConnection {
    private static volatile MongoDBConnection mongoDBConnection;
    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    private MongoDBConnection() throws IOException {
        Properties properties = new Properties();
        properties.load(MongoDBConnection.class.getResourceAsStream("/mongodb.properties"));

        String connectionString = properties.getProperty("connectionString");
        String dbName = properties.getProperty("dbName");
        String collectionName = properties.getProperty("collectionName");


        mongoClient = MongoClients.create(connectionString);
        database = mongoClient.getDatabase(dbName);
        collection = database.getCollection(collectionName);
    }

    public static synchronized MongoDBConnection getMongoDBConnection() throws IOException {
        if (mongoDBConnection == null) {
            mongoDBConnection = new MongoDBConnection();
        }
        return mongoDBConnection;
    }

    public MongoDatabase getDatabase(){
        return database;
    }

    public MongoCollection<Document> getCollection() {
        return collection;
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
