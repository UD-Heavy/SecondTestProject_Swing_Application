package ru.Desktop.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import ru.Desktop.utils.DOCUMENT_TYPE;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import static ru.Desktop.utils.Convert.convertModelDocumentToBsonDocument;
import static ru.Desktop.utils.DocumentFactory.createDocument;

public class MongoDBRepository { // класс с методами для общения с бд

    private static volatile MongoDBRepository mongoDBRepository;
    private final MongoDBConnection connection;
    private final MongoCollection<Document> collection;

    public MongoDBRepository() throws IOException {
        connection = MongoDBConnection.getMongoDBConnection();
        collection = connection.getCollection();
    }

    // паттерн singleton
    public static synchronized MongoDBRepository getMongoDBRepository() throws IOException {
        if (mongoDBRepository == null)
            mongoDBRepository = new MongoDBRepository();
        return mongoDBRepository;
    }

    // получить все документы из бд
    public ArrayList<ru.Desktop.models.Document> getAllDocuments() throws ParseException, RuntimeException {
        ArrayList<ru.Desktop.models.Document> documents = new ArrayList<>();
        DOCUMENT_TYPE documentType;
        for (Document doc : collection.find()) {
            // получаем тип документа
            documentType = DOCUMENT_TYPE.valueOf(doc.get("documentType").toString().toUpperCase());
            // создаем нужный документ
            ru.Desktop.models.Document document = createDocument(documentType, doc);
            documents.add(document);
        }
        return documents;
    }

    // добавить bson в бд
    public void putBson(Document doc) throws MongoWriteException {
        collection.insertOne(doc);
    }

    // добавить документ в бд
    public void putDocument(ru.Desktop.models.Document doc) throws JsonProcessingException {
        collection.insertOne(convertModelDocumentToBsonDocument(doc));
    }

    // удалить документ из бд
    public void deleteById(ru.Desktop.models.Document doc) throws JsonProcessingException {
        collection.deleteOne(convertModelDocumentToBsonDocument(doc));
    }

    // закрыть соединение
    public void closeConnection() {
        connection.closeConnection();
    }


}
