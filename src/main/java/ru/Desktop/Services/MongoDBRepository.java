package ru.Desktop.Services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ru.Desktop.utils.MongoDBConnection;

import java.io.IOException;

public class MongoDBRepository {
    private final MongoDBConnection connection;
    private final MongoCollection<Document> collection;

    public MongoDBRepository() throws IOException {
        connection = MongoDBConnection.getMongoDBConnection();
        collection = connection.getCollection();
    }

    public Document getDocument(Object id) {
        return collection.findOneAndDelete(Filters.eq("_id", id));
    }

    public void closConnection() {

        connection.closeConnection();
    }
}
