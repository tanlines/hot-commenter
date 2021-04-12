package main.util;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import javax.swing.*;
import java.awt.*;

public class LogFile extends AppenderBase<ILoggingEvent> {
    static JFrame jFrame;
    static JTextArea jTextArea;
    static JScrollPane areaScrollPane;

    public LogFile() {
        jFrame = new JFrame("Log file");
        jTextArea = new JTextArea();
        areaScrollPane = new JScrollPane(jTextArea);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(500, 250));
        jFrame.setContentPane(areaScrollPane);
        jTextArea.setAutoscrolls(true);
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        if (this.encoder == null) {
            addError("No encoder set for the appender named ["+ name +"].");
            return;
        }
        String s = new String(this.encoder.encode(iLoggingEvent));
        if (jTextArea.getText().length() > 1000) {
            jTextArea.setText("");
        }
        jTextArea.append(s);
    }

    PatternLayoutEncoder encoder;
    public PatternLayoutEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(PatternLayoutEncoder encoder) {
        this.encoder = encoder;
    }

    public static void showLog() {
        if (!jFrame.isVisible()) {
            jFrame.setLocationRelativeTo(null);
            jFrame.pack();
            jFrame.setVisible(true);
        }
    }

    public static JButton logButton() {
        JButton button = new JButton("Show log");
        button.addActionListener(l -> {
            showLog();
        });
        return button;
    }
}
