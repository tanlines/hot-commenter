package main;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Comment {
    private String tag;
    private String subject;
    private String poster;
    private int comments;
    private int views;
    private int likes;
    private LocalDateTime date;
    private String url;

    public void openLink() {
        URI uri = null;
        if (Desktop.isDesktopSupported()) {
            try {
                uri = new URI("https://hotcopper.com.au"+url);
                Desktop.getDesktop().browse(uri);
            } catch (IOException | URISyntaxException e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
    }
}
