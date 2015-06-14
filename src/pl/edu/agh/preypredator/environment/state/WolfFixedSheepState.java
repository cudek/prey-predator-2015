package pl.edu.agh.preypredator.environment.state;

import pl.edu.agh.preypredator.environment.creature.Animal;
import environment.IEnvironment;
import environment.IState;

public class WolfFixedSheepState extends WolfState {

    private Animal fixedSheep;

    public WolfFixedSheepState(IEnvironment environment) {
        super(environment);
    }

    @Override
    public IState copy() {
        WolfFixedSheepState wolfState = (WolfFixedSheepState) super.copy();
        wolfState.setAnimal(fixedSheep);
        return wolfState;
    }

    public Animal getFixedSheep() {
        return fixedSheep;
    }

    public void setFixedSheep(Animal fixedSheep) {
        this.fixedSheep = fixedSheep;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
