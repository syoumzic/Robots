package gui;

import data.Data;
import data.DataManager;

import javax.swing.*;
import java.io.IOException;
import java.util.NoSuchElementException;

public class RobotsProgram
{
    public static void main(String[] args) {
      try {
        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
      } catch (Exception e) {
        e.printStackTrace();
      }
      SwingUtilities.invokeLater(() -> {
        MainApplicationFrame frame = new MainApplicationFrame();
          try {
            Data windowsPreferences = new DataManager();
            windowsPreferences.loadFromFile("C:\\Users\\Noname\\windowsPreferences.txt");
            frame.loadState(windowsPreferences);
            System.out.print("файл удалось открыть!");
          } catch (IOException | NoSuchElementException e) {
            e.printStackTrace();
            frame.setDefault();
            System.out.print("файл открыть не удалось!");
          }
      });
    }
}
