package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMazeGenerator extends AMazeGenerator {
    /**
     * generates maze using DFS
     */
    @Override
    public Maze generate(int r, int c) {
        return DfsMazeGenerator(r, c);
    }

    private Maze DfsMazeGenerator(int rows, int columns) {
        // Arrange
        Random rnd = new Random();
        Maze maze = new Maze(rows, columns);
        Stack<Position> neighbours = new Stack<>();
        ArrayList<Position> neighbourWalls;
        maze.WallInitialize();

        // Pushing the first position into the stack
        Position currentPosition = maze.getStartPosition();
        maze.SetTransition(currentPosition);
        neighbours.push(currentPosition);

        while (!neighbours.isEmpty()) { // While the neighbours stack is not empty
            currentPosition = neighbours.pop();
            neighbourWalls = maze.wallsTwoStepsAway(currentPosition);

            if (neighbourWalls.size() != 0) {
                neighbours.push(currentPosition);
                Position randNeighbour = neighbourWalls.get(rnd.nextInt(neighbourWalls.size()));
                maze.SetTransition(randNeighbour);
                maze.connectNeighbours(currentPosition, randNeighbour);
                neighbours.push(randNeighbour);
            }
        }
        maze.setGoalPosition();
        return maze;
    }
}