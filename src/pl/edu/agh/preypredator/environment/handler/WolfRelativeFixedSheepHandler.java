package pl.edu.agh.preypredator.environment.handler;

import pl.edu.agh.preypredator.environment.MeadowEnvironment;
import pl.edu.agh.preypredator.environment.creature.Animal;
import pl.edu.agh.preypredator.environment.state.WolfFixedSheepState;
import pl.edu.agh.preypredator.environment.state.WolfState;
import pl.edu.agh.preypredator.satistics.Statistics;
import pl.edu.agh.preypredator.util.DistanceUtil;
import pl.edu.agh.preypredator.util.Point;

public class WolfRelativeFixedSheepHandler extends WolfRelativeSheepHandler {

    public WolfRelativeFixedSheepHandler(MeadowEnvironment environment, Statistics statistics) {
        super(environment, statistics);
    }

    @Override
    public WolfState createInitialState(Animal animal) {
        WolfFixedSheepState wolfState = new WolfFixedSheepState(environment);
        setupInitialState(wolfState, animal);
        setClosestSheep(wolfState);
        return wolfState;
    }

    @Override
    protected void setClosestSheep(WolfState wolfState) {
        WolfFixedSheepState wolfFixedSheepState = (WolfFixedSheepState) wolfState;

        Point closestSheepPosition = null;
        int closestDistance = -1;

        Point wolfPosition = wolfFixedSheepState.getPosition();

        if (wolfFixedSheepState.getFixedSheep() == null || wolfFixedSheepState.getFixedSheep().isEaten()) {
            for (Animal sheep : environment.getSheeps()) {
                int distance = DistanceUtil.calculateDistance(wolfPosition, sheep.getPosition());
                if (closestDistance < 0 || distance < closestDistance) {
                    closestDistance = distance;
                    closestSheepPosition = sheep.getPosition();
                    wolfFixedSheepState.setFixedSheep(sheep);
                }
            }
        } else {
            closestSheepPosition = new Point(wolfFixedSheepState.getFixedSheep().getPosition());
        }

        // @formatter:off
        Point relativePosition = new Point(
            closestSheepPosition.getX() - wolfPosition.getX(),
            closestSheepPosition.getY() - wolfPosition.getY()
        );
        // @formatter:on

        wolfFixedSheepState.setClosestSheepPosition(relativePosition);
    }

    @Override
    protected WolfState newState() {
        return new WolfFixedSheepState(environment);
    }

}
