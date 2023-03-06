package algorithms.search;

import java.io.Serializable;

/**
 * abstract State in searching problem
 */
public abstract class AState implements Serializable {

    private AState from;
    private Object currentState;
    private int cost;

    // Constructor:
    public AState(AState prevState, Object currentState, int cost) {
        this.from = prevState;
        this.currentState = currentState;
        this.cost = cost;
    }

    // Default constructor:
    public AState(){
        this.currentState = null;
        this.from = null;
        this.cost = 0;
    }

    // Getters & Setters
    public AState getFrom() {
        return from;
    }

    public Object getCurrentState() {
        return currentState;
    }

    public int getCost() {
        return cost;
    }

    public void setFrom(AState from){
        this.from = from;
    }

    public void setCurrentState(Object pos){
        this.currentState = pos;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    /**
     * Auto generates
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AState aState = (AState) o;
        return currentState != null ? currentState.equals(aState.currentState) : aState.currentState != null;
    }

    @Override
    public String toString() {
        return this.currentState.toString();
    }
}