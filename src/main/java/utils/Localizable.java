package utils;

import java.util.ResourceBundle;

/**
 * Интерфейс объектов, которые можно локализировать
 */
public interface Localizable {
    /**
     * Получить имя сущности
     */
    String getWindowName();

    /**
     * Перевести сущность по заданному ResourceBundle
     */
    void onUpdateLocale(ResourceBundle bundle);
}
