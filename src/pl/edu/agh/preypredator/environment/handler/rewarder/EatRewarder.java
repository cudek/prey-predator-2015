package pl.edu.agh.preypredator.environment.handler.rewarder;

import pl.edu.agh.preypredator.environment.MeadowEnvironment;
import environment.IAction;
import environment.IState;

public class EatRewarder extends Rewarder {

    private MeadowEnvironment environment;

    public EatRewarder(MeadowEnvironment environment) {
        this.environment = environment;
    }

    @Override
    public double getReward(IState s1, IState s2, IAction a) {
        double reward = environment.getTempReward();
        environment.setTempReward(0);

        return reward;
    }
}
