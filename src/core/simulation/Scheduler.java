package core.simulation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Tomek Cudek
 */
public class Scheduler {

    private List<ISteppable> steppableObjects = new LinkedList<ISteppable>();
    private HashSet<ISteppable> toUnregister = new HashSet<ISteppable>();

    void register(ISteppable object){
        if (steppableObjects.contains(object))
            throw new IllegalArgumentException("Agent already registered");
        steppableObjects.add(object);
    };

    /* Agent nie jest od razu usuwany z listy zarejestrowanych agentów,
     * gdyż w chwili, gdy jest wyrejestrowywany trwa jeszcze w metodzie
     * step() Schdulera iteracja po liście z któej ma być usunięty. Agent jest
     * zatem wpisywany do zbioru agentów do usunięcia. Zostanie usunięty przy
     * po zakończeniu iteracji w metodzie step().
     */
    void unregister(ISteppable object){
        if (!steppableObjects.contains(object))
            throw new IllegalArgumentException("Agent not registered");
        toUnregister.add(object);
    };

    void step(Environment env){
        for (ISteppable object : steppableObjects){
            /*sprawdź czy agent nie został wyrejestrowny w czasie tej iteracji*/
            if (!toUnregister.contains(object))
                object.step(env);
        }

        /*usuń z listy wszystkich niezarejestrowanych agentów*/
        Object[] toRemove = toUnregister.toArray();
        for (Object x : toRemove){
            toUnregister.remove(x);
            steppableObjects.remove(x);
        }
    };
}
