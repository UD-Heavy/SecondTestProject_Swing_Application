package ru.Desktop.utils;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldDescriptionPrinter { // класс для проебразования полей объекта в String

    // метод-билдер для вывода значений класса
    public static String getDescriptions(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        StringBuilder stringBuilder = new StringBuilder();

        // получаем описание полей родительского класса
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            stringBuilder.append(getFieldDescriptions(superClass, obj));
        }

        // получаем описание полей класса
        stringBuilder.append(getFieldDescriptions(clazz, obj));

        return new String(stringBuilder);
    }

    // получение значений класса
    private static String getFieldDescriptions(Class<?> clazz, Object obj) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();

        // проходи по всем полям, помеченным аннотациями
        for (Field field : clazz.getDeclaredFields()) {
            // получение аннотаций
            FieldDescription description = field.getAnnotation(FieldDescription.class);
            DateFieldDescription dateDescription = field.getAnnotation(DateFieldDescription.class);

            if (description != null) {
                // проверка доступности поля
                field.setAccessible(true);
                Object value = field.get(obj);
                // проверка есть ли аннотация для даты
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

