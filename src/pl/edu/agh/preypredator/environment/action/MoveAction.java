package pl.edu.agh.preypredator.environment.action;

import environment.IAction;

public class MoveAction implements IAction {

    /**
     * 
     */
    private static final long serialVersionUID = -3751699925371483195L;

    private MoveDirection direction;

    public MoveAction(MoveDirection direction) {
        this.direction = direction;
    }

    @Override
    public Object copy() {
        return new MoveAction(direction);
    }

    @Override
    public int nnCodingSize() {
        return 1;
    }

    @Override
    public double[] nnCoding() {
        return new double[] { direction.ordinal() };
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return direction.name();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((direction == null) ? 0 : direction.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MoveAction other = (MoveAction) obj;
        if (direction != other.direction)
            return false;
        return true;
    }
}
