import java.util.*;

public class Board
{
    private final int N = 3;
    private final int ONE = 1;
    private final int TWO = 2;
    private final int THREE = 3;
    private final int FOUR = 4;
    private final int FIVE = 5;
    private final int SIX = 6;
    private final int SEVEN = 7;
    private final int EIGHT = 8;
    private final int BLANK = 9;
    private int[][] board;
    private int misplaced;
    private final int[][] goalBoard = new int[][] {
        {ONE, TWO, THREE},
        {EIGHT, BLANK, FOUR},
        {SEVEN, SIX, FIVE}
    };

    public Board()
    {
        board = new int[N][N];
        misplaced = 0;
    }
    
    public Board(int[][] b)
    {
        board = b;
        computeMisplaced();
    }
    
    public boolean equals(Board b) {
        int[][] a = b.getBoard();
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(board[i][j] != a[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public String toString() {
        String str = "";
        
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                str += board[i][j] + " ";
            }
        }
        
        str += "\n";
        return str;
    }
    
    // Find out how many tiles are misplaced in the current board
    public void computeMisplaced() {
        int sum = 0;
        sum += blockOutOfPlace(ONE, 0, 0);
        sum += blockOutOfPlace(TWO, 0, 1);
        sum += blockOutOfPlace(THREE, 0, 2);
        sum += blockOutOfPlace(FOUR, 1, 2);
        sum += blockOutOfPlace(FIVE, 2, 2);
        sum += blockOutOfPlace(SIX, 2, 1);
        sum += blockOutOfPlace(SEVEN, 2, 0);
        sum += blockOutOfPlace(EIGHT, 1, 0);
        misplaced = sum;
    }
    
    public int blockOutOfPlace(int block, int x, int y) {
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(board[i][j] == block) {
                    if(i != x || j != y) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }
    
    private int[] getBlankIndices() {
        int[] indices = new int[2];
        
        for(int i = 0; i < N; i++) {
           for(int j = 0; j < N; j++) {
               if(board[i][j] == BLANK) {
                   indices[0] = i;
                   indices[1] = j;
                }
            }
        }
        return indices;
    }
    
    private int[][] copyBoard() {
        int[][] copy = new int[3][3];
        
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }
    
    // Move a tile to an adjacent blank, and add this board to the list of children
    private void moveBlankAndAddChild(ArrayList<Board> children, int i, int j, int k, int l) {
        int[][] child = copyBoard();
        
        int temp = child[i][j];
        child[i][j] = child[k][l];
        child[k][l] = temp;
        
        Board childBoard = new Board(child);
        
        children.add(childBoard);
    }
    
    
    public ArrayList<Board> findChildren() {
        ArrayList<Board> children = new ArrayList<Board>();
        
        int[] blankIndices = getBlankIndices();
        
        int blankRowIndex = blankIndices[0];
        int blankColIndex = blankIndices[1];
        
        // Child made from moving blank up
        if(blankRowIndex - 1 >= 0) {
            moveBlankAndAddChild(children, blankRowIndex - 1, blankColIndex, blankRowIndex, blankColIndex);
        }
        // Child made from moving blank down
        if(blankRowIndex + 1 < N) {
            moveBlankAndAddChild(children, blankRowIndex + 1, blankColIndex, blankRowIndex, blankColIndex);
        }
        // Child made from moving blank left
        if(blankColIndex - 1 >= 0) {
            moveBlankAndAddChild(children, blankRowIndex, blankColIndex - 1, blankRowIndex, blankColIndex);
        }
        // Child made from moving blank down
        if(blankColIndex + 1 < N) {
            moveBlankAndAddChild(children, blankRowIndex, blankColIndex + 1, blankRowIndex, blankColIndex);
        }
        
        return children;
    }
    
    // Checks if the board configuration is solvable for the given goal state,
    // following a property of the 8-puzzle problem.
    // It determines the sum of the number of smaller digits which come after each tile.
    // If the total sum is odd, the state is solvable, otherwise it is not.
    public boolean isSolvable() {
        int sum = 0;
        int value;
        
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                value = board[i][j];     
                
                if(value >= TWO && value <= EIGHT) {
                    int k, l;
                    k = i;
                    l = j + 1;
                    
                    if(l >= N) {
                        l = 0;
                        k = i + 1;
                        if(k >= N)
                            break;
                    }
                    
                    while(k < N) {
                        while(l < N) {
                            if(value > board[k][l]) {
                                sum++;
                            }
                            l++;
                        }
                        l = 0;
                        k++;
                    }   
                }
            }
        }
        
        if(sum % 2 == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    // Checks if the current board is equivalent to the goal board
    public boolean isGoalBoard() {
        return Arrays.deepEquals(board, goalBoard);
    }
    
    // Returns the number of misplaced tiles
    public int getMisplaced() {
        computeMisplaced();
        return misplaced;
    }

    public int[][] getBoard() {
        return board;
    }
}
