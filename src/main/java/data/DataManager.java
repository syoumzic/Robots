package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Реализация сохранения данных
 */
public class DataManager implements Data{

    private final Map<String, String> storage = new HashMap<>();

    /**
     * Сохранить переменную int по имени
     */
    @Override
    public void setInt(String name, int value) {
        storage.put(name, Integer.toString(value));
    }

    /**
     * Загрузить переменную типа int по имени
     */
    @Override
    public int getInt(String name) throws NoSuchElementException{
        try {
            return Integer.parseInt(storage.get(name));
        }catch(NumberFormatException e){
            throw new NoSuchElementException();
        }
    }

    /**
     * Сохранить переменную boolean по имени
     */
    @Override
    public void setBoolean(String name, boolean value) {
        storage.put(name, Boolean.toString(value));
    }

    /**
     * Загрузить переменную типа boolean по имени
     */
    @Override
    public boolean getBoolean(String name) throws NoSuchElementException {
        try {
            return Boolean.parseBoolean(storage.get(name));
        }catch(NumberFormatException e){
            throw new NoSuchElementException();
        }
    }
    /**
     * Сохранить переменную типа Data по имени
     */
    @Override
    public void setData(String name, Data value) {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (Map.Entry<String, String> entry : value.entrySet()){
            stringBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append(" ");
        }
        stringBuilder.append("}");
        storage.put(name, stringBuilder.toString());
    }

    /**
     * Загразить переменную типа Data по имени
     */
    @Override
    public Data getData(String name) throws NoSuchElementException{
        String rawData = storage.get(name);
        Data outData = new DataManager();

        Pattern bracketRegex = Pattern.compile("\\{(.*)}");
        Matcher bracketMatcher = bracketRegex.matcher(rawData);

        if(!bracketMatcher.find()) throw new NoSuchElementException();

        for(String line : bracketMatcher.group(1).split(" ")) {
            Pattern sepRegex = Pattern.compile("(.*?):(.*)");
            Matcher sepMatcher = sepRegex.matcher(line);
            if (!sepMatcher.find()) throw new NoSuchElementException();

            outData.setString(sepMatcher.group(1), sepMatcher.group(2));
        }

        return outData;
    }

    /**
     * Сохранить переменную типа String по имени
     */
    @Override
    public void setString(String name, String value) {
        storage.put(name, value);
    }

    /**
     * Загразить переменную типа String по имени
     */
    @Override
    public String getString(String name) {
        return storage.get(name);
    }

    /**
     * Сохраняет в файл текущий объект
     */
    @Override
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
    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return storage.entrySet();
    }

    /**
     * Загружает из файла текущий объект
     */
    @Override
    public void loadFromFile(String path) throws IOException{
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Pattern pointRegex = Pattern.compile("(.*?):(.*)");
                Matcher matcher = pointRegex.matcher(line);
                if(matcher.find()){
                    storage.put(matcher.group(1), matcher.group(2));
                }
                else {
                    throw new IOException(String.format("неправильная строка: '%s'", line));
                }
            }
        }
    }


}
