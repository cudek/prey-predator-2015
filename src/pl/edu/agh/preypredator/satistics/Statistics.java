package pl.edu.agh.preypredator.satistics;

import java.util.Map;
import java.util.TreeMap;

public class Statistics {

    private Map<Integer, Integer> stepSheepsEaten = new TreeMap<>();

    public void sheepEaten(int step) {
        Integer previousValue = stepSheepsEaten.put(step, 1);
        if (previousValue != null) {
            stepSheepsEaten.put(step, previousValue + 1);
        }
    }

    public Map<Integer, Integer> getStepSheepsEaten() {
        return stepSheepsEaten;
    }

    public int getTotalSheepsEaten() {
        int total = 0;
        for (Integer eatenSheeps : stepSheepsEaten.values()) {
            total += eatenSheeps;
        }
        return total;
    }

    public int getTotalStepsWhereSheepEaten() {
        return stepSheepsEaten.size();
    }
}
