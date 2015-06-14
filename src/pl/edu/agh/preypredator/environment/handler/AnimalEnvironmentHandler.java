package pl.edu.agh.preypredator.environment.handler;

import pl.edu.agh.preypredator.environment.MeadowEnvironment;
import pl.edu.agh.preypredator.environment.action.MoveAction;
import pl.edu.agh.preypredator.environment.action.MoveDirection;
import pl.edu.agh.preypredator.environment.creature.Animal;
import pl.edu.agh.preypredator.environment.handler.rewarder.Rewarder;
import pl.edu.agh.preypredator.environment.state.AnimalState;
import pl.edu.agh.preypredator.satistics.Statistics;
import pl.edu.agh.preypredator.util.Point;
import environment.ActionList;
import environment.IAction;
import environment.IState;

public abstract class AnimalEnvironmentHandler {

    protected MeadowEnvironment environment;
    protected Statistics statistics;

    private Rewarder rewarder;

    public AnimalEnvironmentHandler(MeadowEnvironment environment, Statistics statistics) {
        this.environment = environment;
        this.statistics = statistics;
    }

    public ActionList getActionList(IState state) {
        AnimalState animalState = (AnimalState) state;
        ActionList actionList = new ActionList(animalState);
        addActionsToList(animalState, actionList);
        return actionList;

    }

    private void addActionsToList(AnimalState state, ActionList actionList) {
        Point animalPosition = state.getPosition();
        if (animalPosition.getX() > 0) {
            actionList.add(new MoveAction(MoveDirection.LEFT));
        }
        if (animalPosition.getX() < environment.getDimension().getWidth() - 1) {
            actionList.add(new MoveAction(MoveDirection.RIGHT));
        }
        if (animalPosition.getY() > 0) {
            actionList.add(new MoveAction(MoveDirection.UP));
        }
        if (animalPosition.getY() < environment.getDimension().getHeight() - 1) {
            actionList.add(new MoveAction(MoveDirection.DOWN));
        }
    }

    public abstract AnimalState successorState(IState previousState, IAction action);

    protected final IState setupNextState(AnimalState previousState, AnimalState newState, IAction action) {
        int previousX = previousState.getPosition().getX();
        int previousY = previousState.getPosition().getY();

        MoveAction moveAction = (MoveAction) action;
        newState.setAnimal(previousState.getAnimal());

        switch (moveAction.getDirection()) {
        case DOWN: {
            newState.setPosition(new Point(previousX, previousY + 1));
            break;
        }
        case LEFT: {
            newState.setPosition(new Point(previousX - 1, previousY));
            break;
        }
        case RIGHT: {
            newState.setPosition(new Point(previousX + 1, previousY));
            break;
        }
        case UP: {
            newState.setPosition(new Point(previousX, previousY - 1));
            break;
        }
        case NONE: {
            newState.setPosition(new Point(previousX, previousY));
            break;
        }
        }

        return newState;
    }

    protected final AnimalState setupInitialState(AnimalState state, Animal animal) {
        state.setAnimal(animal);
        state.setPosition(new Point(animal.getPosition()));
        animal.getAgent().setInitialState(state);
        return state;
    }

    public double getReward(IState s1, IState s2, IAction a) {
        double reward = environment.getTempReward();
        environment.setTempReward(0);

        return reward;
    }

    public abstract int tryToEatSheeps(AnimalState nextState);

    public Rewarder getRewarder() {
        return rewarder;
    }

    public void setRewarder(Rewarder rewarder) {
        this.rewarder = rewarder;
    }

}
