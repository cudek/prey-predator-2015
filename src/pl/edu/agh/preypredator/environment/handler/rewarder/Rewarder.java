package pl.edu.agh.preypredator.environment.handler.rewarder;

import environment.IAction;
import environment.IState;

public abstract class Rewarder {

    public abstract double getReward(IState s1, IState s2, IAction a);

}
