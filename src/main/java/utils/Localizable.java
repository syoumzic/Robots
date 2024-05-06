package utils;

import java.util.ResourceBundle;

/**
 * Интерфейс объектов, которые можно локализировать
 */
public interface Localizable {
    String getWindowName();
    void onUpdateLocale(ResourceBundle bundle);
}
