package core.simulation;

/**
 *
 * @author cudek
 */
public class IsufficientSpaceForAgentsException extends Exception {

    /**
     * Creates a new instance of <code>IsufficientSpaceForAgentsException</code> without detail message.
     */
    public IsufficientSpaceForAgentsException() {
    }


    /**
     * Constructs an instance of <code>IsufficientSpaceForAgentsException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public IsufficientSpaceForAgentsException(String msg) {
        super(msg);
    }
}
