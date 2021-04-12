package main.table;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MyCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof LocalDateTime) {
            LocalDateTime time = (LocalDateTime)value;
            if (time.toLocalDate().isEqual(LocalDate.now())) {
                value = time.format(DateTimeFormatter.ofPattern("HH:mm"));
            } else {
                value = time.format(DateTimeFormatter.ofPattern("dd/MM"));
            }
        }
        if (value instanceof Integer) {
            value = formatQuantity((Integer)value);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
    private static MyCellRenderer cellRenderer;
    public static MyCellRenderer getCellRenderer() {
        if (cellRenderer != null) return cellRenderer;
        return cellRenderer = new MyCellRenderer();
    }
    private MyCellRenderer() {
    }

    public String formatQuantity(long quantity) {
        if (quantity >= 1000) {
            return (quantity/1000) + "K";
        }
        return NumberFormat.getInstance(Locale.ENGLISH).format(quantity);
    }
}
