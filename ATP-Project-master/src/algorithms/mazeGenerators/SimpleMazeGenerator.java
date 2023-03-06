package algorithms.mazeGenerators;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int r, int c) {
        // Init settings (arrange).
        Random rnd = new Random();
        Maze maze = new Maze(r, c);
        Position currentPosition; // Empty pos.

        //TODO: generate (randomly) start/goal positions for the maze.
        // be aware this functions are not exist I have just wrote the names here inside the comments.

        //maze.randomlyGenerateStartPosition(); // nit: will be on the left-top sides.
        //maze.randomlyGenerateGoalPosition();  // nit: will be in the right-down sides.

        maze.WallInitialize();

        currentPosition = maze.getStartPosition();
        maze.SetTransition(currentPosition);
        maze.SetTransition(maze.getGoalPosition());

        while (!currentPosition.equals(maze.getGoalPosition())) {
            if (rnd.nextInt(2) == 0) {
                if (currentPosition.getRowIndex() > maze.getGoalPosition().getRowIndex())
                    currentPosition = currentPosition.getUpPosition();
                if (currentPosition.getRowIndex() < maze.getGoalPosition().getRowIndex())
                    currentPosition = currentPosition.getDownPosition();
            } else {
                if (currentPosition.getColumnIndex() > maze.getGoalPosition().getColumnIndex())
                    currentPosition = currentPosition.getLeftPosition();
                if (currentPosition.getColumnIndex() < maze.getGoalPosition().getColumnIndex())
                    currentPosition = currentPosition.getRightPosition();
            }

            maze.SetTransition(currentPosition);
        }
        // Randomly break walls for make the grid more complicated.
        // can make it more clever by change the rnd settings here.
        // For example, make transitions only for a block that is part of some path etc.

        for (int i = 0; i < maze.getMazeNumOfRows(); i++) {
            for (int j = 0; j < maze.getMazeNumOfCols(); j++) {
                if (rnd.nextInt(2) == 0)
                    maze.SetTransition(new Position(i, j));
            }
        }
        return maze;
    }
}
