package utils;

import log.Logger;

import java.awt.*;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LocalizationManager {
    public void localize(Component component, Locale locale){
        if(component instanceof Localizable localizable){
            try {
                localizable.onUpdateLocale(ResourceBundle.getBundle("bundles/%s".formatted(localizable.getWindowName()), locale));
            }catch (MissingResourceException e){
                if(e.getKey().isEmpty()) {
                    Logger.error("ресурс '%s' не найден".formatted(e.getClassName()));
                }
                else{
                    Logger.error("значение ресурса '%s' не найден".formatted(e.getKey()));
                }
            }
        }
    }
}
