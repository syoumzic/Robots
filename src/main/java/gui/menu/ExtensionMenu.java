package gui.menu;

import log.Logger;
import utils.ExtensionManager;
import utils.Localizable;

import javax.swing.*;

import java.util.List;
import java.util.ResourceBundle;

//TODO добавить локащизацию и javadoc
public class ExtensionMenu extends JMenu implements Localizable{
    public ExtensionMenu(ExtensionManager extensionManager){
        super("Расширения");

        List<String> extensionNames = extensionManager.getExtensionNames();

        if(extensionNames.isEmpty()) {
            Logger.debug("не найдено ни одного расширения");
            return;
        }

        for (String extensionName : extensionNames){
            JMenuItem systemLookAndFeel = new JMenuItem(extensionName);
            systemLookAndFeel.addActionListener((event) -> {
                try{
                    extensionManager.loadExtension(extensionName);
                    //TODO другая ошибка
                }catch(Exception e){
                    Logger.error(e.getMessage());
                }
            });
            add(systemLookAndFeel);
        }
    }


    @Override
    public String getWindowName() {
        return "ExtensionMenu";
    }

    @Override
    public void onUpdateLocale(ResourceBundle bundle) {
        setText(bundle.getString("title"));
        getItem(0).setText(bundle.getString("russianLanguage"));
        getItem(1).setText(bundle.getString("transliterateLanguage"));
    }
}