package gui.menu;

import utils.Localizable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

/**
 * Вкладка действия. Содержит список действий, которые можно сделать с окном
 */
public class ActionMenu extends JMenu implements Localizable {
    public ActionMenu(JFrame mainFrame){
        super("Действия");
        setMnemonic(KeyEvent.VK_A);
        {
            JMenuItem exitItem = new JMenuItem("Выход", KeyEvent.VK_E);
            exitItem.addActionListener((event) -> {
                mainFrame.dispatchEvent(new WindowEvent(mainFrame, WindowEvent.WINDOW_CLOSING));
            });
            add(exitItem);
        }
    }

    @Override
    public String getWindowName() {
        return "actionMenu";
    }

    @Override
    public void onUpdateLocale(ResourceBundle bundle) {
        setText(bundle.getString("title"));
        getItem(0).setText(bundle.getString("exitText"));
    }
}
