package gui.menu;

import gui.MainApplicationFrame;

import javax.swing.*;

/**
 * Заданное меню
 */
public class CustomMenuBar extends JMenuBar {
    public CustomMenuBar(MainApplicationFrame mainFrame){
        add(new LookAndFeelMenu(mainFrame));
        add(new TestMenu());
        add(new ActionMenu(mainFrame));
        add(new LanguageMenu(mainFrame));
    }
}
