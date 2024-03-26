package utils;

import java.io.*;
import java.util.Map;

/**
 * Класс для сохранения и восстановления из памяти объекта Data
 */
public class DataManager {
    final String path = System.getProperty("user.home") + "/windowsPreferences.txt";

    /**
     * Сохраняет в файл текущий объект Data
     */
    public void saveData(Data data) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try(PrintWriter writer = new PrintWriter(new FileWriter(path))){
            for (Map.Entry<String, String> entry: data.entrySet()) {
                String line = entry.getKey() + ":" + entry.getValue();
                writer.println(line);
            }
        }
    }

    /**
     * Загружает из файла объект Data
     */
    public Data loadData() throws IOException {
        Data data = new Data();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int pointsIndex = line.indexOf(':');

                if(pointsIndex == -1) throw new IOException();

                data.setString(line.substring(0, pointsIndex), line.substring(pointsIndex+1));
            }
        }
        return data;
    }
}
