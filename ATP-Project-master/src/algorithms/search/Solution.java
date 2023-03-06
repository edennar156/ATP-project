package algorithms.search;
import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {
    private ArrayList<AState> solution;

    /**
     *
     * @param solution
     */
    public Solution(ArrayList<AState> solution) {
        this.solution = solution;
    }

    /**
     *
     * @param s
     * adds to the array new state s
     */

    public void add_to_array(AState s){
        if (s==null){return;}
        solution.add(s);
    }

    /**
     *
     * @return the list of all the posible states
     */
    public ArrayList<AState> getSolutionPath(){
        if (solution.isEmpty()){
            System.out.println("this maze has no solution");
        }
        return solution;}
}