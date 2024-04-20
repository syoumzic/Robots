package game;

import gui.GameWindow;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Логика взаимодействия отображения и обновление состояния игры
 */
public class GameController {
    private GameModel model;
    private GameVisualizer visualizer;
    private Timer timer;

    public GameController(GameWindow gameWindow, GameVisualizer visualizer, GameModel model) {
        this.visualizer = visualizer;
        this.model = model;

        timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                visualizer.onRedrawEvent();
            }
        }, 0, 50);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                model.onModelUpdateEvent();
            }
        }, 0, 10);

        gameWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.setTargetPosition(e.getPoint());
                visualizer.repaint();
            }
        });
    }
}
