package algorithms.maze3D;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MyMaze3DGenerator extends AMaze3DGenerator {
    @Override
    public Maze3D generate(int depth, int row, int column) {
        return DfsMaze3dGenerator(depth, row, column);
    }

    private Maze3D DfsMaze3dGenerator(int depth, int rows, int columns) {
        Random rnd = new Random();
        Maze3D maze = new Maze3D(depth, rows, columns);
        Stack<Position3D> neighbours = new Stack<>();
        ArrayList<Position3D> neighbourWalls;
        maze.WallInitialize();
        Position3D currentPosition = maze.getStartPosition();
        maze.SetTransition(currentPosition);
        neighbours.push(currentPosition);
        while (!neighbours.isEmpty()) {
            currentPosition = neighbours.pop();
            neighbourWalls = maze.wallsTwoStepsAway(currentPosition);
            if (neighbourWalls.size() != 0) {
                neighbours.push(currentPosition);
                Position3D randNeighbour = neighbourWalls.get(rnd.nextInt(neighbourWalls.size()));
                maze.SetTransition(randNeighbour);
                maze.connectNeighbours(currentPosition, randNeighbour);
                neighbours.push(randNeighbour);
            }
        }
        maze.setGoalPosition();
        return maze;
    }
}
