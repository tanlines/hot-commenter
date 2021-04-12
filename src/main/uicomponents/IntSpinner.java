package main.uicomponents;
import javax.swing.*;
import java.util.function.IntConsumer;

public class IntSpinner implements Panelled {
    private JSpinner spinner;
    private JLabel label;
    private SpinnerNumberModel spinnerNumberModel;
    private JPanel panel;

    public IntSpinner(String name, SpinnerNumberModel spinnerNumberModel, IntConsumer consumer) {
        this.label = new JLabel(name);
        this.spinnerNumberModel = spinnerNumberModel;
        this.spinner = new JSpinner();
        this.spinner.setModel(this.spinnerNumberModel);
        this.spinner.addChangeListener(l->consumer.accept(getValue()));
        ((JSpinner.DefaultEditor) this.spinner.getEditor()).getTextField().setColumns(7);
        this.panel = new JPanel();
        this.panel.add(label);
        this.panel.add(spinner);
        consumer.accept(getValue());
    }

//    public IntSpinner(String name, SpinnerNumberModel spinnerNumberModel, IntConsumer consumer, Supplier<Integer> supplier) {
//        this(name, spinnerNumberModel,consumer);
//        for (ChangeListener changeListener : this.spinner.getChangeListeners()) {
//            this.spinner.removeChangeListener(changeListener);
//        }
//        this.spinner.addChangeListener(l->consumer.accept(supplier.get()));
//        consumer.accept(supplier.get());
//        setValue(supplier.get());
//    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    public int getValue() {
        return (Integer)this.spinner.getValue();
    }

    public JSpinner getSpinner() {
        return spinner;
    }

    public void setValue(int value) {
        this.spinnerNumberModel.setValue(clamp(value,(int)spinnerNumberModel.getMinimum(),(int)spinnerNumberModel.getMaximum()));
    }
    public static int clamp(int num, int min, int max) {
        return Math.max(Math.min(num,max),min);
    }
}
