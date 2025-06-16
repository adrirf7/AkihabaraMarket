package com.akihabara.market.main;
import javax.swing.SwingUtilities;
import com.akihabara.market.view.InventarioGUI;
import com.akihabara.market.controller.GuiController;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventarioGUI gui = new InventarioGUI();
            new GuiController(gui);
            gui.setVisible(true);
        });
    }
}
