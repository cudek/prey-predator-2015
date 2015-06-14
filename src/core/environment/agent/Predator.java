package core.environment.agent;

import core.environment.selector.PredatorMovementSelector;

/**
 * 
 * @author cudek
 */
public class Predator extends Animal {

    public Predator(int lineOfSight) {
        super(lineOfSight);
        selector = new PredatorMovementSelector();
    }

    @Override
    public boolean isPrey() {
        return false;
    }

    @Override
    public Predator copy() {
        Predator predator = new Predator(getLineOfSight());
        predator.setPoint(getPoint().copy());
        return predator;
    }

}
