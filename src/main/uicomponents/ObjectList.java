package main.uicomponents;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.function.Consumer;

public class ObjectList<T> implements Panelled {
    private JList<T> list;
    private DefaultListModel<T> listModel;
    private JScrollPane listScroller;
    private JPanel panel;
    public ObjectList(Collection<T> items) {
        this(items,c -> {});
    }

    public ObjectList(Collection<T> items, Consumer<T> consumer) {
        this.list = new JList<>();
        this.listModel = new DefaultListModel<>();
        for (T item : items) {
            listModel.addElement(item);
        }
        this.list.setModel(listModel);
        this.list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.listScroller = new JScrollPane(list);

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() != MouseEvent.BUTTON1) return;
                if (evt.getClickCount() == 2) {
                    int index = list.getSelectedIndex();
                    T item = listModel.get(index);
                    consumer.accept(item);
                }
            }
        });
        makePanel();
    }

    private void makePanel() {
        this.panel = new JPanel();
        panel.add(listScroller);
    }

    public void addDoubleClickRemove(Consumer<T> removeConsumer) {
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() != MouseEvent.BUTTON1) return;
                if (evt.getClickCount() == 2) {
                    int index = list.getSelectedIndex();
                    T item = listModel.get(index);
                    listModel.remove(index);
                    removeConsumer.accept(item);
                }
            }
        });
    }

    public T getSelectedItem() {
        return listModel.get(list.getSelectedIndex());
    }

    @Override
    public JPanel getPanel() {
        return this.panel;
    }
}
