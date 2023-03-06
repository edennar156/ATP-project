package algorithms.mazeGenerators;

public interface IMazeGenerator {
    public Maze generate(int r, int c);
    public long measureAlgorithmTimeMillis(int r, int c);
}
