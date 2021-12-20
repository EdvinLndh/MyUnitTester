package Controller;

import View.GUI;
import Model.Tester;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Description: Runs the given methods,
 * Publishing output to gui's textArea.
 * Author: Edvin Lindholm
 * Cas: edli0007
 * C-user: c19elm
 * Version: 1.0
 * Date: 2020-11-17
 */

public class Worker extends SwingWorker<Void, String> {

    private final Method[] methodList;

    private final JTextArea txtOutput;

    private final GUI gui;

    private final Tester tester;

    /**
     * Swingworker that runs tests.
     *
     * @param gui    GUI to append to.
     * @param tester Model class that gives information to worker.
     */
    public Worker(GUI gui, Tester tester) {
        super();
        this.tester = tester;
        this.methodList = tester.getMethods();
        this.gui = gui;
        this.txtOutput = gui.getTextArea();
    }


    @Override
    protected Void doInBackground() {
        try {
            for (Method method : methodList) {

                publish(tester.runTest(method));
            }
            publish(tester.concludeTest());

        } catch (InvocationTargetException | IllegalAccessException e) {
            publish("Exception " + e.getMessage() + ", try again. \n");
        }
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        for (String s : chunks) {
            txtOutput.append(s);
        }
    }

    @Override
    protected void done() {
        gui.setTestButton(true);
    }
}




