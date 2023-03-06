package algorithms.search;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm{
    private final Stack<AState> stack;
    public DepthFirstSearch() {
        super();
        this.stack=new Stack<>();
    }

    /**
     *
     * @return solution
     * create a stack for backtracking
     * set current cell to 0
     * set visited cells to 0
     *
     * while current cell not goal
     *     get unvisited neighbors using cell_neighbors
     *     if at least one neighbor
     *         choose random neighbor to be new cell
     *         visit new cell using visit_cell
     *         push current cell to stack
     *         set current cell to new cell
     *         add 1 to visited cells
     *     else
     *         backtrack current cell using backtrack method
     *         pop from stack to current cell
     *     call refresh_maze_view to update visualization
     * set state to 'idle'
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
        stack.add(start);
        have_been_visited.add(start.toString());

        while (!stack.isEmpty()&&flag){
            curr=stack.pop();
            legal_positions=s.getAllPossibleStates(curr);
            if(curr.equals(goal)){
                flag=false;
            }

            for (AState legal_position : legal_positions) {
                if (!have_been_visited.contains(legal_position.toString())) {
                    stack.add(legal_position);
                    have_been_visited.add(legal_position.toString());
                }
            }
        }
        setNumberOfNodesEvaluated(have_been_visited.size());
        return getSolution(sol, start, goal, temp, curr);
    }

    static Solution getSolution(Stack<AState> sol, AState start, AState goal, ArrayList<AState> temp, AState curr) {
        if (curr.equals(goal)){
            while (curr.getFrom()!=null){
                sol.push(curr);
                curr=curr.getFrom();
            }
        }
        if (sol.empty()){
            return new Solution(new ArrayList<>());
        }
        sol.push(start);

        while (!sol.empty()){
            temp.add(sol.pop());
        }
        return new Solution(temp);
    }

    @Override
    public String getName() {
        return "Depth First Search";
    }
}