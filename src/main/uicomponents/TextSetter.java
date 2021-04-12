package main.uicomponents;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.function.Consumer;

public class TextSetter implements Panelled {
    private JTextField textField;
    private JPanel panel;
    public TextSetter(String name, String initialText, Consumer<String> consumer) {
        this.textField = new JTextField(Math.max(initialText.length(),7));
        this.textField.setText(initialText);
        JLabel label = new JLabel(name);
        this.panel = new JPanel();
        this.panel.add(label);
        this.panel.add(this.textField);

        this.textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                consumer.accept(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                consumer.accept(getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                consumer.accept(getText());
            }
        });
        consumer.accept(initialText);
    }

    public TextSetter(String name, Consumer<String> consumer) {
        this(name,"",consumer);
    }

    @Override
    public JPanel getPanel() {
        return this.panel;
    }

    public String getText() {
        return this.textField.getText();
    }

    public void clearText() {
        this.textField.setText("");
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setValue(String text) {
        this.textField.setText(text);
    }

    public void setVertical() {
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
    }
}
