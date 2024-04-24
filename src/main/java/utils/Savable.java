package utils;

/**
 * Интерфейс объектов, состояние которых можно сохранять и восстанавливать
 */
public interface Savable {

    /**
     * Возвращает имя сохраняемого окна
     */
    String getWindowName();
}
