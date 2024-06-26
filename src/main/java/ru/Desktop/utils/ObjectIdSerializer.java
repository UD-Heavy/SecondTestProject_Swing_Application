package ru.Desktop.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.bson.types.ObjectId;

import java.io.IOException;

public class ObjectIdSerializer extends JsonSerializer<ObjectId> { // переопределение метода сериализации
    @Override
    public void serialize(ObjectId value, JsonGenerator jsonGen, SerializerProvider provider) throws IOException {
        jsonGen.writeString(value.toString());
    }
}