package game;

import java.util.Timer;
import java.util.TimerTask;

public class GameController {
    private GameEngine engine;
    private GameVisualizer visualizer;
    private Timer timer;

    GameController(GameVisualizer visualizer, GameEngine engine) {
        this.visualizer = visualizer;
        this.engine = engine;

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
                engine.onModelUpdateEvent();
            }
        }, 0, 10);
    }
}
