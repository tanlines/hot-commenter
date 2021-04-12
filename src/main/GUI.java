package main;

import lombok.extern.slf4j.Slf4j;
import main.table.CommentTab;
import main.uicomponents.IntSpinner;
import main.uicomponents.TextSetter;
import main.uicomponents.UI;
import main.util.LoadAndSaveJson;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class GUI {
    private TextSetter shareCode = new TextSetter("Share code", s->{});
    private IntSpinner numberOfPages = new IntSpinner("Pages",new SpinnerNumberModel(3,1,20,1),i->{});
    private JButton search = new JButton("Search");
    private JTabbedPane tabbedPane;
    private JFrame frame = new JFrame("Hot Commenter");
    private List<CommentTab> tabs = new CopyOnWriteArrayList<>();

    private Scraper scraper;
    private Settings settings;
    public GUI(Scraper scraper, Settings settings) {
        this.scraper = scraper;
        this.settings = settings;
        init();
        settings.getStockPages().forEach(this::addTab);
    }

    private void init() {
        tabbedPane = new JTabbedPane();
        tabbedPane.add("Main",getPanel());
        tabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount()==2) {
                    int index = tabbedPane.indexAtLocation(evt.getX(), evt.getY());
                    for (CommentTab tab : tabs) {
                        if (tab.getPanel().equals(tabbedPane.getComponentAt(index))) {
                            tabs.remove(tab);
                            tabbedPane.remove(tab.getPanel());
                            return;
                        }
                    }
                }
            }
        });
        shareCode.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addTab(shareCode.getText(),numberOfPages.getValue());
                }
            }
        });
        search.addActionListener(l -> {
            addTab(shareCode.getText(),numberOfPages.getValue());
        });
        frame.setContentPane(tabbedPane);

        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                settings.getStockPages().clear();
                for (CommentTab tab : tabs) {
                    settings.getStockPages().put(tab.getTabName(),tab.getNumberOfPagesSpinner().getValue());
                }
                LoadAndSaveJson.save("savedShares",settings);
                System.exit(0);
            }
        };
        frame.addWindowListener(exitListener);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel getPanel() {
        JPanel main = UI.boxPanel();
        main.setBorder(BorderFactory.createEmptyBorder(5,300,300,300));
        main.add(UI.createJPanel(p-> {
            p.add(shareCode.getPanel());
            p.add(numberOfPages.getPanel());
            p.add(search);
        }));
        return main;
    }

    private void addTab(String code, int pages) {
        log.info("Adding tab " + code);
        CommentTab tab = new CommentTab(code, scraper);
        shareCode.setValue("");
        if (!tab.search(code,pages)) {
            return;
        }
        tabs.add(tab);
        tabbedPane.add(code,tab.getPanel());
//        frame.pack();
    }
}
