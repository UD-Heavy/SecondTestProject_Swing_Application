package ru.Desktop.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.toedter.calendar.JDateChooser;
import org.bson.types.ObjectId;
import ru.Desktop.models.Document;
import ru.Desktop.models.PaymentOrder;
import ru.Desktop.repositories.MongoDBRepository;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Date;

import static ru.Desktop.utils.DOCUMENT_TYPE.PAYMENT_ORDER;
import static ru.Desktop.repositories.MongoDBRepository.getMongoDBRepository;

public class PaymentOrderCreationDialog extends JDialog {

    private final MongoDBRepository mongoDBRepository;
    private JTextField numberField;
    private JDateChooser dateChooser;
    private JTextField userField;
    private JTextField amountField;
    private JTextField employeeField;
    private Document document;

    public PaymentOrderCreationDialog(MainWindow mainWindow) throws IOException {
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
        employeeField = new JTextField();

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
        contentPane.add(new JLabel("Сотрудник:"));
        contentPane.add(employeeField);


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

        String employee = employeeField.getText();
        if (employee.isEmpty()) {
            throw new IllegalArgumentException("Пожалуйста, укажите сотрудника.");
        }


        document = new PaymentOrder(new ObjectId(), PAYMENT_ORDER, number, date, user, amount, employee);
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