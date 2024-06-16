package ru.Desktop.GUI;

import org.bson.Document;
import ru.Desktop.Services.MongoDBRepository;

import javax.swing.*;
import java.io.IOException;

public class GUIEventHandler {
    private GUI gui;
    private MongoDBRepository mongoDBRepository;

    public GUIEventHandler(GUI gui) {
        try {
            this.gui = gui;
            mongoDBRepository = new MongoDBRepository();
        } catch (IOException e) {
            String msg = "Произошла ошибка " + e.getMessage();
            JOptionPane.showConfirmDialog(null, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleCreateInvoiceButtonClick() {
        // Добавить код для создания документа
        System.out.println("123");
        mongoDBRepository.createDocument();
    }

    public void handleSaveButtonClick() {
        // Добавить код для сохранения документа
    }

    public void handleLoadButtonClick() {
        // Добавить код для загрузки документа
    }

    public void handleExitButtonClick() {
        int result = JOptionPane.showConfirmDialog(gui,
                "Вы действительно хотите выйти?",
                "Подтверждение выхода",
                JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_NO_OPTION) {
            gui.dispose();
            System.exit(0);
        }
    }
}
