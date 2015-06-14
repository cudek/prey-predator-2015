
package core.environment.selector;

import core.deciding.IAction;
import core.environment.Meadow;
import core.environment.MovementAction;
import core.environment.agent.Predator;
import core.simulation.Agent;
import core.simulation.Environment;


/**
 *
 * @author cudek
 */

/**
 * Obiekty tej klasy wybierają akcję dla drapieżników
 */

public class PredatorMovementSelector extends GreedyMovementSelector{

    protected  int evaluateAction(IAction action, Agent agent, Environment env) {
        MovementAction movement = (MovementAction)action;
        Predator predator = (Predator)agent;
        Meadow meadow = (Meadow)env;
        int distance = meadow.getClosestAntagonistDistance(
                movement.getDestination(), true);
        /*jezeli najblizsza ofiara jest dalej niz pole widznia to ocen ruch na
         * wartosc rowna polu widzenia, jesli jest blizej to wartosc ruchu
         * rowna jest odleglosci od ofiary
         */
        return distance > predator.getLineOfSight() ?
            predator.getLineOfSight() : distance;
    }
}
