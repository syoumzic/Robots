package gui;

import game.CustomGameVisualizer;
import game.GameController;
import game.GameModel;
import game.GameVisualizer;

import java.awt.*;
import java.beans.PropertyChangeListener;

import utils.Localizable;
import utils.Savable;

import javax.swing.*;
import java.util.ResourceBundle;

public class GameWindow extends JInternalFrame implements Savable, Localizable {
    private GameController controller;
    private PropertyChangeListener gamePropertyChangeListener;

    public GameWindow(PropertyChangeListener gamePositionListener){
        super("Игровое поле", true, true, true, true);
        this.gamePropertyChangeListener = gamePositionListener;
        controller = new GameController(this);
        setVisualizerModel(new CustomGameVisualizer(), new GameModel());

        setLocation(350, 20);
        setSize(400, 400);
    }

    public void setVisualizerModel(GameVisualizer visualizer, GameModel model) {
        model.addPropertyChangeListener(visualizer);
        model.addPropertyChangeListener(gamePropertyChangeListener);
        model.notifyChange();

        Container container = getContentPane();
        container.removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(visualizer, BorderLayout.CENTER);
        container.add(panel);

        controller.setModel(model);
    }

    @Override
    public String getWindowName() {
        return "gameWindow";
    }

    @Override
    public void onUpdateLocale(ResourceBundle bundle) {
        setTitle(bundle.getString("title"));
    }
}
