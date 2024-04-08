package gui.menu;

import gui.MainApplicationFrame;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class ActionMenu extends JMenu {
    public ActionMenu(JFrame mainFrame){
        super("Действия");
        setMnemonic(KeyEvent.VK_A);
        getAccessibleContext().setAccessibleDescription("Действия с приложением");

        {
            JMenuItem exitItem = new JMenuItem("Выход", KeyEvent.VK_E);
            exitItem.addActionListener((event) -> {
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            });
            add(exitItem);
        }
    }
}
