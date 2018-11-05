/* Program: GameOfLifeMain
 * Uses other made classes: 'StdDraw' & 'GameOfLife'
 * Made by: Simon Amtoft Pedersen & Morten Holmark Vandborg
 *
 * Short description:
 * Simulates Game of Life from a random starting state.
 * Terminates program if the current state is equal to one of the last 3 states.
 */
package opg3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameOfLifeMain {
    public static void main(String[] args) throws FileNotFoundException{
        int states = 0; // Number of states

        GameOfLife game = pickInitialState();
        setupCanvas(game.getGameGrid().length);
        drawGameOfLife(game);

        while (game.patternRepeat()) {
            StdDraw.clear();
            game.nextState();
            game = new GameOfLife(game.getGameGrid());
            drawGameOfLife(game);
            StdDraw.show(3500/game.getGameGrid().length);
            states++;
        }

        System.out.printf("Game of Life exiting after %d states", states);
        System.exit(0);
    }

    private static int userPickInitialState() {
        Scanner console = new Scanner(System.in);
        System.out.print("Welcome to Game of Life!\n" +
                "There are the following available initial states:\n" +
                "State 1: Acorn\n" +
                "State 2: Glider gun\n" +
                "State 3: Pentadecathlon\n" +
                "State 4: Pulsar\n" +
                "State 5: Toad\n" +
                "Type a different number to get a random theme.\n\n" +
                "Enter the initial state number: ");
        while (!console.hasNextInt()) { // Checks user input
            console.next();
            System.out.print("Number must be an integer!\nPlease enter new number: ");
        }
        return console.nextInt();
    }

    // Makes a new Game of Life with an initial state chosen by user
    private static GameOfLife pickInitialState() throws FileNotFoundException {
        int state = userPickInitialState();
        GameOfLife game;
        String path;
        int size; // Size of initial state array

        switch (state) {
            case 1:
                path = "src\\opg3\\initialstate\\acorn.gol";
                size = 250;
                break;
            case 2:
                path = "src\\opg3\\initialstate\\glider_gun.gol";
                size = 40;
                break;
            case 3:
                path = "src\\opg3\\initialstate\\pentadecathlon.gol";
                size = 16;
                break;
            case 4:
                path = "src\\opg3\\initialstate\\pulsar.gol";
                size = 15;
                break;
            case 5:
                path = "src\\opg3\\initialstate\\toad.gol";
                size = 4;
                break;
            default:
                path = "";
                size = 100;
                break;
        }

        if (path.equals("")) {
            game = new GameOfLife(size);
        } else {
            int[][] initialstate = getInitialState(path,size);
            game = new GameOfLife(initialstate);
        }

        return game;
    }

    private static int[][] getInitialState(String path, int size) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanFile = new Scanner(file);
        int[][] initialstate = new int[size][size];

        while (scanFile.hasNext()) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    initialstate[i][j] = scanFile.nextInt();
                }
            }
        }
        return initialstate;
    }

    // Draws all the points in one state of Game of Life
    private static void drawGameOfLife(GameOfLife state) {
        int size = state.getGameGrid().length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (state.getGameGrid()[i][j] == 1) {
                    StdDraw.point(i, j);
                }
            }
        }
    }

    // Sets up the canvas ready for drawing
    private static void setupCanvas(int size) {
        StdDraw.show(0);              // Makes the drawing only show after the next show command
        StdDraw.setPenRadius(0.6/size); // Sets pen radius
        StdDraw.setXscale(-1,size);     // x-scale
        StdDraw.setYscale(-1,size);     // y-scale
    }
}
