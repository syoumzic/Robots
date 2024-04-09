package menu;

import log.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class TestMenu extends JMenu {
    public TestMenu(){
        super("Тесты");
        setMnemonic(KeyEvent.VK_T);
        getAccessibleContext().setAccessibleDescription("Тестовые команды");

        {
            JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug("Новая строка");
            });
            add(addLogMessageItem);
        }
    }
}
