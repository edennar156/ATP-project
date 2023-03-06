package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {
    public MazeState(AState from, Position stateName, int cost) {
        super(from, stateName, cost);
    }
}