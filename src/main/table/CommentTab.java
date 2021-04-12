package main.table;

import lombok.Getter;
import main.Comment;
import main.Scraper;
import main.uicomponents.IntSpinner;
import main.uicomponents.LinkIcon;
import main.uicomponents.TextSetter;
import main.uicomponents.UI;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

import static main.table.MyTableModel.*;

public class CommentTab {
    @Getter
    private final String tabName;
    private TextSetter shareCodeTextSetter = new TextSetter("Share code", s->{});
    @Getter
    private IntSpinner numberOfPagesSpinner = new IntSpinner("Pages",new SpinnerNumberModel(3,1,20,1), i->{});
    private JTextField statusText = new JTextField(20);
    private IntSpinner minLikes;
    private JButton search = new JButton("Search");
    private JTable table;
    private TableRowSorter<MyTableModel> sorter;
    private JPanel panel;

    private MyTableModel myTableModel;
    private Scraper scraper;
    public CommentTab(String tabName, Scraper scraper) {
        this.tabName = tabName;
        this.scraper = scraper;
        init();
    }

    public boolean search(String code, int numberOfPages) {
        shareCodeTextSetter.setValue(code);
        numberOfPagesSpinner.setValue(numberOfPages);
        refresh();
        return myTableModel.getComments().size() > 0;
    }

    private void init() {
        CopyOnWriteArrayList<Comment> comments = new CopyOnWriteArrayList<>();
        comments.add(new Comment("tag","subject","today",1,2,3, LocalDateTime.now(),"threads/ann-global-institutions-back-core-lithium-40-million-placement.5889193/page-569"));
        comments.add(new Comment("tagg","pastry","5daysplus",4,5,6, LocalDateTime.now().plusDays(5),"threads/ann-global-institutions-back-core-lithium-40-million-placement.5889193/page-569"));
        comments.add(new Comment("taggg","zigazag","10daysplus",7,8,9, LocalDateTime.now().plusDays(10),"threads/ann-global-institutions-back-core-lithium-40-million-placement.5889193/page-569"));


        this.myTableModel = new MyTableModel();
        myTableModel.getComments().addAll(comments);
        sorter = new TableRowSorter<>(myTableModel);
        table = new JTable(myTableModel);

        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(
                event -> {
                    int viewRow = table.getSelectedRow();
                    if (viewRow < 0) {
                        //Selection got filtered away.
                        statusText.setText("");
                    } else {
                        int modelRow = table.convertRowIndexToModel(viewRow);
                        statusText.setText(
                                String.format("Selected Row in view: %d. " +
                                                "Selected Row in model: %d.",
                                        viewRow, modelRow));
                    }
                }
        );

        table.getColumnModel().getColumn(DATE_INDEX).setCellRenderer(MyCellRenderer.getCellRenderer());
        table.getColumnModel().getColumn(LIKES_INDEX).setCellRenderer(MyCellRenderer.getCellRenderer());
        table.getColumnModel().getColumn(COMMENT_INDEX).setCellRenderer(MyCellRenderer.getCellRenderer());
        table.getColumnModel().getColumn(VIEWS_INDEX).setCellRenderer(MyCellRenderer.getCellRenderer());
        table.getColumnModel().getColumn(DATE_INDEX).setMaxWidth(60);
        table.getColumnModel().getColumn(SUBJECT_INDEX).setPreferredWidth(600);
        table.getColumnModel().getColumn(LIKES_INDEX).setMaxWidth(60);
        table.getColumnModel().getColumn(COMMENT_INDEX).setMaxWidth(60);
        table.getColumnModel().getColumn(VIEWS_INDEX).setMaxWidth(60);
        table.getColumnModel().getColumn(POSTER_INDEX).setPreferredWidth(200);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFont(new Font(table.getFont().getName(), Font.PLAIN, 20));
        table.setRowHeight(30);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int viewRow = table.getSelectedRow();
                    if (viewRow < 0) {
                    } else {
                        int modelRow = table.convertRowIndexToModel(viewRow);
                        myTableModel.getComments().get(modelRow).openLink();
                    }
                }
            }
        });
        minLikes = new IntSpinner("Minimum likes", new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1), this::newFilter);
        search.addActionListener(l -> {
            refresh();
        });
        shareCodeTextSetter.getTextField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    refresh();
                }
            }
        });

        commsec = new LinkIcon("/resources/commsec.jpg","https://www2.commsec.com.au/",50);
        hotcopper = new LinkIcon("/resources/hotcopper.png","https://hotcopper.com.au/",50);
        asx = new LinkIcon("/resources/asx.jpg","https://www2.asx.com.au/",50);
        smallcaps = new LinkIcon("/resources/smallcaps.jpg","https://smallcaps.com.au/",50);
        stocktrack = new LinkIcon("/resources/stocktrack.png","https://stocktrack.com.au/",50);
        //https://www2.asx.com.au/markets/company/bot
        panel = getPanel();
    }

    //https://hotcopper.com.au/asx/rnt/discussion/
    private void refresh() {
        stocktrack.setLink("https://stocktrack.com.au/asx/"+shareCodeTextSetter.getText()+"/fundamentals");
        smallcaps.setLink("https://smallcaps.com.au/stocks/?symbol="+shareCodeTextSetter.getText());
        asx.setLink("https://www2.asx.com.au/markets/company/"+shareCodeTextSetter.getText());
        commsec.setLink("https://www2.commsec.com.au/quotes/summary?stockCode="+shareCodeTextSetter.getText()+"&exchangeCode=ASX");
        hotcopper.setLink("https://hotcopper.com.au/asx/"+shareCodeTextSetter.getText()+"/discussion/");
        scraper.scrape(myTableModel, shareCodeTextSetter.getText(),numberOfPagesSpinner.getValue());
        newFilter(minLikes.getValue());
    }


    private LinkIcon commsec;
    private LinkIcon hotcopper;
    private LinkIcon asx;
    private LinkIcon smallcaps;
    private LinkIcon stocktrack;

    public JPanel getPanel() {
        if (panel != null) return panel;
        panel = UI.boxPanel();
        panel.add(UI.createJPanel(p-> {
            p.add(shareCodeTextSetter.getPanel());
            p.add(numberOfPagesSpinner.getPanel());
            p.add(minLikes.getPanel());
            p.add(search);
        }));
        panel.add(UI.createJPanel(p-> {
            p.add(commsec.getPanel());
            p.add(hotcopper.getPanel());
            p.add(asx.getPanel());
            p.add(smallcaps.getPanel());
            p.add(stocktrack.getPanel());
        }));
//        panel.add(statusText);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);
        return panel;
    }

    private void newFilter(int minimumLikes) {
        RowFilter<MyTableModel, Object> ageFilter = RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, minimumLikes-1, LIKES_INDEX);
        sorter.setRowFilter(ageFilter);
    }
}
