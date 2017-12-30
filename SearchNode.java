import java.util.*;

public class SearchNode
{
    // Board state of this search node
    private Board board; 
    // Predecessor of this search node
    private SearchNode parent;
    // f(n) = g(n) + h(n)
    private int f;
    // Cost of path to get to this state
    private int g;
    // Estimated remaining cost to the goal state, given by the heuristic function
    private int h;
    
    public SearchNode()
    {
        board = null;
        parent = null;
        f = 0;
        g = 0;
        h = 0;
    }
    
    public SearchNode(Board b, SearchNode p, int gCost, int hCost) 
    {
        board = b;
        parent = p;
        g = gCost;
        h = hCost;
        f = g + h;
    }
    
    public boolean equals(SearchNode s) {
        return board.equals(s.getBoard());
    }
    
    public String toString() {
        String str = "";
        
        int[][] b = board.getBoard();
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                str += b[i][j] + " ";
            }
        }
        
        str += "\n";
        return str;
    }
    
    // Get the path to this SearchNode 
    public Stack<SearchNode> getPath() {
        Stack<SearchNode> currentPath = new Stack<SearchNode>();
        
        SearchNode currentParent = this;
        
        while(currentParent != null) {
            currentPath.push(currentParent);
            currentParent = currentParent.getParent();
        }
        
        return currentPath;
    }
    
    public Board getBoard() {
        return board;
    }
    
    public SearchNode getParent() {
        return parent;
    }
    
    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }
    
    public int getH() {
        return h;
    }
}
