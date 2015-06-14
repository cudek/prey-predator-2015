package core.simulation;

import core.deciding.IAction;
import core.deciding.ISelector;
import java.util.List;

/**
 *
 * @author cudek
 */
public abstract class Agent implements ISteppable{

    protected ISelector selector;

    private void performAction(IAction action, Environment env){
        action.execute(env);
    }

    public abstract List<IAction> generateActionList(Environment env);

    public void step(Environment env){
        performAction(selector.chooseAction(generateActionList(env), this, env), env);
    }
}
