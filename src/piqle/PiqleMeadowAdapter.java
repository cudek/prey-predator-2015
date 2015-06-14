package piqle;

import java.util.ArrayList;
import java.util.List;

import core.environment.Meadow;
import core.environment.MovementAction;
import core.environment.Point;
import core.environment.agent.Predator;
import environment.ActionList;
import environment.IAction;
import environment.IEnvironmentSingle;
import environment.IState;

public class PiqleMeadowAdapter implements IEnvironmentSingle {

    private Meadow meadow;

    public PiqleMeadowAdapter(Meadow meadow) {
        this.meadow = meadow;
    }

    @Override
    public ActionList getActionList(IState s) {

        PiqleState state = (PiqleState) s;
        List<MovementAction> actionList = new ArrayList<>();
        Predator predator = state.getPredator();
        Point predatorLocation = predator.getPoint();

        Point destination;
        if (meadow.canEnterTile(state.getPredator(),
                destination = new Point(predatorLocation.x, predatorLocation.y + 1)))
            actionList.add(new MovementAction(predator, destination));
        if (meadow.canEnterTile(state.getPredator(),
                destination = new Point(predatorLocation.x, predatorLocation.y - 1)))
            actionList.add(new MovementAction(predator, destination));
        if (meadow.canEnterTile(state.getPredator(),
                destination = new Point(predatorLocation.x + 1, predatorLocation.y)))
            actionList.add(new MovementAction(predator, destination));
        if (meadow.canEnterTile(state.getPredator(),
                destination = new Point(predatorLocation.x - 1, predatorLocation.y)))
            actionList.add(new MovementAction(predator, destination));
        /*
         * Selector zakłada, że zwrócona lista nie będzie pusta, jeżeli nie
         * możemy ruszyć w żadnym kierunku, bo jesteśmy zablokowani, zwracamy
         * jednoelementową listę, z akcją ruchu na pole na którym obecnie
         * znajduje się agent
         */
        if (actionList.isEmpty()) {
            actionList.add(new MovementAction(predator, predatorLocation));
        }
        
        List<IState> states = new ArrayList<>(4);
        for (MovementAction action : actionList) {
            states.add(new PiqleState(this, predator, null))
        }
    }

    @Override
    public IState successorState(IState s, IAction a) {
        return null;
    }

    @Override
    public double getReward(IState s1, IState s2, IAction a) {
        return 0;
    }

    @Override
    public boolean isFinal(IState s) {
        return false;
    }

    @Override
    public int whoWins(IState s) {
        return 0;
    }

    @Override
    public IState defaultInitialState() {
        return null;
    }

}
