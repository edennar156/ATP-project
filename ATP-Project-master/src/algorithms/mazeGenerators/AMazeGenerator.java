package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {
    @Override
    public long measureAlgorithmTimeMillis(int r, int c) {
        long start = System.currentTimeMillis();
        generate(r,c);
        long finish = System.currentTimeMillis();
        return finish-start;
    }
}
