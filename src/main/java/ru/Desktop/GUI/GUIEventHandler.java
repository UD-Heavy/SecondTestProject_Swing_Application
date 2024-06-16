package ru.Desktop.GUI;

import javax.swing.*;

public class GUIEventHandler {
    private GUI gui;

    public GUIEventHandler(GUI gui) {
        this.gui = gui;
    }

    public void handleCreateInvoiceButtonClick() {
        // Добавить код для создания документа
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
