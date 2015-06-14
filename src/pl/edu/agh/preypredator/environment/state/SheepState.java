package pl.edu.agh.preypredator.environment.state;

import pl.edu.agh.preypredator.util.Point;
import environment.IEnvironment;
import environment.IState;

public class SheepState extends AnimalState {

    public SheepState(IEnvironment environment) {
        super(environment);
    }

    @Override
    public boolean isPredator() {
        return false;
    }

    @Override
    public IState copy() {
        SheepState state = new SheepState(getEnvironment());
        state.setAnimal(getAnimal());
        state.setPosition(new Point(getPosition()));
        return state;
    }

    @Override
    public int nnCodingSize() {
        return 2;
    }

    @Override
    public double[] nnCoding() {
        return new double[] { position.getX(), position.getY() };
    }

}
