
package core.simulation;

import core.environment.Meadow;
import core.environment.PPVisualizer;
import core.environment.agent.Predator;
import core.environment.agent.Prey;

/**
 *
 * @author cudek
 */
public class Main {
    public static void main(String[] args) throws Exception{
        /*inicjalizacja środowiska*/
        Meadow meadow = new  Meadow(50, 50);
        int predatorLineOfSight = 15;
        int preyLineOfSight = 10;
        /*inicjalizacja wizualizatora*/
        PPVisualizer visualizer = new PPVisualizer();
        visualizer.initialze(meadow);
        visualizer.setDelay(50);
        /*rejestracja agettów*/
        for (int i = 0; i < 15; ++i){
            meadow.addAgent(new Predator(predatorLineOfSight));
        }
        for (int i = 0; i < 50; ++i){
            meadow.addAgent(new Prey(preyLineOfSight));
        }
       /*na razie visualizer jest harkodowany*/
        meadow.getScheduler().register(visualizer);
        meadow.initialize();
        /*rozpocznij symulację*/
        meadow.start();
    }
}
