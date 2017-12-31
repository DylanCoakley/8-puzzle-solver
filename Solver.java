//**********************************************************
// This program takes as input the initial state of the 8-puzzle
// and uses A* search to find a path from the initial state to
// the goal state, given it is possible. If it is not, the program
// will indicate that it is not solvable. If a solution is found,
// the program will output one line for each state in the path
// from the initial to the goal state.
// **********************************************************

import java.util.*;

public class Solver
{
    public void search(Board board) {
        // Initial depth
        int depth = 0;
        // Set the initial board as the starting SearchNode
        // It has no parent, g(n) = 0, and h(n) = manhattan distance
        SearchNode rootNode = new SearchNode(board, null, depth, board.getMisplaced());
        // fringe sorted by increasing cost of f(n)
        PriorityQueue<SearchNode> fringe = new PriorityQueue<SearchNode>(new NodeComparator());
        // closed list that contains already explored nodes
        ArrayList<SearchNode> closed = new ArrayList<SearchNode>();
        // add the initial state to the fringe
        fringe.add(rootNode);

        while(!fringe.isEmpty()) {

            // Get node from fringe with lowest f(n)
            SearchNode currentNode = fringe.remove();

            if(!currentNode.getBoard().isGoalBoard()) {

                // Find the children of the current node
                ArrayList<Board> childrenBoards = currentNode.getBoard().findChildren();
                ArrayList<SearchNode> children = new ArrayList<SearchNode>();
                // Increase depth
                depth++;

                for(Board b : childrenBoards) {
                    SearchNode childNode = new SearchNode(b, currentNode, depth, b.getMisplaced());
                    children.add(childNode);
                }

                // Remove explored nodes
                for(SearchNode s : children) {
                    if(closed.contains(s) || fringe.contains(s)) {
                        children.remove(s);
                    }
                }

                // Add the current node to the closed list and remove it from the fringe
                closed.add(currentNode);
                fringe.remove(currentNode);

                if(children.isEmpty()) {
                    continue;
                }

                // Add children of the current node to fringe
                fringe.addAll(children);

            } else {
                // Current search node contains a goal state, display path
                System.out.println("-----------------");
                Stack<SearchNode> solutionPath = currentNode.getPath();

                while(!solutionPath.isEmpty()) {
                    System.out.println(solutionPath.peek());
                    solutionPath.pop();
                }

                break;
            }
        }
    }

    class NodeComparator implements Comparator<SearchNode>
    {
        public int compare(SearchNode n1, SearchNode n2)
        {
            return n1.getF() - n2.getF();
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[][] a = new int[3][3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                a[i][j] = input.nextInt();
            }
        }

        input.close();

        Board b = new Board(a);

        if(!b.isSolvable()) {
            System.out.println("The goal state cannot be reached from this initial state!");
        } else {
            Solver solver = new Solver();
            solver.search(b);
        }
    }
}
