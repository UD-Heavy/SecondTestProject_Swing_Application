package ru.Desktop.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoWriteException;
import ru.Desktop.utils.DOCUMENT_TYPE;
import ru.Desktop.models.Document;
import ru.Desktop.repositories.MongoDBRepository;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;

import static ru.Desktop.utils.DOCUMENT_TYPE.getString;
import static ru.Desktop.utils.Convert.convertModelDocumentToJson;
import static ru.Desktop.utils.DocumentFactory.createDocument;
import static ru.Desktop.utils.FieldDescriptionPrinter.getDescriptions;


public class GUIEventHandler { // класс обработчиков событий
    private MainWindow mainWindow;
    private MongoDBRepository mongoDBRepository;

    public GUIEventHandler(MainWindow mainWindow) {
        try {
            this.mainWindow = mainWindow;
            mongoDBRepository = MongoDBRepository.getMongoDBRepository();
        } catch (IOException e) {
            String msg = "Произошла ошибка " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleCreateInvoiceButtonClick() {
        Document document;
        try {
            // создаем новый документ
            document = new InvoiceCreationDialog(mainWindow).getDocument();
            if (document != null) {
                // обновляем список на главном окне
                mainWindow.updateList(document);
            }
        } catch (IOException e) {
            String msg = "Произошла ошибка " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleCreatePaymentOrderButtonClick() {
        Document document;
        try {
            // создаем новый документ
            document = new PaymentOrderCreationDialog(mainWindow).getDocument();
            if (document != null) {
                // обновляем список на главном окне
                mainWindow.updateList(document);
            }
        } catch (IOException e) {
            String msg = "Произошла ошибка " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleCreatePaymentRequestButtonClick() {
        Document document;
        try {
            // создаем новый документ
            document = new PaymentRequestCreationDialog(mainWindow).getDocument();
            if (document != null) {
                // обновляем список на главном окне
                mainWindow.updateList(document);
            }
        } catch (IOException e) {
            String msg = "Произошла ошибка " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void handleSaveButtonClick() {
        // проверка, что выбран 1 документ
        Integer index = mainWindow.getElement();
        if (index == null) {
            JOptionPane.showMessageDialog(null, "Выберите один элемент для просмотра",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // открытие окна выбора директории
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();

            String fileName;
            boolean fileExists;
            do {
                // Запросить у пользователя имя файла
                fileName = JOptionPane.showInputDialog(null,
                        "Введите имя файла:", "Сохранение файла", JOptionPane.PLAIN_MESSAGE);
                if (fileName == null || fileName.trim().isEmpty()) {
                    // Пользователь не ввел имя файла или нажал "Отмена"
                    return;
                }

                // Добавить расширение .txt, если пользователь его не указал
                if (!fileName.endsWith(".txt")) {
                    fileName += ".txt";
                }

                // Проверить, существует ли файл с таким именем в выбранной директории
                File file = new File(selectedDirectory, fileName);
                fileExists = file.exists();
                if (fileExists) {
                    JOptionPane.showMessageDialog(null,
                            "Файл c таким именем уже существует в выбранной директории.",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } while (fileExists);

            File file = new File(selectedDirectory, fileName);
            try {
                // Сохранение документа в виде Json
                FileWriter writer = new FileWriter(file);
                String resultStr = convertModelDocumentToJson(mainWindow.getDocuments().get(index));

                writer.write(resultStr);
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при сохранении файла: " + e.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void handleLoadButtonClick() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Добавляем фильтр, чтобы показывать только файлы с расширением .txt
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Текстовые файлы (*.txt)",
                "txt");
        fileChooser.setFileFilter(txtFilter);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try {
                // Читаем содержимое файла
                StringBuilder stringBuilder = new StringBuilder();
                FileReader reader = new FileReader(selectedFile);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                reader.close();

                // Сохраняем содержимое файла в bson
                org.bson.Document document = org.bson.Document.parse(stringBuilder.toString());
                // определяем тип документа
                DOCUMENT_TYPE documentType = DOCUMENT_TYPE.valueOf(document.get("documentType").toString().toUpperCase());
                // создание документа
                ru.Desktop.models.Document myDocument = createDocument(documentType, document);
                // сохранение в бд и вывод на экран
                mongoDBRepository.putBson(document);
                mainWindow.updateList(myDocument);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Ошибка при загрузке файла: "
                                + e.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (MongoWriteException e) {
                JOptionPane.showMessageDialog(null, "Ошибка при загрузке файла: " +
                                "такой документ уже существует.",
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void handleDeleteButtonClick() {
        ArrayList<Document> documents;
        ArrayList<Integer> indexes = mainWindow.deleteElements();
        // проверка, что элемент выбран
        if (!indexes.isEmpty()) {
            documents = mainWindow.getDocuments();

            for (int index : indexes) {
                try {
                    // удаляем документы из бд
                    mongoDBRepository.deleteById(documents.get(index));
                    // удаляем документы с экрана
                    documents.remove(index);
                } catch (JsonProcessingException e) {
                    JOptionPane.showMessageDialog(null, "Произошла ошибка " + e.getMessage(),
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void handleViewButtonClick() {
        Integer index = mainWindow.getElement();
        // проверка, что элемент выбран
        if (index == null) {
            JOptionPane.showMessageDialog(null, "Выберите один элемент для просмотра",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            JOptionPane.showMessageDialog(null, getDescriptions(mainWindow.getDocuments().get(index)),
                    getString(mainWindow.getDocuments().get(index).getDocumentType()), JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(null, "Произошла ошибка " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleExitButtonClick() {
        int result = JOptionPane.showConfirmDialog(mainWindow,
                "Вы действительно хотите выйти?",
                "Подтверждение выхода",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_NO_OPTION) {
            mongoDBRepository.closeConnection();
            mainWindow.dispose();
            System.exit(0);
        }
    }
}
