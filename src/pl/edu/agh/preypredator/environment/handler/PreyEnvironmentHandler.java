package pl.edu.agh.preypredator.environment.handler;

import java.util.ListIterator;

import pl.edu.agh.preypredator.environment.MeadowEnvironment;
import pl.edu.agh.preypredator.environment.creature.Animal;
import pl.edu.agh.preypredator.environment.state.AnimalState;
import pl.edu.agh.preypredator.environment.state.SheepState;
import pl.edu.agh.preypredator.satistics.Statistics;
import environment.IAction;
import environment.IState;

public class PreyEnvironmentHandler extends AnimalEnvironmentHandler {

    public PreyEnvironmentHandler(MeadowEnvironment environment, Statistics statistics) {
        super(environment, statistics);
    }

    @Override
    public AnimalState successorState(IState state, IAction action) {
        SheepState sheepPreviousState = (SheepState) state;
        SheepState sheepNextState = new SheepState(environment);
        setupNextState(sheepPreviousState, sheepNextState, action);

        return sheepNextState;
    }

    public SheepState createInitialState(Animal animal) {
        SheepState sheepState = new SheepState(environment);
        setupInitialState(sheepState, animal);
        return sheepState;
    }

    /**
     * @return reward
     */
    @Override
    public int tryToEatSheeps(AnimalState nextState) {
        for (Animal wolf : environment.getWolfs()) {
            if (wolf.getPosition().equals(nextState.getPosition())) {
                removeSheep(nextState);
                return -1;
            }
        }
        return 0;
    }

    public void removeSheep(AnimalState animalState) {
        ListIterator<Animal> sheepIterator = environment.getSheeps().listIterator();
        while (sheepIterator.hasNext()) {
            Animal sheep = sheepIterator.next();
            if (sheep.getPosition().equals(animalState.getPosition())) {
                environment.addSheepToRemove(sheep);
                statistics.sheepEaten(environment.getStep());
                return;
            }
        }
        throw new IllegalStateException("No sheep removed.");
    }
}
