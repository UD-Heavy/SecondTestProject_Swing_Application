package ru.Desktop.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Convert {
    // методов конвертирования

    // проебразование даты в строку по паттерну
    public static String convertDateToFormatString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    // преобразование документов трех типов в тип данных для хранения в mongodb
    public static Document convertModelDocumentToBsonDocument(ru.Desktop.models.Document doc) throws JsonProcessingException {
        return Document.parse(convertModelDocumentToJson(doc));
    }

    // преобразование документов трех типов в Json (String)
    public static String convertModelDocumentToJson(ru.Desktop.models.Document doc) throws JsonProcessingException {
        ObjectWriter ow = new CustomObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(doc);
    }
}
