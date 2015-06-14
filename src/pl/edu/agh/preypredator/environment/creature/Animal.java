package pl.edu.agh.preypredator.environment.creature;

import pl.edu.agh.preypredator.util.Point;
import agents.IAgent;

public class Animal {

    private IAgent agent;
    private Point position;

    private boolean isEaten;

    public Animal() {
    }

    public Animal(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public IAgent getAgent() {
        return agent;
    }

    public void setAgent(IAgent agent) {
        this.agent = agent;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public void setEaten(boolean isEaten) {
        this.isEaten = isEaten;
    }

    public void step() {
        agent.act();
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
