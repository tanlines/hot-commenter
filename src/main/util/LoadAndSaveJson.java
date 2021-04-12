package main.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
public class LoadAndSaveJson {
    public static <T> T load(String fileName,Type typeOfT) {
        File file = new File(fileName);
        if (file.exists()) {
            try {
                FileReader fr = new FileReader(file);
                T loadedFile = new Gson().fromJson(new JsonReader(fr), typeOfT);
                fr.close();
                return loadedFile;
            } catch (IOException e) {
                e.printStackTrace();
                log.error("Load file", e);
            }
        }
        return null;
    }

    public static  void save(String fileName, Object obj) {
        File file = new File(fileName);
        try {
            FileWriter fw = new FileWriter(file.getPath());
            new Gson().toJson(obj,fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            log.error("Save file", e);
        }
    }
}
