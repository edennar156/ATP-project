package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    protected String name;
    protected int NumberOfNodesEvaluated;

    public ASearchingAlgorithm() {
        this.NumberOfNodesEvaluated =0;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setNumberOfNodesEvaluated(int amount) {
        NumberOfNodesEvaluated =amount;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return NumberOfNodesEvaluated;
    }

    @Override
    public Solution solve(ISearchable s) throws Exception {
        return null;
    }
}
