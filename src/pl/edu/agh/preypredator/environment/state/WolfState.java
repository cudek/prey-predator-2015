package pl.edu.agh.preypredator.environment.state;

import pl.edu.agh.preypredator.util.Point;
import environment.IEnvironment;
import environment.IState;

public class WolfState extends AnimalState {

    private Point closestSheep;

    public WolfState(IEnvironment environment) {
        super(environment);
    }

    @Override
    public boolean isPredator() {
        return true;
    }

    public Point getClosestSheep() {
        return closestSheep;
    }

    public WolfState setClosestSheepPosition(Point closestSheep) {
        this.closestSheep = closestSheep;
        return this;
    }

    @Override
    public IState copy() {
        WolfState state = new WolfState(getEnvironment());
        state.setAnimal(getAnimal());
        state.setClosestSheepPosition(new Point(getClosestSheep()));
        state.setPosition(new Point(getPosition()));
        return state;
    }

    @Override
    public int nnCodingSize() {
        return 4;
    }

    @Override
    public double[] nnCoding() {
        // @formatter:off
        return new double[] { 
            position.getX(), 
            position.getY(), 
            closestSheep.getX(), 
            closestSheep.getY() 
            // @formatter:on
        };
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((closestSheep == null) ? 0 : closestSheep.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        WolfState other = (WolfState) obj;
        if (closestSheep == null) {
            if (other.closestSheep != null)
                return false;
        } else if (!closestSheep.equals(other.closestSheep))
            return false;
        return true;
    }
}
