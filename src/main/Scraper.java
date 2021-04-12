package main;

import main.table.MyTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Scraper {
    public void scrape(MyTableModel myTableModel, String code) {
        scrape(myTableModel, code,1);
    }

    public void scrape(MyTableModel myTableModel, String code, int pages) {
        myTableModel.getComments().clear();
        for (int i = 1; i <= pages; i++) {
            myTableModel.getComments().addAll(scrapePage(code,i));
        }
    }

    private List<Comment> scrapePage(String code, int page) {
        List<Comment> commentsList = new ArrayList<>();
        String url = "https://hotcopper.com.au/asx/"+code+"/discussion/page-"+page;
        String html = null;
        try {
            html = Jsoup.connect(url).get().html();
            Document doc = Jsoup.parse(html);

            for (Element elementsByClass : doc.getElementsByTag("tr")) {
                Elements elements = elementsByClass.getElementsByTag("td");
                if (elements.size() == 9) {
                    String junk = elements.get(0).text();
                    String shareCode = elements.get(1).text();
                    String subject = elements.get(2).text();
                    String href = elements.get(2).getElementsByTag("a").get(0).attributes().get("href");
                    String junk2 = elements.get(3).text();
                    String poster = elements.get(4).text();
                    int comments = parseInt(elements.get(5).text());
                    int views = parseInt(elements.get(6).text());
                    int likes = parseInt(elements.get(7).text());
                    LocalDateTime date = parseDate(elements.get(8).text());
                    commentsList.add(new Comment(shareCode,subject,poster,comments,views,likes, date,href));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return commentsList;
    }


    public static int parseInt(String s) {
        if (s == null || s.isEmpty()) return 0;
        return Integer.parseInt(s.replaceAll("\\D","")) * (s.contains("K") ? 1000 : 1);
    }

    //LocalDateTime aux = LocalDateTime.parse("2017-12-05 20:16", DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"));
    private LocalDateTime parseDate(String date) {
        if (date.contains(":")) {
            String[] arr = date.split(":");
            int hours = parseInt(arr[0]);
            int mins = parseInt(arr[1]);
            return LocalDate.now().atTime(hours,mins);
        } else {
            return LocalDate.parse(date,DateTimeFormatter.ofPattern("dd/MM/yy")).atTime(0,0);
        }
    }
}
