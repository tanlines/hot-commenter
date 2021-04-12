package main.uicomponents;

import lombok.Getter;

import javax.swing.*;
import java.util.function.Consumer;

public class TickBox implements Panelled {
    @Getter
    private JCheckBox checkBox;
    private JPanel panel;

    public TickBox(String name, Consumer<Boolean> consumer) {
        this.checkBox = new JCheckBox();
        this.checkBox.addChangeListener(l->consumer.accept(isSelected()));
        JLabel label = new JLabel(name);
        this.panel = new JPanel();
        panel.add(label);
        panel.add(this.checkBox);
    }

    public TickBox(String name, boolean initialValue, Consumer<Boolean> consumer) {
        this(name, consumer);
        this.checkBox.setSelected(initialValue);
    }

    @Override
    public JPanel getPanel() {
        return this.panel;
    }

    public boolean isSelected() {
        return this.checkBox.isSelected();
    }

    public void setValue(boolean bool) {
        this.checkBox.setSelected(bool);
    }
}
