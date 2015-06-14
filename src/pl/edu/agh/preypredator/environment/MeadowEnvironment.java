package pl.edu.agh.preypredator.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.edu.agh.preypredator.agent.selector.NoMoveSelector;
import pl.edu.agh.preypredator.environment.creature.Animal;
import pl.edu.agh.preypredator.environment.handler.PredatorEnvironmentHandler;
import pl.edu.agh.preypredator.environment.handler.PreyEnvironmentHandler;
import pl.edu.agh.preypredator.environment.state.AnimalState;
import pl.edu.agh.preypredator.satistics.Statistics;
import pl.edu.agh.preypredator.util.Dimension;
import pl.edu.agh.preypredator.util.Point;
import pl.edu.agh.preypredator.visualizaton.Visualizer;
import agents.AbstractAgent;
import algorithms.ISelector;
import environment.ActionList;
import environment.IAction;
import environment.IEnvironment;
import environment.IState;

public class MeadowEnvironment implements IEnvironment {

    private static final long serialVersionUID = 2844351588170448197L;

    private double tempReward = 0;

    private Dimension meadowDimension;

    private List<Animal> wolfs;
    private List<Animal> sheeps;

    private List<Animal> sheepsToRemove = new ArrayList<>();

    private Visualizer visualizer;

    private boolean isSheepNumberConstant = true;
    private int sheepNumber;

    private Random randomGenerator;

    private PredatorEnvironmentHandler predatorEnvironmentHandler;
    private PreyEnvironmentHandler preyEnvironmentHandler;

    private Statistics statistics = new Statistics();

    private int step = 0;

    public MeadowEnvironment() {
    }

    public void step() {
        ++step;
        for (Animal wolf : wolfs) {
            wolf.step();
        }
        for (Animal sheep : sheeps) {
            sheep.step();
        }
        for (Animal sheep : sheepsToRemove) {
            sheep.setEaten(true);
        }
        sheeps.removeAll(sheepsToRemove);
        removeSheepsEatenInThisStep();
        redeployEatenSheeps();
        if (step % 1000 == 0) {
            System.out.println(step);
        }
    }

    private void redeployEatenSheeps() {
        if (isSheepNumberConstant) {
            if (sheepNumber > sheeps.size()) {
                for (int i = sheeps.size(); i < sheepNumber; ++i) {
                    ISelector sheepSelector = new NoMoveSelector();
                    AbstractAgent sheepAgent = new AbstractAgent(this, sheepSelector);
                    // @formatter:off
                    Animal sheep = new Animal(new Point(
                        randomGenerator.nextInt(meadowDimension.getWidth()), 
                        randomGenerator.nextInt(meadowDimension.getHeight())
                    ));
                    //@formatter:on
                    sheep.setAgent(sheepAgent);
                    preyEnvironmentHandler.createInitialState(sheep);

                    sheeps.add(sheep);
                }
            }
        }
    }

    private void removeSheepsEatenInThisStep() {
        sheepsToRemove.clear();
        visualizer.step(this);
    }

    @Override
    public ActionList getActionList(IState state) {
        AnimalState animalState = (AnimalState) state;
        if (animalState.isPredator()) {
            return predatorEnvironmentHandler.getActionList(animalState);
        } else {
            return preyEnvironmentHandler.getActionList(animalState);
        }
    }

    @Override
    public IState successorState(IState state, IAction action) {
        AnimalState animalPreviousState = (AnimalState) state;
        AnimalState animalNextState;
        if (animalPreviousState.isPredator()) {
            animalNextState = predatorEnvironmentHandler.successorState(animalPreviousState, action);
        } else {
            animalNextState = preyEnvironmentHandler.successorState(animalPreviousState, action);
        }

        modifyEnvironment(animalPreviousState, animalNextState);

        return animalNextState;
    }

    protected void modifyEnvironment(AnimalState previousState, AnimalState nextState) {
        moveAnimal(nextState);
        if (previousState.isPredator()) {
            tempReward = predatorEnvironmentHandler.tryToEatSheeps(nextState);
        } else {
            tempReward = preyEnvironmentHandler.tryToEatSheeps(nextState);
        }
    }

    private void moveAnimal(AnimalState nextState) {
        for (Animal animal : getAllAnimals()) {
            if (animal == nextState.getAnimal()) {
                animal.setPosition(new Point(nextState.getPosition()));
                break;
            }
        }
    }

    private List<Animal> getAllAnimals() {
        ArrayList<Animal> animals = new ArrayList<>(wolfs.size() + sheeps.size());
        animals.addAll(wolfs);
        animals.addAll(sheeps);
        return animals;
    }

    @Override
    public double getReward(IState s1, IState s2, IAction a) {
        AnimalState state = (AnimalState) s1;
        if (state.isPredator()) {
            return predatorEnvironmentHandler.getReward(s1, s2, a);
        } else {
            return preyEnvironmentHandler.getReward(s1, s2, a);

        }
    }

    @Override
    public boolean isFinal(IState s) {
        return sheeps.isEmpty();
    }

    @Override
    public int whoWins(IState s) {
        return 0;
    }

    public void setWolfs(List<Animal> wolfs) {
        this.wolfs = wolfs;
    }

    public void setSheeps(List<Animal> sheeps) {
        this.sheeps = sheeps;
        sheepNumber = sheeps.size();
    }

    public void setMeadowDimension(Dimension meadowDimension) {
        this.meadowDimension = meadowDimension;
    }

    public void initalize() {
        for (Animal wolf : wolfs) {
            predatorEnvironmentHandler.createInitialState(wolf);
        }
        for (Animal sheep : sheeps) {
            preyEnvironmentHandler.createInitialState(sheep);
        }
        visualizer.initialze(this);
    }

    public Dimension getDimension() {
        return meadowDimension;
    }

    public List<Animal> getWolfs() {
        return wolfs;
    }

    public List<Animal> getSheeps() {
        return sheeps;
    }

    public void addSheepToRemove(Animal sheep) {
        sheepsToRemove.add(sheep);
    }

    public Visualizer getVisualizer() {
        return visualizer;
    }

    public void setVisualizer(Visualizer visualizer) {
        this.visualizer = visualizer;
    }

    public boolean isSheepNumberConstant() {
        return isSheepNumberConstant;
    }

    public void setSheepNumberConstant(boolean isSheepNumberConstant) {
        this.isSheepNumberConstant = isSheepNumberConstant;
    }

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public int getStep() {
        return step;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public double getTempReward() {
        return tempReward;
    }

    public void setTempReward(double tempReward) {
        this.tempReward = tempReward;
    }

    public PredatorEnvironmentHandler getPredatorEnvironmentHandler() {
        return predatorEnvironmentHandler;
    }

    public void setPredatorEnvironmentHandler(PredatorEnvironmentHandler predatorEnvironmentHandler) {
        this.predatorEnvironmentHandler = predatorEnvironmentHandler;
    }

    public PreyEnvironmentHandler getPreyEnvironmentHandler() {
        return preyEnvironmentHandler;
    }

    public void setPreyEnvironmentHandler(PreyEnvironmentHandler preyEnvironmentHandler) {
        this.preyEnvironmentHandler = preyEnvironmentHandler;
    }

}
