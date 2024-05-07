package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import log.Logger;
import utils.FileManager;
import utils.Savable;
import utils.WindowsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import gui.menu.CustomMenuBar;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import utils.Localizable;

public class MainApplicationFrame extends JFrame implements Localizable {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final WindowsManager windowsManager = new WindowsManager();
    private final FileManager fileManager = new FileManager();
    private ResourceBundle bundle;

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);

        try {
            windowsManager.setStorage(fileManager.loadWindowPreference());
        }catch(IOException e){
            Logger.error(e.toString());
        }

        LogWindow logWindow = new LogWindow();
        addWindow(logWindow);

        RobotPositionWindow robotPositionWindow = new RobotPositionWindow();
        addWindow(robotPositionWindow);

        GameWindow gameWindow = new GameWindow(robotPositionWindow);
        addWindow(gameWindow);

        loadWindowStates();

        setJMenuBar(new CustomMenuBar(this));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });

        setWindowsLocale(Locale.of("en"));

        pack();
        setVisible(true);
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
    
    protected void addWindow(JInternalFrame frame){
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    /**
     * Метод подтверждения выхода
     */
    private void confirmExit() {
        Object[] options1 = {bundle.getString("yesOption"), bundle.getString("noOption")};

        int option = JOptionPane.showOptionDialog(null,
                bundle.getString("confirmExit"),
                bundle.getString("confirmationExit"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options1,
                null);

        if (option == JOptionPane.YES_OPTION) {
            saveState();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dispose();
        }
    }

    /**
     * Устанавливает локаль локализируемым окнам
     */
    public void setWindowsLocale(Locale locale){
        for (Component component : desktopPane.getComponents()) {
            try {
                if (component instanceof Localizable localizable) {
                    localizable.onUpdateLocale(ResourceBundle.getBundle(localizable.getWindowName(), locale));
                } else if (component instanceof JInternalFrame.JDesktopIcon icon) {
                    if (icon.getInternalFrame() instanceof Localizable localizable) {
                        localizable.onUpdateLocale(ResourceBundle.getBundle(localizable.getWindowName(), locale));
                    }
                }
            }catch (MissingResourceException e){
                Logger.debug(e.toString());
            }
        }

        for(Component component : getJMenuBar().getComponents()){
            if(component instanceof Localizable localizable){
                localizable.onUpdateLocale(ResourceBundle.getBundle(localizable.getWindowName(), locale));
            }
        }

        onUpdateLocale(ResourceBundle.getBundle(this.getWindowName(), locale));
    }

    /**
     * Сохраняет состояние всех внутренних окон
     */
    public void saveState() {
        for (Component component : desktopPane.getComponents()) {
            if (component instanceof Savable savable) {
                windowsManager.setWindow(savable.getWindowName(), component);
            }
            else if (component instanceof JInternalFrame.JDesktopIcon icon) {
                if (icon.getInternalFrame() instanceof Savable savable)
                    windowsManager.setWindow(savable.getWindowName(), component);
            }
            try {
                fileManager.saveWindowPreferences(windowsManager.getStorage());
            }catch (IOException e){
                Logger.error(e.toString());
            }
        }
    }

    /**
     * Восстанавливает состояние всех внутренних окон
     */
    public void loadWindowStates(){
        try {
            for (Component component : desktopPane.getComponents()) {
                if (component instanceof Savable savable) {
                    windowsManager.loadWindow(savable.getWindowName(), component);
                } else if (component instanceof JInternalFrame.JDesktopIcon icon) {
                    if (icon.getInternalFrame() instanceof Savable savable)
                        windowsManager.loadWindow(savable.getWindowName(), component);
                }
            }
        }catch (NoSuchElementException e){
            Logger.error("не удалось загрузить окна");
        }
    }

    @Override
    public String getWindowName() {
        return "mainWindow";
    }

    @Override
    public void onUpdateLocale(ResourceBundle bundle) {
        this.bundle = bundle;
    }
}
