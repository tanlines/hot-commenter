package main.uicomponents;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public abstract class BaseGUI {
    private final JPanel mainPanel;
    private final JFrame frame;
    private final JButton start;
    private boolean done;
    private boolean closed;
    public BaseGUI(String title) {
        this.frame = new JFrame(title);
        this.mainPanel = new JPanel();
        this.start = new JButton("Start");
    }

    protected void init() {
        UI.align(mainPanel);
        frame.setContentPane(mainPanel);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closed = true;
            }
        };
        frame.addWindowListener(exitListener);

        start.setAlignmentX(0.5f);
        start.addActionListener(l->{
            frame.dispose();
            done = true;
        });
    }

    protected abstract void addComponents();

    public boolean isDone() {
        return done;
    }

    public void show() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getStart() {
        return start;
    }

    public boolean isClosed() {
        return closed;
    }

}
