package ru.Desktop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {

    public static String convertDateToFormatString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static Document convertModelDocumentToBsonDocument(ru.Desktop.models.Document doc) throws JsonProcessingException {
        return Document.parse(convertModelDocumentToJson(doc));
    }

    public static String convertModelDocumentToJson(ru.Desktop.models.Document doc) throws JsonProcessingException {
        ObjectWriter ow = new CustomObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(doc);
    }
}
