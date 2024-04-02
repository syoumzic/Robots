package utils;

import java.util.NoSuchElementException;

/**
 * Интерфейс объектов, состояние которых можно сохранять и восстанавливать
 */
public interface Savable {
    /**
     * Сохраняет своё состояние
     */
    void saveState();

    /**
     * Восстанавливает своё состояние
     *
     * @throws NoSuchElementException в данных нет необходимого поля
     */
    void loadState() throws NoSuchElementException;
}
