package algorithms.search;
import java.util.ArrayList;

public interface ISearchable {
    AState getGoalState();
    AState getStartState();
    ArrayList<AState> getAllPossibleStates(AState s);
}
