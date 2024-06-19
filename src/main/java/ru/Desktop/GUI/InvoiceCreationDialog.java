package ru.Desktop.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toedter.calendar.JDateChooser;
import org.bson.types.ObjectId;
import static ru.Desktop.utils.DOCUMENT_TYPE.INVOICE;

import ru.Desktop.models.Document;
import ru.Desktop.models.Invoice;
import ru.Desktop.repositories.MongoDBRepository;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

import static ru.Desktop.repositories.MongoDBRepository.getMongoDBRepository;

public class InvoiceCreationDialog extends JDialog {

    private final MongoDBRepository mongoDBRepository;
    private JTextField numberField;
    private JDateChooser dateChooser;
    private JTextField userField;
    private JTextField amountField;
    private JTextField currencyField;
    private JTextField currencyRateField;
    private JTextField productField;
    private JTextField quantityField;

    private Document document;

    public InvoiceCreationDialog(MainWindow mainWindow) throws IOException {
        super(mainWindow, "Создание накладной", true);
        setSize(400, 300);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(mainWindow);

        mongoDBRepository = getMongoDBRepository();

        // Создание графических компонентов
        numberField = new JTextField();
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd.MM.yyyy");
        userField = new JTextField();
        amountField = new JTextField();
        currencyField = new JTextField();
        currencyRateField = new JTextField();
        productField = new JTextField();
        quantityField = new JTextField();

        // Добавление компонентов на панель
        JPanel contentPane = new JPanel(new GridLayout(8, 2, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPane.add(new JLabel("Номер:"));
        contentPane.add(numberField);
        contentPane.add(new JLabel("Дата:"));
        contentPane.add(dateChooser);
        contentPane.add(new JLabel("Пользователь:"));
        contentPane.add(userField);
        contentPane.add(new JLabel("Сумма:"));
        contentPane.add(amountField);
        contentPane.add(new JLabel("Валюта:"));
        contentPane.add(currencyField);
        contentPane.add(new JLabel("Курс валюты:"));
        contentPane.add(currencyRateField);
        contentPane.add(new JLabel("Товар:"));
        contentPane.add(productField);
        contentPane.add(new JLabel("Количество:"));
        contentPane.add(quantityField);


        // Добавление обработчиков событий для кнопок "Сохранить" и "Отмена"
        JButton saveButton = new JButton("Сохранить");
        saveButton.addActionListener(e -> {
            try {
                saveInvoice();
                dispose();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> dispose());

        // Добавление кнопок на панель
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        add(contentPane, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }



    private void saveInvoice() {
        String number = numberField.getText();
        if (number.isEmpty()) {
            throw new IllegalArgumentException("Пожалуйста, введите номер накладной.");
        }

        Date date = dateChooser.getDate();
        if (date == null) {
            throw new IllegalArgumentException("Пожалуйста, введите дату накладной.");
        }

        String user = userField.getText();
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Пожалуйста, введите пользователя.");
        }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Пожалуйста, введите корректную сумму.");
        }

        String currency = currencyField.getText();
        if (currency.isEmpty()) {
            throw new IllegalArgumentException("Пожалуйста, введите валюту.");
        }

        double currencyRate;
        try {
            currencyRate = Double.parseDouble(currencyRateField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Пожалуйста, введите корректный курс валюты.");
        }

        String product = productField.getText();
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Пожалуйста, введите товар.");
        }

        double quantity;
        try {
            quantity = Double.parseDouble(quantityField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Пожалуйста, введите корректное количество.");
        }

        document = new Invoice(new ObjectId(), INVOICE, number, date, user, amount, currency,
                currencyRate, product, quantity);
        try {
            mongoDBRepository.putDocument(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при сохранении Документа");
        }
    }

    public Document getDocument() {
        return document;
    }
}