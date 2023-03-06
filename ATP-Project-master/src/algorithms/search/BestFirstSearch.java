package algorithms.search;
import java.util.*;

public class BestFirstSearch extends  BreadthFirstSearch{

    public BestFirstSearch() {
        super(new PriorityQueue<>(new costComparator()));
    }

    @Override
    public String getName() {
        return "Best First Search";
    }
}

class costComparator implements Comparator<AState>{

    @Override
    public int compare(AState o1, AState o2) {
        if (o1 == null || o2 == null)
            throw new IllegalArgumentException();
        return Double.compare(o1.getCost(), o2.getCost());
    }
}