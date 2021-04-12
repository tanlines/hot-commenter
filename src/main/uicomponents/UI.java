package main.uicomponents;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

public class UI {
    /**
     * Hollow factory/decorator for JLabels
     * @param actions
     * @return
     */
    @SafeVarargs
    public static JPanel createJPanel(Consumer<JPanel>... actions) {
        JPanel panel = new JPanel();
        for(Consumer<JPanel> action: actions) {
            action.accept(panel);
        }
        return panel;
    }

    /**
     * Hollow factory/decorator for JLabels
     * @param actions
     * @return
     */
    @SafeVarargs
    public static JLabel createJLabel(Consumer<JLabel>... actions) {
        JLabel label = new JLabel();
        for(Consumer<JLabel> action: actions) {
            action.accept(label);
        }
        return label;
    }

    public static void align(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof JPanel) {
                align((JPanel)component);
            } else if (component instanceof JComponent) {
                ((JComponent)component).setAlignmentX(0.5f);
            }
        }
    }

    public static JPanel boxPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        return panel;
    }

    public static JPanel linkPanel(String labelText, String linkText) {
        JLabel label = new JLabel("<html><span style=\"color: #000099;\">" +
                "<u>"+
                labelText +
                "<u>" +
                "</span></html>");
        try {
            final URI uri = new URI(linkText);
            label.setToolTipText(uri.toString());
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    openLink(uri);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(label);
        return p;
        //
    }


    public static void openLink(URI uri) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
