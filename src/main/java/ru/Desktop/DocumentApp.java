package ru.Desktop;

import ru.Desktop.GUI.GUI;
import ru.Desktop.GUI.GUIEventHandler;

import javax.swing.*;

public class DocumentApp extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }
}
