package ru.Desktop.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toedter.calendar.JDateChooser;
import org.bson.types.ObjectId;
import ru.Desktop.models.PaymentRequest;
import ru.Desktop.repositories.MongoDBRepository;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

import static ru.Desktop.models.DOCUMENT_TYPE.PAYMENT_REQUEST;
import static ru.Desktop.repositories.MongoDBRepository.getMongoDBRepository;

public class PaymentRequestCreationDialog extends JDialog {

    private final MongoDBRepository mongoDBRepository;
    private JTextField numberField;
    private JDateChooser dateChooser;
    private JTextField userField;
    private JTextField counterpartyField;
    private JTextField amountField;
    private JTextField currencyField;
    private JTextField currencyRateField;
    private JTextField commissionField;

    public PaymentRequestCreationDialog(GUI gui) throws IOException {
        super(gui, "Создание накладной", true);
        setSize(400, 300);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(gui);

        mongoDBRepository = getMongoDBRepository();

        // Создание графических компонентов
        numberField = new JTextField();
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd.MM.yyyy");
        userField = new JTextField();
        counterpartyField = new JTextField();
        amountField = new JTextField();
        currencyField = new JTextField();
        currencyRateField = new JTextField();
        commissionField = new JTextField();

        // Добавление компонентов на панель
        JPanel contentPane = new JPanel(new GridLayout(8, 2, 10, 10));
        contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPane.add(new JLabel("Номер:"));
        contentPane.add(numberField);
        contentPane.add(new JLabel("Дата:"));
        contentPane.add(dateChooser);
        contentPane.add(new JLabel("Пользователь:"));
        contentPane.add(userField);
        contentPane.add(new JLabel("Контрагент:"));
        contentPane.add(counterpartyField);
        contentPane.add(new JLabel("Сумма:"));
        contentPane.add(amountField);
        contentPane.add(new JLabel("Валюта:"));
        contentPane.add(currencyField);
        contentPane.add(new JLabel("Курс валюты:"));
        contentPane.add(currencyRateField);
        contentPane.add(new JLabel("Комиссия:"));
        contentPane.add(commissionField);


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

        String counterparty = counterpartyField.getText();
        if (counterparty.isEmpty()) {
            throw new IllegalArgumentException("Пожалуйста, укажите контрагента.");
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

        double commission;
        try {
            commission = Double.parseDouble(commissionField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Пожалуйста, введите корректную комиссию.");
        }

        PaymentRequest paymentRequest = new PaymentRequest(new ObjectId(), PAYMENT_REQUEST,number, date, user, amount, counterparty, currency,currencyRate, commission);
        try {
            mongoDBRepository.putDocument(paymentRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка при сохранении Документа");
        }
    }
}