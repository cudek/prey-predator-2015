
package core.deciding;

import core.simulation.Agent;
import core.simulation.Environment;
import java.util.List;

/**
 *
 * @author cudeks
 */
public interface ISelector {
    public IAction chooseAction(List<IAction> actionList, Agent agent, Environment env);
}
