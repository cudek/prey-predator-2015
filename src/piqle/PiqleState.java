package piqle;

import core.environment.agent.Predator;
import core.environment.agent.Prey;
import environment.AbstractState;
import environment.IEnvironment;
import environment.IState;

/**
 * @author Tomasz Cudek
 * 
 */
public class PiqleState extends AbstractState {

    private Predator predator;
    private Prey nearestNeighbour;

    public PiqleState(IEnvironment ct, Predator predator, Prey nearestNeighbour) {
        super(ct);
        this.predator = predator;
        this.nearestNeighbour = nearestNeighbour;
    }

    @Override
    public IState copy() {
        return new PiqleState(myEnvironment, predator.copy(), nearestNeighbour.copy());
    }

    @Override
    public int nnCodingSize() {
        return 2;
    }

    @Override
    public double[] nnCoding() {
        return new double[] { predator.getPoint().x, predator.getPoint().y, nearestNeighbour.getPoint().x,
                nearestNeighbour.getPoint().y };
    }

    public Predator getPredator() {
        return predator;
    }

    public void setPredator(Predator predator) {
        this.predator = predator;
    }

    public Prey getNearestNeighbour() {
        return nearestNeighbour;
    }

    public void setNearestNeighbour(Prey nearestNeighbour) {
        this.nearestNeighbour = nearestNeighbour;
    }
}
