
package core.visualization;

import core.simulation.Environment;
import core.simulation.ISteppable;

/**
 *
 * @author cudek
 */
public abstract class Visualizer implements ISteppable{

    private long delay; //czas na jaki wizualizator opóźnia kolejne kroki symulacji

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public abstract void initialze(Environment env);

    public void step(Environment env){
        try{
            Thread.sleep(delay);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
