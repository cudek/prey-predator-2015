package pl.edu.agh.preypredator.environment.handler;

import pl.edu.agh.preypredator.environment.MeadowEnvironment;
import pl.edu.agh.preypredator.environment.creature.Animal;
import pl.edu.agh.preypredator.environment.state.WolfState;
import pl.edu.agh.preypredator.satistics.Statistics;
import pl.edu.agh.preypredator.util.DistanceUtil;
import pl.edu.agh.preypredator.util.Point;

public class WolfAbsoluteSheepHandler extends PredatorEnvironmentHandler {

    public WolfAbsoluteSheepHandler(MeadowEnvironment environment, Statistics statistics) {
        super(environment, statistics);
    }

    @Override
    protected void setClosestSheep(WolfState wolfState) {
        wolfState.setClosestSheepPosition(getClosestSheepPositon(wolfState));
    }

    private Point getClosestSheepPositon(WolfState wolfState) {
        Point closestSheepPosition = null;
        int closestDistance = -1;

        Point wolfPosition = wolfState.getPosition();

        for (Animal sheep : environment.getSheeps()) {
            int distance = DistanceUtil.calculateDistance(wolfPosition, sheep.getPosition());
            if (closestDistance < 0 || distance < closestDistance) {
                closestDistance = distance;
                closestSheepPosition = sheep.getPosition();
            }
        }

        return closestSheepPosition;
    }

    @Override
    protected WolfState newState() {
        return new WolfState(environment);
    }

}
