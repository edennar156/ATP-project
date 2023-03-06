package algorithms.search;
import java.util.*;
import static algorithms.search.DepthFirstSearch.getSolution;

public class BreadthFirstSearch extends ASearchingAlgorithm{
    protected Queue<AState> q;

    public BreadthFirstSearch() {
        super();
        this.q= new LinkedList<>();
    }

    // Constructor for Best-first-search set up:
    public BreadthFirstSearch(Queue<AState> q){
        super();
        this.q = q;
    }

    /**
     * @return solution
     * @throws Exception
     * bfs(start, looking_for)
     *   create arrays (node_queue, visited_nodes, and traveled_path)
     *   add the start to the arrays
     *   while the queue is not empty
     *     take out the first element in the queue
     *     for each of the neighbors of this first element
     *       if its not in the visited set and not blocked
     *         add this to the arrays
     *         if this contains what we are looking for
     *return the backtrack of this node
     */

    @Override
    public Solution solve(ISearchable s) throws Exception {
        if (s == null){
            throw new Exception();
        }
        AState start = s.getStartState();
        AState goal=s.getGoalState();
        ArrayList<AState> temp=new ArrayList<>();
        AState curr=s.getStartState();
        ArrayList<AState> legal_positions;
        Stack<AState> sol=new Stack<>();
        HashSet<String> have_been_visited=new HashSet<>();
        boolean flag = true;
        q.add(start);
        have_been_visited.add(start.toString());

        while (!q.isEmpty()&&flag){
            curr=q.poll(); // polling the first element (Dequeue).
            legal_positions=s.getAllPossibleStates(curr);
            if(curr.equals(goal)){
                flag=false;
            }

            for (AState legal_position : legal_positions) {
                if (!have_been_visited.contains(legal_position.toString())) {
                    q.add(legal_position);
                    have_been_visited.add(legal_position.toString());
                }
            }
        }
        setNumberOfNodesEvaluated(have_been_visited.size());
        return getSolution(sol, start, goal, temp, curr);
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }
}