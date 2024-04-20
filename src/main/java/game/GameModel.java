package game;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Модель управления роботом
 */
public class GameModel {
    private volatile double robotPositionX = 100;
    private volatile double robotPositionY = 100;
    private volatile double robotDirection = 0;

    private volatile int targetPositionX = 100;
    private volatile int targetPositionY = 100;

    private final double maxVelocity = 0.1;
    private final double maxAngularVelocity = 0.001;
    private final double minTargetDistance = 0.5;

    private final double duration = 10;

    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public GameModel(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
        sendPosition(robotPositionX, robotPositionY);
    }

    /**
     * Рассчитывает расстояние от точки (x1, y1) до точки (x2, y2)
     */
    private double distance(double x1, double y1, double x2, double y2) {
        double diffX = x1 - x2;
        double diffY = y1 - y2;
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    /**
     * Рассчитывает угол прямой от точки (fromX, fromY) до точки (toX, toY)
     */
    private double angleTo(double fromX, double fromY, double toX, double toY) {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return Math.atan2(diffY, diffX);
    }

    /**
     * Обработка движения робота
     */
    public void onModelUpdateEvent() {
        double distance = distance(targetPositionX, targetPositionY, robotPositionX, robotPositionY);
        if (distance < minTargetDistance) {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = angleTo(robotPositionX, robotPositionY, targetPositionX, targetPositionY);
        double angularVelocity = 0;

        double dist1 = robotDirection - angleToTarget + Math.TAU;
        double dist2 = Math.abs(robotDirection - angleToTarget);
        double dist3 = -robotDirection + angleToTarget + Math.TAU;

        double minDist = Math.min(dist1, Math.min(dist2, dist3));

        if(dist1 < dist2 && dist1 < dist3){
            angularVelocity = -maxAngularVelocity;
        }
        else if(dist3 < dist1 && dist3 < dist2){
            angularVelocity = maxAngularVelocity;
        }
        else if(robotDirection > angleToTarget){
            angularVelocity = -maxAngularVelocity;
        }
        else if(robotDirection < angleToTarget){
            angularVelocity = maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity);
    }

    /**
     * Ограничение значения переменной value от min до max
     */
    private double applyLimits(double value, double min, double max) {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    /**
     * Двигать роота с заданной скоростью и вращать с заданным углом поворота
     */
    private void moveRobot(double velocity, double angularVelocity) {
        velocity = applyLimits(velocity, 0, maxVelocity);
        angularVelocity = applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
        double newX = robotPositionX + velocity / angularVelocity *
                (Math.sin(robotDirection + angularVelocity * duration) -
                        Math.sin(robotDirection));
        if (!Double.isFinite(newX)) {
            newX = robotPositionX + velocity * duration * Math.cos(robotDirection);
        }
        double newY = robotPositionY - velocity / angularVelocity *
                (Math.cos(robotDirection + angularVelocity * duration) -
                        Math.cos(robotDirection));
        if (!Double.isFinite(newY)) {
            newY = robotPositionY + velocity * duration * Math.sin(robotDirection);
        }
        support.firePropertyChange("changePosition", new double[]{robotPositionX, robotPositionY}, new double[]{newX, newY});
        robotPositionX = newX;
        robotPositionY = newY;
        sendPosition(newX, newY);
        double newDirection = angleNormalize(robotDirection + angularVelocity * duration);
        robotDirection = newDirection;
    }

    /**
     * Ограничение значения angle в диапазоне от -pi до pi
     */
    private double angleNormalize(double angle){
        if(angle > Math.PI)
            return angle - Math.TAU;
        else if(angle < -Math.PI)
            return angle + Math.TAU;
        return angle;
    }

    /**
     * Разослать всем подписанным слушателям новые координаты
     */
    private void sendPosition(double x, double y){
        support.firePropertyChange("changePosition", null, new double[]{x, y});
    }

    /**
     * Установка позиции цели
     */
    public void setTargetPosition(Point p) {
        targetPositionX = p.x;
        targetPositionY = p.y;
    }

    public double getRobotPositionX() {
        return robotPositionX;
    }

    public double getRobotPositionY() {
        return robotPositionY;
    }

    public double getRobotDirection() {
        return robotDirection;
    }

    public int getTargetPositionX() {
        return targetPositionX;
    }

    public int getTargetPositionY() {
        return targetPositionY;
    }
}
