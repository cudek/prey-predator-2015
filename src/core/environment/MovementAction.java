
package core.environment;

import core.deciding.IAction;
import core.environment.agent.Animal;
import core.simulation.Environment;

/**
 *
 * @author cudek
 */
public class MovementAction implements IAction{

    Animal animal;
    Point destination;

    public Point getDestination() {
        return destination;
    }

    public void setDestination(Point destination) {
        this.destination = destination;
    }

    public MovementAction(Animal animal, Point destination){
        this.animal = animal;
        this.destination = destination;
    }
    
    public void execute(Environment env) {
        Meadow meadow = (Meadow)env;
        if (destination != animal.getPoint())
            meadow.moveAnimal(animal, destination);
    }
}
