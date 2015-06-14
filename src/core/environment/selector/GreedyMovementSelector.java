
package core.environment.selector;

import core.deciding.IAction;
import core.deciding.ISelector;
import core.simulation.Agent;
import core.simulation.Environment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author cudek
 */

/**
 *
 * Klasa zawierająca parę <wartość akcji, akcja>
 */

class MovEvalEntry implements Comparable<MovEvalEntry>{
    int value;
    IAction action;

    public MovEvalEntry(int value, IAction movement) {
        this.value = value;
        this.action = movement;
    }

    public IAction getAction() {
        return action;
    }

    public int getValue() {
        return value;
    }

    public int compareTo(MovEvalEntry movEntry) {
        return (this.value > movEntry.value ? 1 : (this.value < movEntry.value ?
            -1 : 0));
    }
    

}

/**
 * Klasa reprezentująca zachanny wybór akcji, obiekt tej wybierał będzie akcje
 * posiadającą w danej chwili najwyższą jakość
 */

public abstract class GreedyMovementSelector implements ISelector {
    
    protected  abstract int evaluateAction(
            IAction action, Agent agent, Environment env);

     /**
     * Wybiera najkorzystniejszy ruch, normą jakości ruchu jest odległość od
     * zwierzyny, jeżeli jest kilka najlepszych ruchów do wyboru to wybierany
     * jest losowo jeden z najlepszych. Ruch jest tym lepszy im mniejszą wartość
     * ma przypisaną. Najlepszy ruch to 0, najgorszy ma wartość nie większą niż
     * zasięg widzenia (jeżeli drapieżnik nie widzi ofiary to każdy kierunek
     * jest tak samo dobry)
     * @param actionList lista możliwych ruchów
     * @param agent agent wykonukacy ruch
     * @param env środowisko
     * @return najlepszy ruch
     */

    public IAction chooseAction(List<IAction> actionList, 
            Agent agent, Environment env)
    {
        List<MovEvalEntry> actions = new ArrayList<MovEvalEntry>();

        /*oszacowanie jakości każdej z akcji i wstawienie ich do listy*/
        for(IAction action : actionList){
            actions.add(new MovEvalEntry(evaluateAction(action, agent, env),
                    action));
        }
        /*posortownaie akcji wg. jakości*/
        Collections.sort(actions);
        /*policzenie ile jest najlepszych akcji o równej jakości*/
        int bestChoicesNoumber = 0;
        int bestMovementValue = actions.get(0).getValue();
        for (MovEvalEntry entry : actions){
            if (entry.getValue() == bestMovementValue)
                ++bestChoicesNoumber;
            else
                break;
        }
        /*wylosowanie akcji z najlepszych akcji*/

        /*jeżeli tylko jedna akcja jest najlepsza zwróć ją od razu*/
        if (bestChoicesNoumber == 1){
            return actions.get(0).getAction();
        }

        /*wybierz losowo jedną z najlepszch akcji*/
        Random randomGenerator = new Random();
        return actions.get(
                randomGenerator.nextInt(bestChoicesNoumber)).getAction();
    }
}
