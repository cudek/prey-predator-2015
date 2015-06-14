package piqle;

import agents.LoneAgent;
import core.environment.agent.Animal;

public class PiqlePredator extends Animal {

    private LoneAgent agent;

    public PiqlePredator(int lineOfSight) {
        super(lineOfSight);
    }

    @Override
    public boolean isPrey() {
        return false;
    }

}
