package pl.edu.agh.preypredator.environment.handler.rewarder;

import pl.edu.agh.preypredator.environment.state.WolfState;
import pl.edu.agh.preypredator.util.DistanceUtil;
import pl.edu.agh.preypredator.util.Point;
import environment.IAction;
import environment.IState;

public class DistanceRewarder extends Rewarder {

    @Override
    public double getReward(IState s1, IState s2, IAction a) {
        WolfState state1 = (WolfState) s1;
        WolfState state2 = (WolfState) s2;
        return rewardForBeingCloser(state1, state2);
    }

    private int rewardForBeingCloser(WolfState state1, WolfState state2) {
        // @formatter:off
        return Integer.compare(
            DistanceUtil.calculateDistance(new Point(0, 0), state1.getClosestSheep()),
            DistanceUtil.calculateDistance(new Point(0, 0), state2.getClosestSheep())
        );
        // @formatter:on
    }

}
