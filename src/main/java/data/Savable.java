package data;

/**
 * Интерфейс объектов, которые можно сохранять и воспроизводить
 */
public interface Savable {
    /**
     * Возвращает себя как объект данных
     */
    Data getData();

    /**
     * Загружает объект по данным
     */
    void loadState(Data data);

    /**
     * Устанавлеивает объект в значение по умолчанию
     */
    void setDefault();
}
