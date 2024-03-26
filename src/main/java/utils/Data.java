package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Реализация объекта данных
 */
public class Data{

    private final Map<String, String> storage = new HashMap<>();

    /**
     * Сохранить переменную int по имени
     */
    public void setInt(String name, int value) {
        storage.put(name, Integer.toString(value));
    }

    /**
     * Загрузить переменную типа int по имени
     */
    public int getInt(String name) throws NoSuchElementException {
        try {
            return Integer.parseInt(storage.get(name));
        } catch (NumberFormatException e) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Сохранить переменную boolean по имени
     */
    public void setBoolean(String name, boolean value) {
        storage.put(name, Boolean.toString(value));
    }

    /**
     * Загрузить переменную типа boolean по имени
     */
    public boolean getBoolean(String name) throws NoSuchElementException {
        try {
            return Boolean.parseBoolean(storage.get(name));
        } catch (NumberFormatException e) {
            throw new NoSuchElementException();
        }
    }

    /**
     * Сохранить переменную типа Data по имени
     */
    public void setData(String name, Data value) {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (Map.Entry<String, String> entry : value.entrySet()) {
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append(" ");
        }
        stringBuilder.append("}");
        storage.put(name, stringBuilder.toString());
    }

    /**
     * Загразить переменную типа Data по имени
     */
    public Data getData(String name) throws NoSuchElementException {
        String rawData = storage.get(name);
        Data outData = new Data();

        int startIndex = rawData.indexOf('{');
        int endIndex = rawData.lastIndexOf('}');

        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex)
            throw new NoSuchElementException();

        String dataContent = rawData.substring(startIndex + 1, endIndex);
        String[] lines = dataContent.split(" ");

        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length != 2)
                throw new NoSuchElementException();

            outData.setString(parts[0], parts[1]);
        }

        return outData;
    }

    /**
     * Сохранить переменную типа String по имени
     */
    public void setString(String name, String value) {
        storage.put(name, value);
    }

    /**
     * Загразить переменную типа String по имени
     */
    public String getString(String name) {
        return storage.get(name);
    }

    /**
     * Сохраняет в файл текущий объект
     */
    public void saveToFile(String path) throws IOException {
        try (PrintWriter writer = new PrintWriter(path)) {
            for (Map.Entry<String, String> entry : storage.entrySet()) {
                String line = entry.getKey() + ":" + entry.getValue();
                writer.println(line);
            }
        }
    }

    /**
     * Предоставляет итерируемый список
     */
    public Set<Map.Entry<String, String>> entrySet() {
        return storage.entrySet();
    }

    /**
     * Загружает из файла текущий объект
     */
    public void loadFromFile(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int pointsIndex = line.indexOf(':');

                if(pointsIndex == -1) throw new IOException();

                storage.put(line.substring(0, pointsIndex), line.substring(pointsIndex+1));
            }
        }
    }
}