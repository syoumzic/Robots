package utils;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Сущность для работы с окнами
 */
public class WindowsManager{
    private Data storage = new Data();

    /**
     * Сохраняет состояние компонента в хранилище
     */
    public void setWindow(String name, Component component){
        Data windowData = new Data();
        windowData.setInt("x", component.getX());
        windowData.setInt("y", component.getY());
        windowData.setInt("width", component.getWidth());
        windowData.setInt("height", component.getHeight());

        if(component instanceof JInternalFrame jInternalFrame)
            windowData.setBoolean("isIcon", jInternalFrame.isIcon());

        storage.setData(name, windowData);
    }

    /**
     * Восстанавливает состояние компонента из хранилища
     *
     * @throws NoSuchElementException в объекте данных нет необходимого поля
     */
    public void loadWindow(String name, Component component) throws NoSuchElementException{
        Data windowData = storage.getData(name);

        component.setLocation(
                windowData.getInt("x"),
                windowData.getInt("y"));

        component.setSize(
                windowData.getInt("width"),
                windowData.getInt("height"));

        if(component instanceof JInternalFrame jInternalFrame) {
            try {
                jInternalFrame.setIcon(windowData.getBoolean("isIcon"));
            } catch (PropertyVetoException e) {
                //ignore
            }
        }
    }

    public Data getStorage() {
        return storage;
    }

    public void setStorage(Data storage) {
        this.storage = storage;
    }
}
