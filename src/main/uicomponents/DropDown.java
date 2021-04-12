package main.uicomponents;

import javax.swing.*;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DropDown<T> implements Panelled {
    private JLabel label;
    private JPanel panel;
    private JComboBox<T> comboBox;
    private final T[] allItems;
    @Override
    public JPanel getPanel() {
        return panel;
    }

    public DropDown(String name, Collection<T> items, Consumer<T> consumer) {
        this(name,items.toArray((T[]) new Object[items.size()]), consumer);
    }

    public DropDown(String name, T[] items, Consumer<T> consumer) {
        this.label = new JLabel(name);
        this.allItems = items;
        comboBox = new JComboBox<>(items);
        comboBox.addActionListener(l -> consumer.accept(getValue()));
        makePanel();
        consumer.accept(items[0]);
    }


    public DropDown(String name, T[] items, Consumer<T> consumer, Supplier<T> supplier) {
        this.label = new JLabel(name);
        this.allItems = items;
        comboBox = new JComboBox<>(items);
        makePanel();
        comboBox.addActionListener(l -> consumer.accept(getValue()));
        T item = supplier.get();
        if (item != null) {
            comboBox.setSelectedItem(item);
        } else {
            consumer.accept(items[0]);
        }
    }

    private void makePanel() {
        this.panel = new JPanel();
        panel.add(label);
        panel.add(comboBox);
    }

    public T getValue() {
        return comboBox.getItemAt(comboBox.getSelectedIndex());
    }

    public void filter(String s) {
        comboBox.removeAllItems();
        for (T allItem : allItems) {
            if (allItem.toString().toLowerCase().contains(s.toLowerCase())) comboBox.addItem(allItem);
        }
    }
}
