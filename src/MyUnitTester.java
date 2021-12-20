import Controller.Controller;
import View.GUI;

import javax.swing.*;

/**
 * Description: Starts user interface on another thread, to allow users to test their classes.
 * Author: Edvin Lindholm
 * Cas: edli0007
 * C-user: c19elm
 * Version: 1.0
 * Date: 2020-11-15
 */

public class MyUnitTester {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            new Controller(gui);
            gui.show();
        });
    }
}

