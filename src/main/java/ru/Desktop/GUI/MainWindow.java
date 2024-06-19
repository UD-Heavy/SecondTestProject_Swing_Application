package ru.Desktop.GUI;

import ru.Desktop.models.Document;
import ru.Desktop.repositories.MongoDBRepository;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;


public class MainWindow extends JFrame { // главное окно приложения
    private MongoDBRepository mongoDBRepository;
    private GUIEventHandler eventHandler;
    private JButton createInvoiceButton;
    private JButton createPaymentOrderButton;
    private JButton createPaymentRequestButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton viewButton;
    private JButton deleteButton;
    private JButton exitButton;
    private ArrayList<Document> documents;
    private JPanel listPanel;
    private JScrollPane scrollPane;
    private ArrayList<JCheckBox> checkboxList;

    public MainWindow() {
        // настройка праметров отображения
        setupWindow();
        setupWindowSize();

        // вывод списка документов из бд
        creatStartDocumentList();

        // отображение списка на экране
        createListObject();


        // Создаем кнопки и панель для них
        createInvoiceButton = new JButton("Накладная");
        createPaymentOrderButton = new JButton("Платёжка");
        createPaymentRequestButton = new JButton("Заявка на оплату");
        saveButton = new JButton("Сохранить");
        loadButton = new JButton("Загрузить");
        viewButton = new JButton("Просмотр");
        deleteButton = new JButton("Удалить");
        exitButton = new JButton("Выход");

        // Обработчик событий
        eventHandler = new GUIEventHandler(this);
        createInvoiceButton.addActionListener(e -> eventHandler.handleCreateInvoiceButtonClick());
        createPaymentOrderButton.addActionListener(e -> eventHandler.handleCreatePaymentOrderButtonClick());
        createPaymentRequestButton.addActionListener(e -> eventHandler.handleCreatePaymentRequestButtonClick());
        saveButton.addActionListener(e -> eventHandler.handleSaveButtonClick());
        loadButton.addActionListener(e -> eventHandler.handleLoadButtonClick());
        viewButton.addActionListener(e -> eventHandler.handleViewButtonClick());
        deleteButton.addActionListener(e -> eventHandler.handleDeleteButtonClick());
        exitButton.addActionListener(e -> eventHandler.handleExitButtonClick());

        // Добавляем кнопки в панель и панель в правую часть окна
        JPanel buttonPanel = new JPanel(new GridLayout(8, 1));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(createInvoiceButton);
        buttonPanel.add(createPaymentOrderButton);
        buttonPanel.add(createPaymentRequestButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exitButton);

        // Добавить компоненты на форму
        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.EAST);
        add(scrollPane, BorderLayout.WEST);
    }

    // Настройка окна
    private void setupWindow() {
        setTitle("Document App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Установка размеров окна
    private void setupWindowSize() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        setBounds(dimension.width / 2 - 275, dimension.height / 2 - 200, 550, 400);
        setMinimumSize(new Dimension(500, 400));
    }

    // Создаем список объектов для отображения
    private void creatStartDocumentList() {
        try {
            mongoDBRepository = MongoDBRepository.getMongoDBRepository();
            documents = mongoDBRepository.getAllDocuments();
        } catch (IOException | ParseException | RuntimeException e) {
            JOptionPane.showMessageDialog(null, "Произошла ошибка " + e.getMessage(),
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            dispose();
            mongoDBRepository.closeConnection();
            System.exit(0);
        }
    }

    // настройка параметров списка
    private void createListObject() {
        checkboxList = new ArrayList<>();
        // новый лист и сетка
        listPanel = new JPanel(new GridLayout(10, 1));
        // добавляем элемементы тольо по вертикали
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        // настройк границ
        listPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (Document document : documents) {
            JCheckBox checkbox = new JCheckBox((document.toString()));
            listPanel.add(checkbox);
            checkboxList.add(checkbox);
        }
        // установка скроллбара
        scrollPane = new JScrollPane(listPanel);
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    // обновляем содержимое списка
    public void updateList(Document document) {
        documents.add(document);
        checkboxList.add(new JCheckBox(document.toString()));
        refreshListPanel();
    }

    // обновляем отображение списка
    private void refreshListPanel() {
        listPanel.removeAll();
        checkboxList.clear();
        for (Document document : documents) {
            JCheckBox checkbox = new JCheckBox(document.toString());
            listPanel.add(checkbox);
            checkboxList.add(checkbox);
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    // удаление элемента
    public ArrayList<Integer> deleteElements() {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = checkboxList.size() - 1; i >= 0; i--) {
            if (checkboxList.get(i).isSelected()) {
                listPanel.remove(checkboxList.get(i));
                checkboxList.remove(i);
                indexes.add(i);
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
        return indexes;
    }

    // проверка, что выбран 1 документ
    public Integer getElement() {
        int selectedIndex = -1;
        int selectedCount = 0;
        for (int i = checkboxList.size() - 1; i >= 0; i--) {
            if (checkboxList.get(i).isSelected()) {
                selectedIndex = i;
                selectedCount++;
                if (selectedCount > 1) {
                    return null;
                }
            }
        }
        return selectedCount == 1 ? selectedIndex : null;
    }
}
