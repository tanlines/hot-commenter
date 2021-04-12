package main.uicomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.net.URISyntaxException;

public class LinkIcon implements Panelled {
    private Image img;
    private JLabel label;
    private String link;

    public LinkIcon(String path, String link) {
        this(path, link, 30);
    }

    public LinkIcon(String path, String link, int size) {
        this.img = new ImageIcon(getClass().getResource(path)).getImage();
        this.link = link;
        ImageIcon imageIcon = new ImageIcon(img); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(size, size,  Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back
        label = new JLabel(imageIcon);
        setLink(link);
    }

    public void setLink(String link) {
        try {
            final URI uri = new URI(link);
            label.setToolTipText(uri.toString());
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            for (MouseListener mouseListener : label.getMouseListeners()) {
                label.removeMouseListener(mouseListener);
            }
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    UI.openLink(uri);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JPanel getPanel() {
        return UI.createJPanel(p -> p.add(label));
    }
}
