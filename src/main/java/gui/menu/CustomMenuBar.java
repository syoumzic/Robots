package gui.menu;

import gui.MainApplicationFrame;
import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

public class CustomMenuBar extends JMenuBar {
    public CustomMenuBar(JFrame mainFrame){
        add(new LookAndFeelMenu(mainFrame));
        add(new TestMenu());
        add(new ActionMenu(mainFrame));
    }
}
