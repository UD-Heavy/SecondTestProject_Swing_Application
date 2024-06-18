package ru.Desktop.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import ru.Desktop.models.DOCUMENT_TYPE;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static ru.Desktop.utils.Convert.convertModelDocumentToBsonDocument;
import static ru.Desktop.utils.DocumentFactory.createDocument;

public class MongoDBRepository {

    private static volatile MongoDBRepository mongoDBRepository;
    private final MongoDBConnection connection;
    private final MongoCollection<Document> collection;

    public MongoDBRepository() throws IOException {
        connection = MongoDBConnection.getMongoDBConnection();
        collection = connection.getCollection();
    }

    public static synchronized MongoDBRepository getMongoDBRepository() throws IOException {
        if(mongoDBRepository == null)
            mongoDBRepository = new MongoDBRepository();
        return mongoDBRepository;
    }

    public Document getDocument(Object id) {
        return collection.findOneAndDelete(Filters.eq("_id", id));
    }

    public ArrayList<ru.Desktop.models.Document> getAllDocuments() throws ParseException, RuntimeException {
        ArrayList<ru.Desktop.models.Document> documents = new ArrayList<>();
        DOCUMENT_TYPE documentType = null;
        for (Document doc : collection.find()) {
            documentType = DOCUMENT_TYPE.valueOf(doc.get("documentType").toString().toUpperCase());
            ru.Desktop.models.Document document = createDocument(documentType, doc);
            documents.add(document);
        }
        return documents;
    }

    public void putDocument(ru.Desktop.models.Document doc) throws JsonProcessingException {
        collection.insertOne(convertModelDocumentToBsonDocument(doc));
    }

    public void closeConnection() {
        connection.closeConnection();
    }
}
