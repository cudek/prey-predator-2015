package core.environment.agent;

import core.environment.selector.PreyMovementSelector;

/**
 * 
 * @author cudek
 */
public class Prey extends Animal {

    public Prey(int lineOfSight) {
        super(lineOfSight);
        selector = new PreyMovementSelector();
    }

    @Override
    public boolean isPrey() {
        return true;
    }

    @Override
    public Prey copy() {
        Prey prey = new Prey(getLineOfSight());
        prey.setPoint(getPoint().copy());
        return prey;
    }

}
