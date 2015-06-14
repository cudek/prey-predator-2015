package pl.edu.agh.preypredator.experiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import pl.edu.agh.preypredator.agent.selector.NoMoveSelector;
import pl.edu.agh.preypredator.environment.MeadowEnvironment;
import pl.edu.agh.preypredator.environment.creature.Animal;
import pl.edu.agh.preypredator.environment.handler.PredatorEnvironmentHandler;
import pl.edu.agh.preypredator.environment.handler.PreyEnvironmentHandler;
import pl.edu.agh.preypredator.environment.handler.WolfRelativeFixedSheepHandler;
import pl.edu.agh.preypredator.environment.handler.rewarder.EatRewarder;
import pl.edu.agh.preypredator.util.Dimension;
import pl.edu.agh.preypredator.util.Point;
import pl.edu.agh.preypredator.visualizaton.Visualizer;
import agents.AbstractAgent;
import algorithms.ISelector;
import algorithms.RandomSelector;

public class Experiment {

    public static void main(String[] args) {
        MeadowEnvironment environment = new MeadowEnvironment();

        Dimension environmentDimension = new Dimension(5, 5);

        Random randomGenerator = new Random();

        int sheepsNumber = 3;
        List<Animal> sheeps = new ArrayList<>();

        for (int i = 0; i < sheepsNumber; ++i) {
            // ISelector sheepSelector = new RandomSelector();
            ISelector sheepSelector = new NoMoveSelector();
            AbstractAgent sheepAgent = new AbstractAgent(environment, sheepSelector);
            // @formatter:off
            Animal sheep = new Animal(new Point(
                randomGenerator.nextInt(environmentDimension.getWidth()), 
                randomGenerator.nextInt(environmentDimension.getHeight())
            ));
            //@formatter:on
            sheep.setAgent(sheepAgent);
            sheeps.add(sheep);
        }

        List<Animal> wolfs = new ArrayList<>();
        wolfs.add(new Animal(new Point(0, 0)));
        ISelector wolfSelector = new RandomSelector();
        // QLearningSelector wolfSelector = new QLearningSelector();
        // ISelector wolfSelector = new PengSelector(1);
        AbstractAgent wolfAgent = new AbstractAgent(environment, wolfSelector);
        wolfs.get(0).setAgent(wolfAgent);

        Visualizer visualizer = new Visualizer();

        // predatorEnvironmentHandler = new WolfAbsoluteSheepHandler(this,
        // statistics);
        // predatorEnvironmentHandler = new WolfRelativeSheepHandler(this,
        // statistics);
        PredatorEnvironmentHandler predatorEnvironmentHandler = new WolfRelativeFixedSheepHandler(environment,
            environment.getStatistics());
        predatorEnvironmentHandler.setRewarder(new EatRewarder(environment));
        PreyEnvironmentHandler preyEnvironmentHandler = new PreyEnvironmentHandler(environment,
            environment.getStatistics());
        preyEnvironmentHandler.setRewarder(new EatRewarder(environment));

        environment.setMeadowDimension(environmentDimension);
        environment.setSheeps(sheeps);
        environment.setWolfs(wolfs);
        environment.setVisualizer(visualizer);
        environment.setRandomGenerator(randomGenerator);
        environment.setPredatorEnvironmentHandler(predatorEnvironmentHandler);
        environment.setPreyEnvironmentHandler(preyEnvironmentHandler);

        environment.initalize();
        for (int i = 0; i < 10000; ++i) {
            environment.step();
            if (environment.isFinal(null)) {
                break;
            }
        }

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(
            "C:/Users/Właściciel/Desktop/prey_predator_other.txt")))) {
            for (Entry<Integer, Integer> entry : environment.getStatistics().getStepSheepsEaten().entrySet()) {
                bufferedWriter.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        System.out.println("Number of states where sheeps eaten: "
            + environment.getStatistics().getTotalStepsWhereSheepEaten());
        System.out.println("Total sheeps eaten: " + environment.getStatistics().getTotalSheepsEaten());
    }
}
