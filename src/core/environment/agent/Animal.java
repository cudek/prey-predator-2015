package core.environment.agent;

import java.util.ArrayList;
import java.util.List;

import core.deciding.IAction;
import core.environment.Meadow;
import core.environment.MovementAction;
import core.environment.Point;
import core.simulation.Agent;
import core.simulation.Environment;

/**
 * 
 * @author cudek
 */
public abstract class Animal extends Agent {
    private Point point;
    private int lineOfSight;

    public Animal(int lineOfSight) {
        this.lineOfSight = lineOfSight;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getLineOfSight() {
        return lineOfSight;
    }

    public abstract boolean isPrey();

    @Override
    public List<IAction> generateActionList(Environment env) {
        List<IAction> actionList = new ArrayList<IAction>();
        Meadow meadow = (Meadow) env;
        Point destination;
        if (meadow.canEnterTile(this, destination = new Point(point.x, point.y + 1)))
            actionList.add(new MovementAction(this, destination));
        if (meadow.canEnterTile(this, destination = new Point(point.x, point.y - 1)))
            actionList.add(new MovementAction(this, destination));
        if (meadow.canEnterTile(this, destination = new Point(point.x + 1, point.y)))
            actionList.add(new MovementAction(this, destination));
        if (meadow.canEnterTile(this, destination = new Point(point.x - 1, point.y)))
            actionList.add(new MovementAction(this, destination));
        /*
         * Selector zakłada, że zwrócona lista nie będzie pusta, jeżeli nie
         * możemy ruszyć w żadnym kierunku, bo jesteśmy zablokowani, zwracamy
         * jednoelementową listę, z akcją ruchu na pole na którym obecnie
         * znajduje się agent
         */
        if (actionList.isEmpty())
            actionList.add(new MovementAction(this, getPoint()));
        return actionList;
    }

    public abstract Animal copy();
}
