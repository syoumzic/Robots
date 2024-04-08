package gui.menu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class LookAndFeelMenu extends JMenu {
    LookAndFeelMenu(JFrame mainFrame){
        super("Режим отображения");
        setMnemonic(KeyEvent.VK_V);
        getAccessibleContext().setAccessibleDescription("Управление режимом отображения приложения");
        
        UIManager.LookAndFeelInfo[] looks = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo look : looks){
            JMenuItem systemLookAndFeel = new JMenuItem(look.getName());
            systemLookAndFeel.addActionListener((event) -> {
                try
                {
                    UIManager.setLookAndFeel(look.getClassName());
                    SwingUtilities.updateComponentTreeUI(mainFrame);
                }
                catch (ClassNotFoundException | InstantiationException
                       | IllegalAccessException | UnsupportedLookAndFeelException e)
                {
                    // игнорируется
                }
                mainFrame.invalidate();
            });
            add(systemLookAndFeel);
        }
    }
}
