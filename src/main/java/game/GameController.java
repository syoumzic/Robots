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
    private Timer timer;

    public GameController(GameWindow gameWindow) {
        timer = new Timer("events generator", true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(model != null) {
                    model.onModelUpdateEvent();
                }
            }
        }, 0, 50);

        gameWindow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.setTargetPosition(e.getPoint());
            }
        });
    }

    public void setModel(GameModel model) {
        this.model = model;
    }
}
