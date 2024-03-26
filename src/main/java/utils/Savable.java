package utils;

import java.util.NoSuchElementException;

/**
 * Интерфейс объектов, состояние которых можно сохранять и восстанавливать
 */
public interface Savable {
    /**
     * Сохраняет своё состояние в переменную Data
     */
    void saveState(Data windowsState);

    /**
     * Восстанавливает своё состояние по переменной Data
     *
     * @throws NoSuchElementException в данных нет необходимого поля
     */
    void loadState(Data windowsState) throws NoSuchElementException;
}
