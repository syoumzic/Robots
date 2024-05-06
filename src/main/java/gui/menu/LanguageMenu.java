package gui.menu;

import gui.MainApplicationFrame;
import utils.Localizable;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Вкладка выбора языка интерфейса
 */
public class LanguageMenu extends JMenu implements Localizable{
    public LanguageMenu(MainApplicationFrame mainApplicationFrame){
        super("Язык");
        setMnemonic(KeyEvent.VK_A);

        {
            JMenuItem russian = new JMenuItem("Русский");
            russian.addActionListener((event) -> {
                mainApplicationFrame.setWindowsLocale(Locale.of("ru"));
            });
            add(russian);
        }

        {
            JMenuItem english = new JMenuItem("Транслит");
            english.addActionListener((event) -> {
                mainApplicationFrame.setWindowsLocale(Locale.of("en"));
            });
            add(english);
        }
    }


    @Override
    public String getWindowName() {
        return "languageMenu";
    }

    @Override
    public void onUpdateLocale(ResourceBundle bundle) {
        setText(bundle.getString("title"));
        getItem(0).setText(bundle.getString("russianLanguage"));
        getItem(1).setText(bundle.getString("transliterateLanguage"));
    }
}