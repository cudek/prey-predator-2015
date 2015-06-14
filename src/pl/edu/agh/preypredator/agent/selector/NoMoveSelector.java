package pl.edu.agh.preypredator.agent.selector;

import pl.edu.agh.preypredator.environment.action.MoveAction;
import pl.edu.agh.preypredator.environment.action.MoveDirection;
import algorithms.ISelector;
import dataset.Dataset;
import environment.ActionList;
import environment.IAction;
import environment.IState;

public class NoMoveSelector implements ISelector {

    @Override
    public void learn(IState s1, IState s2, IAction a, double reward) {
    }

    @Override
    public void newEpisode() {
    }

    @Override
    public IAction getChoice(ActionList l) {
        return new MoveAction(MoveDirection.NONE);
    }

    @Override
    public Dataset extractDataset() {
        return null;
    }

}
