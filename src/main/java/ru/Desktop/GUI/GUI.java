package ru.Desktop.GUI;

import ru.Desktop.models.Document;
import ru.Desktop.repositories.MongoDBRepository;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public class GUI extends JFrame {
    private GUIEventHandler eventHandler;
    private JButton createInvoiceButton;
    private JButton createPaymentOrderButton;
    private JButton createPaymentRequestButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton viewButton;
    private JButton exitButton;
    private ArrayList<JCheckBox> checkboxList;
    private ArrayList<Document> documents;

    public GUI() {
        // Настройка окна
        setTitle("Document App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // Установка размеров окна
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 300, dimension.height / 2 - 200, 600, 400);
        setMinimumSize(new Dimension(500, 200));

        // Создаем список объектов для отображения ==============================
        try {
            MongoDBRepository mongoDBRepository = MongoDBRepository.getMongoDBRepository();
            ArrayList<Document> allDocuments = mongoDBRepository.getAllDocuments();
        } catch (IOException | ParseException | RuntimeException e) {
            String msg = "Произошла ошибка " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
            dispose();
            System.exit(0);
        }
        checkboxList = new ArrayList<>();
        JPanel listPanel = new JPanel(new GridLayout(10, 1));
        for (int i = 0; i < 10; i++) {
            JCheckBox checkbox = new JCheckBox("Item " + (i + 1));
            listPanel.add(checkbox);
            checkboxList.add(checkbox);
        }

        // Создаем кнопки и панель для них
        createInvoiceButton = new JButton("Накладная");
        createPaymentOrderButton = new JButton("Платёжка");
        createPaymentRequestButton = new JButton("Заявка на оплату");
        saveButton = new JButton("Сохранить");
        loadButton = new JButton("Загрузить");
        viewButton = new JButton("Просмотр");
        exitButton = new JButton("Выход");

        // Обработчик событий
        eventHandler = new GUIEventHandler(this);
        createInvoiceButton.addActionListener(e -> eventHandler.handleCreateInvoiceButtonClick());
        createPaymentOrderButton.addActionListener(e -> eventHandler.handleCreatePaymentOrderButtonClick());
        createPaymentRequestButton.addActionListener(e -> eventHandler.handleCreatePaymentRequestButtonClick());
        saveButton.addActionListener(e -> eventHandler.handleSaveButtonClick());
        loadButton.addActionListener(e -> eventHandler.handleLoadButtonClick());
        exitButton.addActionListener(e -> eventHandler.handleExitButtonClick());

        // Добавляем кнопки в панель и панель в правую часть окна
        JPanel buttonPanel = new JPanel(new GridLayout(7, 1));
        buttonPanel.add(createInvoiceButton);
        buttonPanel.add(createPaymentOrderButton);
        buttonPanel.add(createPaymentRequestButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(exitButton);


        // Добавить компоненты на форму
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.EAST);
        add(listPanel, BorderLayout.WEST);
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public void updateList(Document doc){
        return;
    }
}
