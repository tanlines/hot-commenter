package main.table;

import lombok.Getter;
import main.Comment;

import javax.swing.table.AbstractTableModel;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyTableModel extends AbstractTableModel {
    public static final int DATE_INDEX = 0;
    public static final int SUBJECT_INDEX = 1;
    public static final int LIKES_INDEX = 2;
    public static final int COMMENT_INDEX = 3;
    public static final int VIEWS_INDEX = 4;
    public static final int POSTER_INDEX = 5;
    public static final int TAG_INDEX = 6;

    @Getter
    private CopyOnWriteArrayList<Comment> comments;

    public String getColumnName(int col) {
        switch (col) {
            case TAG_INDEX: return "Tag";
            case SUBJECT_INDEX: return "Subject";
            case POSTER_INDEX: return "Poster";
            case COMMENT_INDEX: return "Comments";
            case VIEWS_INDEX: return "Views";
            case LIKES_INDEX: return "Likes";
            case DATE_INDEX: return "Date";
        }
        return "ASDF";
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public MyTableModel() {
        this.comments = new CopyOnWriteArrayList<>();
    }

    @Override
    public int getRowCount() {
        return comments.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Comment comment = comments.get(rowIndex);
        switch (columnIndex) {
            case TAG_INDEX: return comment.getTag();
            case SUBJECT_INDEX: return comment.getSubject();
            case POSTER_INDEX: return comment.getPoster();
            case COMMENT_INDEX: return comment.getComments();
            case VIEWS_INDEX: return comment.getViews();
            case LIKES_INDEX: return comment.getLikes();
            case DATE_INDEX: return comment.getDate();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case TAG_INDEX: return String.class;
            case SUBJECT_INDEX: return String.class;
            case POSTER_INDEX: return String.class;
            case COMMENT_INDEX: return Integer.class;
            case VIEWS_INDEX: return Integer.class;
            case LIKES_INDEX: return Integer.class;
            case DATE_INDEX: return LocalDateTime.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
