package algorithms.maze3D;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;
import java.util.Random;

public class Maze3D {

    /**
     * Maze attributes
     */
    public static final int WALL = 1;
    public static final int TRAN = 0;
    private int[][][] maze;
    private Position3D start;
    private Position3D goal;

    public Maze3D(int[][][] maze, Position3D s, Position3D g) {
        this.maze = maze;
        this.start = s;
        this.goal = g;
    }

    public Maze3D(int depth, int row, int col) throws IllegalArgumentException {
        if (depth < 2 || row < 2 || col < 2) {
            throw new IllegalArgumentException();
        }
        this.maze = new int[depth][row][col];
        this.start = new Position3D(0, 0, 0);
        this.goal = new Position3D(depth - 1, row - 1, col - 1);
    }

    public int[][][] getMap() {
        return this.maze;
    }

    public Position3D getStartPosition() {
        return this.start;
    }

    public Position3D getGoalPosition() {
        return this.goal;
    }

    // The implementation of the maze 3d is the same as the maze 2d imp.
    // because we are using the same algorithms in order to generate the maze, (with some adaptions)
    // the following method will be the same.
    // we cannot re-use the code from project's first part (maze 2d) because it is not capable.

    public void TranInitialize() {
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                for (int k = 0; k < this.maze[0][0].length; k++) {
                    this.maze[i][j][k] = TRAN; // transition = 0;
                }
            }
        }
    }

    public void WallInitialize() {
        for (int i = 0; i < this.maze.length; i++) {
            for (int j = 0; j < this.maze[0].length; j++) {
                for (int k = 0; k < this.maze[0][0].length; k++) {
                    this.maze[i][j][k] = WALL; // wall = 1;
                }
            }
        }
    }

    public void SetTransition(Position3D position) {
//        if (isValidPosition(position)) {
            this.maze[position.getDepthIndex()][position.getRowIndex()][position.getColumnIndex()] = TRAN;
//        }
    }

    public ArrayList<Position3D> GetWallNeighbour(Position3D currentPosition) {
        ArrayList<Position3D> wallsList = new ArrayList<>();
        if (currentPosition != null) {
            Position3D up = currentPosition.getUpPosition();
            if (this.isValidPosition(up) && IsWall(up)) //UP
                wallsList.add(up);
            Position3D right = currentPosition.getRightPosition();
            if (this.isValidPosition(right) && IsWall(right)) //RIGHT
                wallsList.add(right);
            Position3D down = currentPosition.getDownPosition();
            if (this.isValidPosition(down) && IsWall(down)) //DOWN
                wallsList.add(down);
            Position3D left = currentPosition.getLeftPosition();
            if (this.isValidPosition(left) && IsWall(left)) //LEFT
                wallsList.add(left);
            Position3D high = currentPosition.getHigherPosition();
            if (this.isValidPosition(high) && IsWall(down)) //DOWN
                wallsList.add(down);
            Position3D low = currentPosition.getLowerPosition();
            if (this.isValidPosition(low) && IsWall(left)) //LEFT
                wallsList.add(left);
        }
        return wallsList;
    }

    public ArrayList<Position3D> GetTransitionNeighbour(Position3D currentPosition) {
        ArrayList<Position3D> wallsList = new ArrayList<>();
        if (currentPosition != null) {
            Position3D up = currentPosition.getUpPosition();
            if (this.isValidPosition(up) && !IsWall(up)) //UP
                wallsList.add(up);
            Position3D right = currentPosition.getRightPosition();
            if (this.isValidPosition(right) && !IsWall(right)) //RIGHT
                wallsList.add(right);
            Position3D down = currentPosition.getDownPosition();
            if (this.isValidPosition(down) && !IsWall(down)) //DOWN
                wallsList.add(down);
            Position3D left = currentPosition.getLeftPosition();
            if (this.isValidPosition(left) && !IsWall(left)) //LEFT
                wallsList.add(left);
            Position3D high = currentPosition.getHigherPosition();
            if (this.isValidPosition(high) && !IsWall(down)) //DOWN
                wallsList.add(down);
            Position3D low = currentPosition.getLowerPosition();
            if (this.isValidPosition(low) && !IsWall(left)) //LEFT
                wallsList.add(left);
        }
        return wallsList;
    }

    public ArrayList<Position3D> wallsTwoStepsAway(Position3D currentPosition) {
        ArrayList<Position3D> wallsList = new ArrayList<>();
        if (currentPosition != null) {
            Position3D up = currentPosition.getUpPosition().getUpPosition();
            if (this.isValidPosition(up) && IsWall(up))
                wallsList.add(up);
            Position3D right = currentPosition.getRightPosition().getRightPosition();
            if (this.isValidPosition(right) && IsWall(right))
                wallsList.add(right);
            Position3D down = currentPosition.getDownPosition().getDownPosition();
            if (this.isValidPosition(down) && IsWall(down))
                wallsList.add(down);
            Position3D left = currentPosition.getLeftPosition().getLeftPosition();
            if (this.isValidPosition(left) && IsWall(left))
                wallsList.add(left);
            Position3D high = currentPosition.getHigherPosition().getHigherPosition();
            if (this.isValidPosition(high) && IsWall(high))
                wallsList.add(high);
            Position3D low = currentPosition.getLowerPosition().getLowerPosition();
            if (this.isValidPosition(low) && IsWall(low))
                wallsList.add(low);
        }
        return wallsList;
    }

    public boolean IsWall(Position3D position) {
        return (isValidPosition(position) && this.maze[position.getDepthIndex()][position.getRowIndex()][position.getColumnIndex()] == WALL);
    }

    public void connectNeighbours(Position3D currentPosition, Position3D neighbour) throws IllegalArgumentException {
        if (!isValidPosition(currentPosition) || !isValidPosition(neighbour))
            throw new IllegalArgumentException();

        // same layer (implementation as if it was 2d maze)
        if (currentPosition.getDepthIndex() == neighbour.getDepthIndex()) {
            if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
                this.SetTransition(new Position3D(currentPosition.getDepthIndex(), currentPosition.getRowIndex(), Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1));
            } else if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
                this.SetTransition(new Position3D(currentPosition.getDepthIndex(), Math.min(neighbour.getRowIndex(), currentPosition.getRowIndex()) + 1, currentPosition.getColumnIndex()));
            }

            // same layer (row)
        } else if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
            if (currentPosition.getDepthIndex() == neighbour.getDepthIndex()) {
                this.SetTransition(new Position3D(currentPosition.getDepthIndex(), currentPosition.getRowIndex(), Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1));
            } else if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
                this.SetTransition(new Position3D(Math.min(currentPosition.getDepthIndex(), neighbour.getDepthIndex()) + 1, currentPosition.getRowIndex(), currentPosition.getColumnIndex()));
            }

            // same layer (col)
        } else if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
            if (currentPosition.getDepthIndex() == neighbour.getDepthIndex()) {
                this.SetTransition(new Position3D(currentPosition.getDepthIndex(), Math.min(currentPosition.getRowIndex(), neighbour.getRowIndex()) + 1, Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1));
            } else if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
                this.SetTransition(new Position3D(Math.min(currentPosition.getDepthIndex(), neighbour.getDepthIndex()) + 1, currentPosition.getRowIndex(), currentPosition.getColumnIndex()));
            }
        }
        else {}
    }

    public void setGoalPosition() {
        Random rnd = new Random();
        ArrayList<Position3D> goalPositionOptions = new ArrayList<>();
        int depthSize = this.getMazeNumOfDepth();
        int columnsSize = this.getMazeNumOfCols();
        int rowSize = this.getMazeNumOfRows();

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < columnsSize; j++) {
                if (this.maze[0][i][j] == TRAN)
                    goalPositionOptions.add(new Position3D(0, i, j));
                if (this.maze[depthSize - 1][i][j] == TRAN)
                    goalPositionOptions.add(new Position3D(depthSize - 1, i, j));
            }
        }
        for (int i = 0; i < depthSize; i++) {
            for (int j = 0; j < columnsSize; j++) {
                if (this.maze[i][0][j] == TRAN)
                    goalPositionOptions.add(new Position3D(i, 0, j));
                if (this.maze[i][rowSize - 1][j] == TRAN)
                    goalPositionOptions.add(new Position3D(i, rowSize - 1, j));
            }
        }
        for (int i = 0; i < depthSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                if (this.maze[i][j][0] == TRAN)
                    goalPositionOptions.add(new Position3D(i, j, 0));
                if (this.maze[i][j][columnsSize - 1] == TRAN)
                    goalPositionOptions.add(new Position3D(i, j, columnsSize - 1));
            }
        }
        if (goalPositionOptions.size() <= 1)
            throw new RuntimeException();

        do
            this.goal = (goalPositionOptions.get(rnd.nextInt(goalPositionOptions.size())));
        while (this.getStartPosition().equals(this.getGoalPosition()));
    }

    private int getMazeNumOfDepth(){
        return this.maze.length;
    }

    private int getMazeNumOfRows() {
        return this.maze[0].length;
    }

    private int getMazeNumOfCols() {
        return this.maze[0][0].length;
    }

    public boolean isValidPosition(Position3D position) {
//        return true;
        return (position != null &&
                0 <= position.getRowIndex() && position.getRowIndex() < this.getMazeNumOfRows() &&
                0 <= position.getColumnIndex() && position.getColumnIndex() < this.getMazeNumOfCols() &&
                0 <= position.getDepthIndex() && position.getDepthIndex() < this.getMazeNumOfDepth()
        );
    }
}