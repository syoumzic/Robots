package utils;

import game.GameModel;
import game.GameVisualizer;
import gui.GameWindow;
import log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Загружает расширения из папки resources/extensions
 */
public class ExtensionManager extends ClassLoader {
    private String path = String.valueOf(Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "extensions"));
    private GameWindow gameWindow;

    public ExtensionManager(GameWindow gameWindow){
        this.gameWindow = gameWindow;
    }

    /**
     * Возвращает списко имёт всех классов в папке
     */
    public List<String> getExtensionNames() {
        File directory = new File(path);

        File[] files = directory.listFiles();
        List<String> extensionNames = new ArrayList<>();

        if(files == null)
            return extensionNames;

        for(File f: files){
            if(f.getName().endsWith(".jar")){
                extensionNames.add(f.getName());
            }
        }

        return extensionNames;
    }

    /**
     *  загружает расширение по имени jar файла
     */
    public void loadExtension(String extensionName) {
        try {
            Class<?> visualizerClass = findClass(extensionName, "game.CustomGameVisualize");
            Class<?> modelClass = findClass(extensionName, "game.GameModel");
            Object rawVisualizer = visualizerClass.getConstructor().newInstance();
            Object rawModelClass = modelClass.getConstructor().newInstance();
            if (rawVisualizer instanceof GameVisualizer gameVisualizer && rawModelClass instanceof GameModel gameModel)
                gameWindow.setVisualizerModel(gameVisualizer, gameModel);
            else
                throw new ClassCastException();
        }catch (Exception e){
            Logger.error("не удалось загрузить класс");
        }
    }

    @Override
    public Class<?> findClass(String jarName, String name) {
        byte[] bytecode = getClassData(jarName, name);
        return defineClass(name, bytecode, 0, bytecode.length);
    }

    /**
     * Загрузить byte код класса из jar файла по имени класса
     */
    private byte[] getClassData(String jarName, String name) {
        try {
            name = name.replace('.', '/') + ".class";

            byte[] buffer = new byte[1024];
            JarFile jarFile = new JarFile(path + File.separator + jarName);
            Enumeration<?> enu = jarFile.entries();
            while (enu.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enu.nextElement();
                if (jarEntry.getName().equals(name)) {
                    InputStream inputStream = jarFile.getInputStream(jarEntry);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int read;
                    while ((read = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, read);
                    }
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
}
