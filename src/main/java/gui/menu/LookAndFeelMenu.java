package gui.menu;

import utils.Localizable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

/**
 * Меню настройки темы окна. Содержит различные темы
 */
public class LookAndFeelMenu extends JMenu implements Localizable {
    LookAndFeelMenu(JFrame mainFrame){
        super("Режим отображения");
        setMnemonic(KeyEvent.VK_V);
        
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

    @Override
    public String getWindowName() {
        return "lookAndFeelMenu";
    }

    @Override
    public void onUpdateLocale(ResourceBundle bundle) {
        setText(bundle.getString("title"));
    }
}
