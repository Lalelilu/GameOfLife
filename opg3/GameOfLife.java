package opg3;
import java.util.Random;

public class GameOfLife {
    private int[][] gameGrid;
    Random rand = new Random();

    // Default constructor
    public GameOfLife () {
        gameGrid = new int[][] {{-1}};
    }

    // Constructor 1
    public GameOfLife(int size) {
        gameGrid = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameGrid[i][j] = rand.nextInt(2);
            }
        }
    }

    // Constructor 2
    public GameOfLife(int[][] initialState) {
        this.gameGrid = initialState;
    }

    // Counts live neighbours for cell at (x,y)
    private int liveNeighbours(int x, int y, int size) {
        int count = 0;

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (j >= 0 && i >= 0 && j < size && i < size) {
                    if (j == y && i == x) {
                        // Do nothing
                    } else if (gameGrid[i][j] == 1) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    // Copies one array to another array
    private int[][] copyArray(int[][] arrayOld, int[][] arrayNew, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arrayNew[i][j] = arrayOld[i][j];
            }
        }
        return arrayNew;
    }

    // Generates the next gameGrid array
    public void nextState() {
        int size = gameGrid.length;
        int[][] next = new int[size][size];
        next = copyArray(gameGrid,next,size);

        // Kills or revives the cells according to their neighbours
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int count = liveNeighbours(i,j,size);

                if (gameGrid[i][j] == 1) {
                    if (count == 2 || count == 3) {
                        next[i][j] = 1;
                    } else {
                        next[i][j] = 0;
                    }
                } else {
                    if (count == 3) {
                        next[i][j] = 1;
                    } else {
                        next[i][j] = 0;
                    }
                }
            }
        }
        gameGrid = copyArray(next,gameGrid,size);
    }

    // Returns the game array
    public int[][] getGameGrid() {
        return gameGrid;
    }

    // Checks if game repeats
    public boolean patternRepeat() {
        return true;
    }
}
