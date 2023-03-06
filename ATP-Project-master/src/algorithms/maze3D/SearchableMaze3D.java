package algorithms.maze3D;

import algorithms.search.AState;
import algorithms.search.ISearchable;

import java.util.ArrayList;

public class SearchableMaze3D implements ISearchable {
    private final Maze3D maze;
    private final Maze3DState startState, goalState;

    /**
     * constructor
     *
     * @param maze 3D maze to solve
     * @throws IllegalArgumentException -> maze == null
     */
    public SearchableMaze3D(Maze3D maze) throws IllegalArgumentException {
        if (maze == null)
            throw new IllegalArgumentException("cant handle null maze");
        this.maze = maze;
        this.startState = new Maze3DState(null, maze.getStartPosition(), 0);
        this.goalState = new Maze3DState(null, maze.getGoalPosition(), 0);
    }

    @Override
    public AState getStartState() {
        return this.startState;
    }

    @Override
    public AState getGoalState() {
        return this.goalState;
    }

    /**
     * all optional 1 block moves in the maze from the given state
     *
     * @param state a valid state in the problem
     * @return all the possible next states of the given state
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> possibleStates = new ArrayList<>();
        if (state == null)
            return possibleStates;
        Position3D currentPosition = (Position3D) state.getCurrentState();
        if (maze.isValidPosition(currentPosition.getUpPosition()) && !maze.IsWall(currentPosition.getUpPosition())) // UP
            possibleStates.add(new Maze3DState(state, currentPosition.getUpPosition(), 0));
        if (maze.isValidPosition(currentPosition.getRightPosition()) && !maze.IsWall(currentPosition.getRightPosition())) // RIGHT
            possibleStates.add(new Maze3DState(state, currentPosition.getRightPosition(), 0));
        if (maze.isValidPosition(currentPosition.getDownPosition()) && !maze.IsWall(currentPosition.getDownPosition())) // DOWN
            possibleStates.add(new Maze3DState(state, currentPosition.getDownPosition(), 0));
        if (maze.isValidPosition(currentPosition.getLeftPosition()) && !maze.IsWall(currentPosition.getLeftPosition())) // LEFT
            possibleStates.add(new Maze3DState(state, currentPosition.getLeftPosition(), 0));
        if (maze.isValidPosition(currentPosition.getHigherPosition()) && !maze.IsWall(currentPosition.getHigherPosition())) // HIGH
            possibleStates.add(new Maze3DState(state, currentPosition.getHigherPosition(), 0));
        if (maze.isValidPosition(currentPosition.getLowerPosition()) && !maze.IsWall(currentPosition.getLowerPosition())) //LOW
            possibleStates.add(new Maze3DState(state, currentPosition.getLowerPosition(), 0));

        return possibleStates;
    }
}