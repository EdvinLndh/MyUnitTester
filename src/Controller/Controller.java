package Controller;

import View.GUI;
import Model.Tester;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

/**
 * Description: Controller that takes input
 * and issues commands to model and worker.
 * Author: Edvin Lindholm
 * Cas: edli0007
 * C-user: c19elm
 * Version: 1.0
 * Date: 2020-11-17
 */

public class Controller {

    private final GUI gui;

    private final JTextArea txtOutput;

    /**
     * Class that handles information between model and view.
     *
     * @param gui Interface to update.
     */
    public Controller(GUI gui) {
        this.gui = gui;
        txtOutput = gui.getTextArea();

        // Create button listeners.
        gui.setTestButtonListener(new testButtonListener());
        gui.setClearButtonListener(e -> gui.setText(""));
    }

    /**
     * Description: Listener for the testButton in gui.
     */
    private class testButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            // Get text from textfield
            String textInputValue = gui.getTextInput();
            gui.appendText("Testing " + textInputValue + "\n\n");
            if (!textInputValue.isEmpty()) {
                try {
                    Tester test = new Tester(textInputValue);
                    // Check the input is a class according to specification.
                    String assertResult = test.assertClass();
                    if (assertResult != null) {
                        txtOutput.append(assertResult);
                        return;
                    }
                    // If input is okay get methods, and run tests with worker.
                    Method[] methodList = test.getMethods();
                    test.getSetUpAndTearDown(methodList);
                    gui.setTestButton(false);
                    Worker worker = new Worker(gui,
                            test);
                    worker.execute();
                } catch (RuntimeException e) {
                    gui.appendText("Exception " + e.toString()
                            + ", try again. \n");
                } catch (ClassNotFoundException notFoundException) {
                    gui.appendText("Couldn't find class, try again. \n");
                }
                catch (NoClassDefFoundError e) {
                    gui.appendText("Aborted");
                }
            } else {
                gui.appendText("No testClass given, try again. \n");
            }
        }
    }
}

