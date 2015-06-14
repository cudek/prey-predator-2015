/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package core.environment;

/**
 *
 * @author Właściciel
 */
public class UndefinedActionException extends RuntimeException {

    /**
     * Creates a new instance of <code>UndefinedActionException</code> without detail message.
     */
    public UndefinedActionException() {
    }


    /**
     * Constructs an instance of <code>UndefinedActionException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UndefinedActionException(String msg) {
        super(msg);
    }
}
