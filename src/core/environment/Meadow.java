package core.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import core.environment.agent.Animal;
import core.simulation.Agent;
import core.simulation.Environment;
import core.simulation.IsufficientSpaceForAgentsException;
import core.visualization.AudioApp;

/**
 *
 * @author cudek
 */

/**
 * 
 * Zakladamy, ze łaka jest ograniczona od dolu przez 0, od gory przez <i>length
 * </i>,od lewej przez 0, od prawej przez <i>width</i>
 */
public class Meadow extends Environment {

    List<Animal> animals;
    /* przyspiesza sprawdzanie czy pole jest zajęte przez zwierze */
    HashMap<Point, Animal> occupiedTiles;
    int preysLeft = 0;
    AudioApp audioApp;
    int width;
    int length;

    public Meadow(int width, int length) {
        this.width = width;
        this.length = length;
        animals = new LinkedList<Animal>();
        occupiedTiles = new HashMap<Point, Animal>();
        audioApp = new AudioApp();
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getPreysLeft() {
        return preysLeft;
    }

    /**
     * rejestruje agenta i dodaje go do środowiska
     */
    @Override
    public void addAgent(Agent agent) {
        Animal animal = (Animal) agent;
        super.addAgent(agent);
        if (animals.contains(animal)) {
            throw new IllegalArgumentException("Agent is already added");
        }
        animals.add((Animal) agent);
    }

    /**
     * wyrejestrowywuje agenta i usuwa go ze środowiska
     */
    @Override
    public void killAgent(Agent agent) {
        Animal animal = (Animal) agent;
        super.killAgent(agent);
        if (!animals.contains(animal)) {
            throw new IllegalArgumentException("Agent does not exist");
        }
        occupiedTiles.remove(animal.getPoint());
        animals.remove(animal);
        --preysLeft;
    }

    /**
     * inicjalizuje środowisko - przypisuje m.in. zwierzętom początkową pozycję
     */
    @Override
    public void initialize() throws IsufficientSpaceForAgentsException {
        preysLeft = 0;
        /* sprawdź, czy zwierzęta mieszczą się na łące */
        if (animals.size() > width * length)
            throw new IsufficientSpaceForAgentsException();

        /* utwórz listę niezajętych pól */
        List<Point> freeFields = new ArrayList<Point>();
        for (int i = 0; i < width; ++i) {
            for (int j = 0; j < length; ++j) {
                freeFields.add(new Point(i, j));
            }
        }
        Random randomGenerator = new Random(44);
        int fieldsLeft = freeFields.size(); // pozostałe wolne pola
        Iterator<Animal> it = animals.iterator();
        occupiedTiles = new HashMap<Point, Animal>();

        while (it.hasNext()) {
            Animal animal = it.next();
            if (animal.isPrey() == true)
                ++preysLeft;
            Point point = freeFields.remove(randomGenerator.nextInt(fieldsLeft--));
            animal.setPoint(point);
            occupiedTiles.put(point, animal);
        }

    }

    /**
     * symulacja jest skończona jeśli zostały zjedzone wszystkie króliki
     */
    @Override
    public boolean isFInished() {
        return preysLeft == 0 ? true : false;
    }

    private void performMovement(Animal animal, Point destination) {
        occupiedTiles.remove(animal.getPoint());
        occupiedTiles.put(destination, animal);
        animal.setPoint(destination);
    }

    void moveAnimal(Animal animal, Point destination) {
        Animal animal2;
        if ((animal2 = occupiedTiles.get(destination)) != null) {
            if (animal.isPrey() == animal2.isPrey())
                throw new UndefinedActionException("Two animals of the same type cannot share a tile.");
            else {

                if (animal.isPrey()) {
                    killAgent(animal);
                    performMovement(animal2, destination);
                    audioApp.playSoundOnce();
                } else {
                    killAgent(animal2);
                    performMovement(animal, destination);
                    audioApp.playSoundOnce();
                }
            }
        } else {
            performMovement(animal, destination);
        }
    }

    /**
     * 
     * Zwraca odległość między dwoma punktami w metryce taksówkowej
     */
    private int getDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    /**
     * Zwraca odległość do najbliższego drpieżnika (w przypadku ofiary) lub
     * najbliższej zwierzyny (w przypadku drapieżnika) w metryce taksówkowej :)
     * 
     * @param observerPoint
     *            współrzędne obserwatora
     * @param isPredator
     *            czy obserwator jest drapieżnikem
     * @return odległość w metryce taksówkowej
     */
    public int getClosestAntagonistDistance(Point observerPoint, boolean isPredator) {
        int closestDistance = Integer.MAX_VALUE;
        for (Animal animal : animals) {
            Point animalPosition = animal.getPoint();
            int distance;
            if (animal.isPrey() == isPredator
                    && (distance = getDistance(animalPosition, observerPoint)) < closestDistance) {
                closestDistance = distance;
            }
        }
        return closestDistance;
    }

    /*
     * sprawdza czy dane zwierze może wkroczyć na płytkę o podanychwspółrzędnych
     */
    public boolean canEnterTile(Animal animal, Point point) {
        Animal animalOnTile;
        if (point.x >= width || point.x < 0 || point.y >= length || point.y < 0
                || ((animalOnTile = occupiedTiles.get(point)) != null && animalOnTile.isPrey() == animal.isPrey()))
            return false;
        return true;
    }
}
