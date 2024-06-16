package ru.Desktop.Services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import ru.Desktop.utils.MongoDBConnection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public void createDocument(){
        String svgContext = "test";

        Document document = new Document();
        document.append("_id", new ObjectId());
        document.append("map", svgContext);

        collection.insertOne(document);
    }

    public void closConnection() {

        connection.closeConnection();
    }
}
