package pl.edu.agh.preypredator.environment.handler;

import java.util.ListIterator;

import pl.edu.agh.preypredator.environment.MeadowEnvironment;
import pl.edu.agh.preypredator.environment.creature.Animal;
import pl.edu.agh.preypredator.environment.state.AnimalState;
import pl.edu.agh.preypredator.environment.state.WolfState;
import pl.edu.agh.preypredator.satistics.Statistics;
import environment.IAction;
import environment.IState;

public abstract class PredatorEnvironmentHandler extends AnimalEnvironmentHandler {

    public PredatorEnvironmentHandler(MeadowEnvironment environment, Statistics statistics) {
        super(environment, statistics);
    }

    @Override
    public AnimalState successorState(IState state, IAction action) {
        WolfState wolfPreviousState = (WolfState) state;
        WolfState wolfNextState = newState();

        wolfNextState = (WolfState) setupNextState(wolfPreviousState, wolfNextState, action);
        setClosestSheep(wolfNextState);

        return wolfNextState;
    }

    /**
     * @return reward
     */
    @Override
    public int tryToEatSheeps(AnimalState nextState) {
        ListIterator<Animal> sheepIterator = environment.getSheeps().listIterator();
        while (sheepIterator.hasNext()) {
            Animal sheep = sheepIterator.next();
            if (sheep.getPosition().equals(nextState.getPosition())) {
                environment.addSheepToRemove(sheep);
                statistics.sheepEaten(environment.getStep());
                return 1;
            }
        }
        return 0;
    }

    public WolfState createInitialState(Animal animal) {
        WolfState wolfState = new WolfState(environment);
        setupInitialState(wolfState, animal);
        setClosestSheep(wolfState);
        return wolfState;
    }

    protected abstract void setClosestSheep(WolfState wolfState);

    protected abstract WolfState newState();
}
