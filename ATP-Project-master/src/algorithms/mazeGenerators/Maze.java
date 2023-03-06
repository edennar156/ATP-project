package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * Maze class
 */
public class Maze implements Serializable {
    /**
     * Maze attributes
     */
    public static final int BASE = 127;
    public static final int WALL = 1;
    public static final int TRAN = 0;
    private int[][] maze;
    private Position start;
    private Position goal;

    /**
     * C-tor // given row-col
     *
     * @param rows:    num of desire rows in maze.
     * @param columns: num of desire cols in maze.
     * @throws IllegalArgumentException in case of given args are illegal- out of bound.
     */
    public Maze(int rows, int columns) {
        this.maze = new int[rows][columns];
        this.start = new Position(0, 0);
        this.goal = new Position(rows - 1, columns - 1);
    }

    /**
     * C-tor // given explicit args
     *
     * @param maze:  maze grid- 1/0 grid represents a maze.
     * @param start: start pos // starting point of the maze.
     * @param goal:  goal pos // finish point of the maze.
     */
    public Maze(int[][] maze, Position start, Position goal) {
        this.maze = maze;
        this.start = start;
        this.goal = goal;
    }

    public Maze(byte[] bytes) throws IllegalArgumentException {
        int k = 12;
        try {
            this.maze = new int[baseConversionByteToInt(bytes[0], bytes[1])][baseConversionByteToInt(bytes[2], bytes[3])];
            this.start = new Position(baseConversionByteToInt(bytes[4], bytes[5]), baseConversionByteToInt(bytes[6], bytes[7]));
            this.goal = new Position(baseConversionByteToInt(bytes[8],bytes[9]), baseConversionByteToInt(bytes[10],bytes[11]));
            for (int i=0; i<this.maze.length; i++){
                for(int j=0; j<this.maze[0].length; j++)
                    this.maze[i][j] = bytes[k++];
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public int getMazeNumOfRows() {
        return maze.length;
    }

    public int getMazeNumOfCols() {
        return maze[0].length;
    }

    public int[][] getMaze() {
        return maze;
    }

    public Position getStartPosition() {
        return start;
    }

    public Position getGoalPosition() {
        return goal;
    }

    // Init maze with transitions "0"
    public void TranInitialize() {
        for (int i = 0; i < this.getMazeNumOfRows(); ++i) {
            for (int j = 0; j < this.getMazeNumOfCols(); ++j) {
                // transition => 0 equality (final).
                maze[i][j] = TRAN;
            }
        }
    }

    // Check position inside array bounds and position is legal.
    public boolean validMazePosition(Position position) {
        return (position != null &&
                0 <= position.getRowIndex() && position.getRowIndex() < this.getMazeNumOfRows() &&
                0 <= position.getColumnIndex() && position.getColumnIndex() < this.getMazeNumOfCols());
    }

    // Init maze with walls "1"
    public void WallInitialize() {
        for (int i = 0; i < this.getMazeNumOfRows(); ++i) {
            for (int j = 0; j < this.getMazeNumOfCols(); ++j) {
                // Wall => 1 equality (final).
                maze[i][j] = WALL;
            }
        }
    }

    // Set "0" = break the wall in current position
    public void SetTransition(Position position) {
        if (position.getRowIndex() >= 0 && position.getColumnIndex() >= 0)
            this.maze[position.getRowIndex()][position.getColumnIndex()] = TRAN;
    }

    public static final String RED = "\033[0;31m";      // RED
    public static final String RESET = "\033[0m";       // Text Reset

    // Print the maze in needed format
    public void print() {
        for (int i = 0; i < this.getMazeNumOfRows(); i++) {
            System.out.print("{");
            for (int j = 0; j < this.getMazeNumOfCols(); j++) {
                if (this.start.equals(new Position(i, j)))
                    System.out.print(RED + " S" + RESET);
                else if (this.goal.equals(new Position(i, j)))
                    System.out.print(RED + " E" + RESET);
                else
                    System.out.print(" " + this.maze[i][j]);
            }
            System.out.println(" }");
        }
    }

    // WALL neighbours
    public ArrayList<Position> GetWallNeighbour(Position currentPosition) {
        ArrayList<Position> wallsList = new ArrayList<>();
        if (currentPosition != null) {
            Position up = currentPosition.getUpPosition();
            if (this.validMazePosition(up) && IsWall(up)) //UP
                wallsList.add(up);
            Position right = currentPosition.getRightPosition();
            if (this.validMazePosition(right) && IsWall(right)) //RIGHT
                wallsList.add(right);
            Position down = currentPosition.getDownPosition();
            if (this.validMazePosition(down) && IsWall(down)) //DOWN
                wallsList.add(down);
            Position left = currentPosition.getLeftPosition();
            if (this.validMazePosition(left) && IsWall(left)) //LEFT
                wallsList.add(left);
        }
        return wallsList;
    }

    // TRAN neighbours
    public ArrayList<Position> GetTransitionNeighbour(Position currentPosition) {
        ArrayList<Position> wallsList = new ArrayList<>();
        if (currentPosition != null) {
            Position up = currentPosition.getUpPosition();
            if (this.validMazePosition(up) && !IsWall(up)) //UP
                wallsList.add(up);
            Position right = currentPosition.getRightPosition();
            if (this.validMazePosition(right) && !IsWall(right)) //RIGHT
                wallsList.add(right);
            Position down = currentPosition.getDownPosition();
            if (this.validMazePosition(down) && !IsWall(down)) //DOWN
                wallsList.add(down);
            Position left = currentPosition.getLeftPosition();
            if (this.validMazePosition(left) && !IsWall(left)) //LEFT
                wallsList.add(left);
        }
        return wallsList;
    }

    // Using as sub-function for the DFS algorithm
    public ArrayList<Position> wallsTwoStepsAway(Position currentPosition) {
        ArrayList<Position> wallsList = new ArrayList<>();
        if (currentPosition != null) {
            Position up = currentPosition.getUpPosition().getUpPosition();
            if (this.validMazePosition(up) && IsWall(up))
                wallsList.add(up);
            Position right = currentPosition.getRightPosition().getRightPosition();
            if (this.validMazePosition(right) && IsWall(right))
                wallsList.add(right);
            Position down = currentPosition.getDownPosition().getDownPosition();
            if (this.validMazePosition(down) && IsWall(down))
                wallsList.add(down);
            Position left = currentPosition.getLeftPosition().getLeftPosition();
            if (this.validMazePosition(left) && IsWall(left))
                wallsList.add(left);
        }
        return wallsList;
    }

    // True - if the given position is "1"
    // False - if the given position is "0"
    public boolean IsWall(Position position) {
        if (validMazePosition(position)) {
            return maze[position.getRowIndex()][position.getColumnIndex()] == 1;
        }
        return false;
    }

    // Connect two positions
    public void connectNeighbours(Position currentPosition, Position neighbour) throws IllegalArgumentException {
        if (!this.validMazePosition(currentPosition)) {
            throw new IllegalArgumentException();
        }
        if (currentPosition.getColumnIndex() == neighbour.getColumnIndex()) {
            this.SetTransition(new Position(Math.min(neighbour.getRowIndex(), currentPosition.getRowIndex()) + 1, currentPosition.getColumnIndex()));
        } else if (currentPosition.getRowIndex() == neighbour.getRowIndex()) {
            this.SetTransition(new Position(currentPosition.getRowIndex(), Math.min(neighbour.getColumnIndex(), currentPosition.getColumnIndex()) + 1));
        }
    }

    public void setGoalPosition() {
        Random rnd = new Random();
        ArrayList<Position> goalPositionOptions = new ArrayList<>();
        int columnsSize = this.getMazeNumOfCols();
        int rowSize = this.getMazeNumOfRows();

        for (int i = 0; i < columnsSize; i++) {
            if (this.maze[0][i] == TRAN)
                goalPositionOptions.add(new Position(0, i));
            if (this.maze[rowSize - 1][i] == TRAN)
                goalPositionOptions.add(new Position(rowSize - 1, i));
        }

        for (int i = 0; i < rowSize; i++) {
            if (this.maze[i][0] == TRAN)
                goalPositionOptions.add(new Position(i, 0));
            if (this.maze[i][columnsSize - 1] == TRAN)
                goalPositionOptions.add(new Position(i, columnsSize - 1));
        }
        if (goalPositionOptions.size() <= 1)
            throw new RuntimeException();

        do
            this.goal = (goalPositionOptions.get(rnd.nextInt(goalPositionOptions.size())));
        while (this.getStartPosition().equals(this.getGoalPosition()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze1 = (Maze) o;
        return Arrays.equals(maze, maze1.maze) &&
                start.equals(maze1.start) &&
                goal.equals(maze1.goal);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(start, goal);
        result = 31 * result + Arrays.hashCode(maze);
        return result;
    }

    public byte[] toByteArray() {
        byte[] finalDataEncryption = soldering(baseConversionIntToByte(this.getMazeNumOfRows()), baseConversionIntToByte(this.getMazeNumOfCols()));

        byte[] encryptedStartPosition = soldering(baseConversionIntToByte(this.getStartPosition().getRowIndex()),
                baseConversionIntToByte(this.getStartPosition().getColumnIndex()));

        byte[] encryptedGoalPosition = soldering(baseConversionIntToByte(this.getGoalPosition().getRowIndex()),
                baseConversionIntToByte(this.getGoalPosition().getColumnIndex()));

        finalDataEncryption = soldering(finalDataEncryption, encryptedStartPosition);
        finalDataEncryption = soldering(finalDataEncryption, encryptedGoalPosition);

        byte[] gridAsArray = new byte[this.getMazeNumOfRows() * this.getMazeNumOfCols()];

        int k = 0;
        for (int i = 0; i < this.getMazeNumOfRows(); i++) {
            for (int j = 0; j < this.getMazeNumOfCols(); j++)
                gridAsArray[k++] = (byte) this.maze[i][j];
        }
        return soldering(finalDataEncryption, gridAsArray);
    }

    // Every number can be represent in 127 base (natural 128 numbers in byte representation).
    // Only this base conversion is optimal. (Large numbers [positive]128*127 +127 = 16388, small amount of bytes 2).
    public byte[] baseConversionIntToByte(int number) {
        byte[] sol = new byte[2];
        sol[0] = (byte) (number % BASE);
        sol[1] = (byte) ((number / BASE) % BASE);
        return sol;
    }

    public int baseConversionByteToInt(byte l, byte m) {
        // Base conversion works the same for second direction.
        // Maximum number to represent 16,388 (unsigned is above 32,000).
        // Other direction will be useful decryption, which basically done at construction.
        return m * BASE + l;
    }

    public byte[] soldering(byte[] first, byte[] second) {
        byte[] newArray = new byte[first.length + second.length];
        System.arraycopy(first, 0, newArray, 0, first.length);
        System.arraycopy(second, 0, newArray, first.length, second.length);
        return newArray;
    }
}