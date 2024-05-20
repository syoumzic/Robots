package gui.menu;

import log.Logger;
import utils.Localizable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ResourceBundle;

/**
 * Вкладка тесты. Содержит различные действия, связанные с отладкой программы
 */
public class TestMenu extends JMenu implements Localizable {
    public TestMenu(){
        super("Тесты");
        setMnemonic(KeyEvent.VK_T);

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            add(addLogMessageItem);
        }
    }

    @Override
    public String getWindowName() {
        return "testMenu";
    }

    @Override
    public void onUpdateLocale(ResourceBundle bundle) {
        setText(bundle.getString("title"));
        getItem(0).setText(bundle.getString("testMessage"));
    }
}
