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
    private final Data storage = new Data();
    final String path = System.getProperty("user.home") + "/windowsPreferences.txt";

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
     * Востанавливает состояние компонента из хранилища
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

    /**
     * Сохраняет в файл состояние внутренних окон
     */
    public void saveWindowsPreferences() throws IOException {
        try(PrintWriter writer = new PrintWriter(new FileWriter(path))){
            for (Map.Entry<String, String> entry: storage.entrySet()) {
                String line = entry.getKey() + ":" + entry.getValue();
                writer.println(line);
            }
        }
    }

    /**
     * Восстанавливает из файла состяние внутренних окон
     */
    public void loadWindowPreferences() throws IOException {
        for(String line: Files.readAllLines(Paths.get(path))){
            int pointsIndex = line.indexOf(':');
            if(pointsIndex == -1) throw new IOException();
            storage.setString(line.substring(0, pointsIndex), line.substring(pointsIndex+1));
        }
    }
}
