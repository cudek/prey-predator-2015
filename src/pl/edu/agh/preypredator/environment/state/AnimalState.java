package pl.edu.agh.preypredator.environment.state;

import pl.edu.agh.preypredator.environment.creature.Animal;
import pl.edu.agh.preypredator.util.Point;
import environment.AbstractState;
import environment.IEnvironment;

public abstract class AnimalState extends AbstractState {

    protected Animal animal;
    protected Point position;

    public AnimalState(IEnvironment environment) {
        super(environment);
    }

    public abstract boolean isPredator();

    /** References animal in the environment, DO NOT MODIFY this object */
    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Point getPosition() {
        return position;
    }

    public AnimalState setPosition(Point position) {
        this.position = position;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AnimalState other = (AnimalState) obj;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }
}
