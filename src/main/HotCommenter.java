package main;

import main.util.LoadAndSaveJson;

public class HotCommenter {
    private Scraper scraper;
    public HotCommenter() {
        Settings settings = LoadAndSaveJson.load("savedShares",Settings.class);
        if (settings == null) settings = new Settings();
        scraper = new Scraper();
        new GUI(scraper, settings);
    }

    public static void main(String[] args) {
        new HotCommenter();
    }
}
