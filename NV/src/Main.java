package src;

import javax.swing.*;
import src.presentation.LoginFrame;

/**
 * NewsVine - Authentic News Management System
 * Entry point of the application
 */
public class Main {

    public static void main(String[] args) {
        // Set system look and feel for better UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}
