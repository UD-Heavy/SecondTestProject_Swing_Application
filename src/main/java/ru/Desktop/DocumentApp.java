package ru.Desktop;

import ru.Desktop.GUI.MainWindow;

import javax.swing.*;

public class DocumentApp extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
