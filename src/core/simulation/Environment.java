package core.simulation;

/**
 *
 * @author cudek
 */
public abstract class Environment {
    private Scheduler scheduler = new Scheduler();
    private long step = 0;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void addAgent(Agent agent){
        scheduler.register(agent);
    }

    public void killAgent(Agent agent){
        scheduler.unregister(agent);
    }

    long getCurrentStep(){
        return step;
    };

    protected void run(){
        while (!isFInished()){
            ++step;
            step();
        }
    }

    /**
     * wykonanie kroku symulacji - wykonanie ackji agentow, zebranie statystyk,
     * komunikacja, itd.
     */
    void step(){
        scheduler.step(this);
    };

    public void start(){
        run();
        stop();
    };

    void stop(){
        System.out.println("Simulation ended succsesfuly.");
    };

    //public abstract void modifyState(IAction action);

    public abstract boolean isFInished();
    
    public abstract void initialize() throws IsufficientSpaceForAgentsException;
}
