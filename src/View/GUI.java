package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Description: Builds user interface, creates a swingworker to test a given class.
 * Author: Edvin Lindholm
 * Cas: edli0007
 * C-user: c19elm
 * Version: 1.0
 * Date: 2020-11-15
 */

public class GUI {

    private final JFrame frame;

    private JTextArea textOutput;

    private JTextField textInput;

    private JButton testB;
    private JButton clearB;

    /**
     * Creates User interface.
     */
    public GUI() {
        frame = new JFrame("My unit tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Build panels.
        JPanel upperPanel = buildUpperPanel();
        JPanel middlePanel = buildMiddlePanel();
        JPanel lowerPanel = buildLowerPanel();

        // Add panels.
        frame.add(upperPanel, BorderLayout.NORTH);
        frame.add(middlePanel, BorderLayout.CENTER);
        frame.add(lowerPanel, BorderLayout.SOUTH);

        // Set to reasonable size.
        frame.setSize(600, 300);
        frame.setMinimumSize(new Dimension(200, 200));
    }

    /**
     * Makes gui visible.
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * @return Upper panel, including a button to run tests,
     * and a text field to input test class.
     */
    private JPanel buildUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BorderLayout());

        // Create test button
        testB = new JButton("Run tests");

        // Create text field.
        textInput = new JTextField("", 30);

        // Add components to panel.
        upperPanel.add(textInput, BorderLayout.CENTER);
        upperPanel.add(testB, BorderLayout.EAST);

        return upperPanel;
    }

    /**
     * Enables or disables TestButton.
     *
     * @param value true if button should be enabled, otherwise false.
     */
    public void setTestButton(boolean value) {
        testB.setEnabled(value);
    }

    /**
     * Adds listener to ButtonListener.
     *
     * @param listener Listener to add to TestButton.
     */
    public void setTestButtonListener(ActionListener listener) {
        testB.addActionListener(listener);
    }

    /**
     * Gets input from JTextField.
     *
     * @return String from JTextField.
     */
    public String getTextInput() {
        return textInput.getText();
    }


    /**
     * @return middle panel,
     * including a text area in which the results will be written.
     */
    private JPanel buildMiddlePanel() {
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        // Create text area.
        textOutput = new JTextArea(10, 30);
        textOutput.setEditable(false);

        // Create scroller for text area.
        JScrollPane scroller = new JScrollPane(textOutput,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // Add components.
        middlePanel.add(scroller);

        return middlePanel;
    }

    /**
     * Gets TextArea.
     *
     * @return GUI textArea.
     */
    public JTextArea getTextArea() {
        return textOutput;
    }

    /**
     * @return Lower panel, including a clera button the clear the text area.
     */
    private JPanel buildLowerPanel() {
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        clearB = new JButton("Clear");

        lowerPanel.add(clearB, BorderLayout.CENTER);
        return lowerPanel;
    }

    /**
     * Add listener to ClearButton.
     *
     * @param listener Listener to add to ClearButton.
     */
    public void setClearButtonListener(ActionListener listener) {
        clearB.addActionListener(listener);
    }

    /**
     * Appends text to textArea.
     *
     * @param text Text to append.
     */
    public void appendText(String text) {
        textOutput.append(text);
    }

    /**
     * Set textArea text.
     *
     * @param text Text to set.
     */
    public void setText(String text) {
        textOutput.setText(text);
    }
}

