package gui;

import utils.Data;
import utils.DataManager;
import utils.Savable;
import log.Logger;
import utils.WindowsManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.NoSuchElementException;

public class MainApplicationFrame extends JFrame implements Savable
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final DataManager dataManager = new DataManager();

    public MainApplicationFrame() {
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);

        WindowsManager windowsManager = new WindowsManager();

        addWindow(new LogWindow(windowsManager));
        addWindow(new GameWindow(windowsManager));

        try {
            Data windowsState = dataManager.loadData();
            loadState(windowsState);
        }catch(NoSuchElementException | IOException e){
            //ignore
        }

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });

        pack();
        setVisible(true);
        setExtendedState(Frame.MAXIMIZED_BOTH);
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
        
        {
            JMenuItem systemLookAndFeel = new JMenuItem("Системная схема", KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }

        {
            JMenuItem crossplatformLookAndFeel = new JMenuItem("Универсальная схема", KeyEvent.VK_S);
            crossplatformLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                this.invalidate();
            });
            lookAndFeelMenu.add(crossplatformLookAndFeel);
        }

        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
                "Тестовые команды");
        
        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            testMenu.add(addLogMessageItem);
        }

        JMenu actionMenu = new JMenu("Действия");
        actionMenu.setMnemonic(KeyEvent.VK_A);
        actionMenu.getAccessibleContext().setAccessibleDescription(
                "Действия с приложением");

        {
            JMenuItem exitItem = new JMenuItem("Выход", KeyEvent.VK_E);
            exitItem.addActionListener((event) -> {
                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            });
            actionMenu.add(exitItem);
        }

        menuBar.add(lookAndFeelMenu);
        menuBar.add(testMenu);
        menuBar.add(actionMenu);

        return menuBar;
    }
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // игнорируется
        }
    }

    /**
     * Метод подтверждения выхода
     */
    private void confirmExit() {
        Object[] options1 = {"Да", "Нет"};

        int option = JOptionPane.showOptionDialog(null,
                "Вы действительно хотите выйти?",
                "Подтверждение выхода",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options1,
                null);

        if (option == JOptionPane.YES_OPTION) {
            exitAction();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            dispose();
        }
    }

    private void exitAction(){
        Data windowsState = new Data();
        saveState(windowsState);
        try {
            dataManager.saveData(windowsState);
        }catch(IOException e){
            //ignore
        }
    }

    /**
     * Сохраняет своё состояние
     */
    @Override
    public void saveState(Data windowsState) {
        for(JInternalFrame component: desktopPane.getAllFrames()){
            if(component instanceof Savable savable){
                savable.saveState(windowsState);
            }
        }
    }

    /**
     * Восстанавливает своё состояние
     */
    @Override
    public void loadState(Data windowsState) throws NoSuchElementException {
        for(JInternalFrame component: desktopPane.getAllFrames()){
            if(component instanceof Savable savable){
                savable.loadState(windowsState);
            }
        }
    }
}
