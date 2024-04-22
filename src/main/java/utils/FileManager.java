package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Класс для работы с файлом состояния окон: с его загрузкой и воспроизведением
 */
public class FileManager {
    private final String path = System.getProperty("user.home") + "/windowsPreferences.txt";

    /**
     * Сохраняет в файл состояние окон
     */
    public void saveWindowPreferences(final Data storage) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (Map.Entry<String, String> entry : storage.entrySet()) {
                String line = entry.getKey() + ":" + entry.getValue();
                writer.println(line);
            }
        }
    }

    /**
     * Восстанавливает из файла состояние окон
     */
    public Data loadWindowPreference() throws IOException {
        Data storage = new Data();

        for (String line : Files.readAllLines(Paths.get(path))) {
            int pointsIndex = line.indexOf(':');
            if (pointsIndex == -1)
                throw new IOException();
            storage.setString(line.substring(0, pointsIndex), line.substring(pointsIndex + 1));
        }

        return storage;
    }
}