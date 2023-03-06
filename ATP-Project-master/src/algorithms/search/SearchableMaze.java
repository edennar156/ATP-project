package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {
    private final Maze maze;
    private final MazeState startState, goalState;

    public SearchableMaze(Maze maze) throws IllegalArgumentException {
        if (maze == null)
            throw new IllegalArgumentException("cant handle null maze");
        this.maze = maze;
        this.startState = new MazeState(null, maze.getStartPosition(), 0);
        this.goalState = new MazeState(null, maze.getGoalPosition(), 0);
    }

    public Maze getMaze() {
        return maze;
    }

    @Override
    public AState getStartState() {
        return this.startState;
    }

    @Override
    public AState getGoalState() {
        return this.goalState;
    }


    @Override
    public ArrayList<AState> getAllPossibleStates(AState state) {
        ArrayList<AState> possibleStates = new ArrayList<>();
        if (state == null)
            return possibleStates;
        Position currentPosition = (Position) state.getCurrentState();

        boolean up = maze.IsWall(currentPosition.getUpPosition()),
                down = maze.IsWall(currentPosition.getDownPosition()),
                right = maze.IsWall(currentPosition.getRightPosition()),
                left = maze.IsWall(currentPosition.getLeftPosition());

        // Right-Up
        if (maze.validMazePosition(currentPosition.getRightUpPosition()) && !maze.IsWall(currentPosition.getRightUpPosition()) && (!up || !right))
            possibleStates.add(new MazeState(state, currentPosition.getRightPosition(), state.getCost() + 15));

        // Right-Down
        if (maze.validMazePosition(currentPosition.getRightDownPosition()) && !maze.IsWall(currentPosition.getRightDownPosition()) && (!down || !right))
            possibleStates.add(new MazeState(state, currentPosition.getRightDownPosition(), state.getCost() + 15));

        // Left-Up
        if (maze.validMazePosition(currentPosition.getLeftUpPosition()) && !maze.IsWall(currentPosition.getLeftUpPosition()) && (!up || !left))
            possibleStates.add(new MazeState(state, currentPosition.getLeftUpPosition(), state.getCost() + 15));

        // Left-Down
        if (maze.validMazePosition(currentPosition.getLeftDownPosition()) && !maze.IsWall(currentPosition.getLeftDownPosition()) && (!down || !left))
            possibleStates.add(new MazeState(state, currentPosition.getLeftDownPosition(), state.getCost() + 15));

        // Up
        if (maze.validMazePosition(currentPosition.getUpPosition()) && !up)
            possibleStates.add(new MazeState(state, currentPosition.getUpPosition(), state.getCost() + 10));

        // Down
        if (maze.validMazePosition(currentPosition.getDownPosition()) && !down)
            possibleStates.add(new MazeState(state, currentPosition.getDownPosition(), state.getCost() + 10));

        // Right
        if (maze.validMazePosition(currentPosition.getRightPosition()) && !right)
            possibleStates.add(new MazeState(state, currentPosition.getRightPosition(), state.getCost() + 10));

        // Left
        if (maze.validMazePosition(currentPosition.getLeftPosition()) && !left)
            possibleStates.add(new MazeState(state, currentPosition.getLeftPosition(), state.getCost() + 10));

        return possibleStates;
    }
}