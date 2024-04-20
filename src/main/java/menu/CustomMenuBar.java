package menu;

import javax.swing.*;

/**
 * Заданное меню
 */
public class CustomMenuBar extends JMenuBar {
    public CustomMenuBar(JFrame mainFrame){
        add(new LookAndFeelMenu(mainFrame));
        add(new TestMenu());
        add(new ActionMenu(mainFrame));
    }
}
