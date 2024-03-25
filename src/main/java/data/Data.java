package data;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Класс для хранения абстракных данных
 */
public interface Data{
    /**
     * Сохранить переменную int по имени
     */
    void setInt(String name, int value);

    /**
     * Загрузить переменную типа int по имени
     */
    int getInt(String name);

    /**
     * Сохранить переменную boolean по имени
     */
    void setBoolean(String name, boolean value);

    /**
     * Загрузить переменную типа boolean по имени
     */
    boolean getBoolean(String name);

    /**
     * Сохранить переменную типа Data по имени
     */
    void setData(String name, Data value);

    /**
     * Загразить переменную типа Data по имени
     */
    Data getData(String name);

    /**
     * Сохранить переменную типа String по имени
     */
    void setString(String name, String value);

    /**
     * Загразить переменную типа String по имени
     */
    String getString(String name);

    /**
     * Сохраняет в файл текущий объект
     */
    void saveToFile(String path) throws IOException;

    /**
     * Предоставляет итерируемый список
     */
    Set<Map.Entry<String, String>> entrySet();

    /**
     * Загружает из файла текущий объект
     */
    void loadFromFile(String path) throws IOException;
}
