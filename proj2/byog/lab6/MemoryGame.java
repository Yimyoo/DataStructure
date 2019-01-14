package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(65, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width*16, this.height*16); // Why we need 16?
        Font font = new Font("Monaco", Font.BOLD, 30); // Initialize the font.
        StdDraw.setFont(font); // Set the font.
        StdDraw.setXscale(0, this.width); // All drawing takes places in the unit square, with (0, 0) at lower left and (this.width, this.height) at upper right.
        StdDraw.setYscale(0, this.height);
        StdDraw.enableDoubleBuffering(); // All drawing takes place on the offscreen canvas.
        StdDraw.clear(Color.BLACK); // Clear the offscreen canvas.
        rand = new Random(seed);
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        String str = "";
        int count = n;
        while (n > 0) {
            int i = rand.nextInt(26);
            str += CHARACTERS[i];
            n--;
        }
        //TODO: Generate random string of letters of length n
        return str;
    }

    public void drawFrame(String s, int round) {
        StdDraw.clear(StdDraw.BLACK); // Clears the screen to the default color black.
        StdDraw.setPenColor(StdDraw.WHITE); // Sets the pen color to the specified color white.
        Font font = new Font("TimesRoman", Font.PLAIN, 30); // draw font on the screen with size 30, type bold,
        // What is font name?
        StdDraw.setFont(font); // Sets the font to the specified value.
        StdDraw.line(0, 37, 40, 37); // Draw a line
        StdDraw.text(20, 20, s);
        StdDraw.text(4, 38, "Round: " + round);
        StdDraw.text(20, 38, "Watch!");
        StdDraw.text(33, 38, "You are a star!");
        StdDraw.show();
        return ;
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    }

    public void flashSequence(String letters, int count) {
        int length = letters.length();
        drawFrame("", count);
        for (int i = 0; i < length; i++) {
            StdDraw.text(20, 20, letters.charAt(i) + ""); // Write the given text string in the current font, centered at (x, y).
            StdDraw.show();
            StdDraw.pause(1000); // Display and pause for 1s.
            StdDraw.clear(StdDraw.BLACK); // Clear the screen to the default color black.
            drawFrame("", count);
            StdDraw.show();
            StdDraw.pause(500); // Display and pause for 0.5s.
        }
        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        int i = 0;
        String input = "";
        while (i < n) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                input += key;
                i++;
            }
        }
        return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Game loop
        int round = 1;
        String strRand, strInput;
        while (true) {
            strRand = generateRandomString(round);
            flashSequence(strRand, round);
            strInput = solicitNCharsInput(round);
            if (!strInput.equals(strRand)) {
                drawFrame("Game Over! You made it to round: " + round, round);
                return;
            }
            round++;
        }

    }

}
