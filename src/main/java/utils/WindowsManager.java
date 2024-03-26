package utils;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.NoSuchElementException;

/**
 * Сущность извлекающая состояние JInternalFrame в объект данных (класс Data)
 */
public class WindowsManager {

    /**
     * Возвращает объект данных исходя из состояния компонента
     */
    public Data toData(JInternalFrame component) {
        Data data = new Data();
        data.setInt("x", component.getX());
        data.setInt("y", component.getY());
        data.setInt("width", component.getWidth());
        data.setInt("height", component.getHeight());

        data.setBoolean("isIcon", component.isIcon());

        return data;
    }

    /**
     * Востанавливает состояние компонента по объекту данных
     *
     * @throws NoSuchElementException выкидывается, если в объекте данных нет необходимого поля
     */
    public void loadComponent(JInternalFrame component, Data data) throws NoSuchElementException {
        component.setLocation(
                data.getInt("x"),
                data.getInt("y"));

        component.setSize(
                data.getInt("width"),
                data.getInt("height"));

        try {
            component.setIcon(data.getBoolean("isIcon"));
        } catch (PropertyVetoException e) {
            //ignore
        }
    }
}
