package main;

import lombok.Data;

import java.util.HashMap;

@Data
public class Settings {
    private HashMap<String, Integer> stockPages = new HashMap<>();
}
