
package core.environment.selector;

import core.deciding.IAction;
import core.environment.Meadow;
import core.environment.MovementAction;
import core.environment.agent.Prey;
import core.simulation.Agent;
import core.simulation.Environment;

/**
 *
 * @author cudek
 */

/**
 * Obiekty tej klasy wybierają akcję dla zwierzyny
 */

public class PreyMovementSelector extends GreedyMovementSelector {

    protected  int evaluateAction(IAction action, Agent agent, Environment env) {
        MovementAction movement = (MovementAction)action;
        Prey prey = (Prey)agent;
        Meadow meadow = (Meadow)env;
        int preysLeft = meadow.getPreysLeft();
        int distance = meadow.getClosestAntagonistDistance(
                movement.getDestination(), false);
        /*jezeli najblizsza ofiara jest dalej niz pole widznia to ocena ruchu ma
         * wartosc rowną polu widzenia, jeśli ofiara jest bliżej to wartość
         * ruchu równa jest odleglości od ofiary
         */
        return distance > prey.getLineOfSight() ?
            0 : prey.getLineOfSight() - distance;
    }
}
