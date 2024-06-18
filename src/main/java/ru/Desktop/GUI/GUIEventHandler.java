package ru.Desktop.GUI;

import ru.Desktop.repositories.MongoDBRepository;

import javax.swing.*;
import java.io.IOException;

public class GUIEventHandler {
    private GUI gui;
    private MongoDBRepository mongoDBRepository;

    public GUIEventHandler(GUI gui) {
        try {
            this.gui = gui;
            mongoDBRepository = MongoDBRepository.getMongoDBRepository();
        } catch (IOException e) {
            String msg = "Произошла ошибка " + e.getMessage();
            JOptionPane.showMessageDialog(null, msg, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void handleCreateInvoiceButtonClick() {
        InvoiceCreationDialog invoiceCreationDialog = null;
        try {
            invoiceCreationDialog = new InvoiceCreationDialog(gui);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleCreatePaymentOrderButtonClick() {
        PaymentOrderCreationDialog PaymentOrderCreationDialog = null;
        try {
            PaymentOrderCreationDialog = new PaymentOrderCreationDialog(gui);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleCreatePaymentRequestButtonClick() {
        PaymentRequestCreationDialog PaymentRequestCreationDialog = null;
        try {
            PaymentRequestCreationDialog = new PaymentRequestCreationDialog(gui);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleSaveButtonClick() {
        // Добавить код для сохранения документа в файл
    }

    public void handleLoadButtonClick() {
        // Добавить код для загрузки документа из файла
    }

    public void handleDeleteButtonClick() {

    }

    public void handleViewButtonClick() {

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
