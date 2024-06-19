package ru.Desktop.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldDescriptionPrinter {
    public static String getDescriptions(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        StringBuilder stringBuilder = new StringBuilder();

        // Печатаем описание полей родительского класса
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            stringBuilder.append(getFieldDescriptions(superClass, obj));
        }

        // Печатаем описание полей текущего класса
        stringBuilder.append(getFieldDescriptions(clazz, obj));

        return new String(stringBuilder);
    }

    private static String getFieldDescriptions(Class<?> clazz, Object obj) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : clazz.getDeclaredFields()) {
            FieldDescription description = field.getAnnotation(FieldDescription.class);
            DateFieldDescription dateDescription = field.getAnnotation(DateFieldDescription.class);
            if (description != null) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (dateDescription != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    value = sdf.format((Date) value);
                }
                stringBuilder.append(description.value()).append(": ").append(value).append("\n");
            }
        }
        return new String(stringBuilder);
    }
}

