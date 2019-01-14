package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.lab5.drawHouse;
import byog.TileEngine.*;
import edu.princeton.cs.introcs.StdDraw;
import java.io.*;
import  java.util.*;
import java.util.List;

import java.awt.*;
import java.awt.desktop.SystemSleepEvent;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 90;
    public static final int HEIGHT = 52;
    public int load;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawStartPage();
        String strInput = "";
        strInput = solicitNCharsInput(1);
        String filename = "save.ser";
        if (strInput.equals("N") || strInput.equals("n")) {
            chooseN(filename);
        }
        if (strInput.equals("L")|| strInput.equals("l")) {
            List<Object> load = new ArrayList<Object>();
            try {
                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(file);
                load = (List<Object>) in.readObject();
                in.close();
                file.close();
                ter.initialize(WIDTH, HEIGHT, 0,0);
                ter.renderFrame((TETile[][])load.get(0));
                TETile[][] loadTET = (TETile[][])load.get(0);
                int[] loadArra = (int[])load.get(1);
                playerMove(loadTET, loadArra, filename);

            }
            catch(IOException ex)
            {
                System.out.println("IOException is caught");
            }
            catch(ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException is caught");
            }
        }
        if (strInput.equals("Q") || strInput.equals("q")) {
            showQuit();
        }
    }

    public void showQuit() {
        ter.initialize(WIDTH, HEIGHT, 0,0);
        StdDraw.clear(Color.BLACK); // Clear the offscreen canvas.
        StdDraw.setPenColor(StdDraw.WHITE); // Sets the pen color to the specified color white.
        Font fontN1 = new Font("TimesRoman", Font.PLAIN, 50); // draw font on the screen with size 30, type bold,
        // What is font name?
        StdDraw.setFont(fontN1); // Sets the font to the specified value.
        StdDraw.text(45, 40, "The game is quit!");
        StdDraw.show();
    }

    public void chooseN(String filename) {
        StdDraw.clear(Color.BLACK); // Clear the offscreen canvas.
        StdDraw.setPenColor(StdDraw.WHITE); // Sets the pen color to the specified color white.
        Font fontN1 = new Font("TimesRoman", Font.PLAIN, 35); // draw font on the screen with size 30, type bold,
        // What is font name?
        StdDraw.setFont(fontN1); // Sets the font to the specified value.
        StdDraw.text(22, 40, "Input your seed:");
        StdDraw.show();
        String seed = "";
        char key = 'A';
        while (key!='S' && key!='s') {
            if (StdDraw.hasNextKeyTyped()) {
                key = StdDraw.nextKeyTyped();
                seed += key;
                flashSequence(seed);
            }
        }
        String in = seed.substring(1, seed.length() - 2);
        int seedInt = Integer.parseInt(in);
        ter.initialize(WIDTH, HEIGHT, 0,0);
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        drawHouse houseBuild = new drawHouse(WIDTH, HEIGHT, seedInt);
        houseBuild.intializeTheWorld(finalWorldFrame);
        // Generate a world
        houseBuild.drawRandomNumberDistributionHouse(finalWorldFrame, Tileset.FLOOR);
        // Revise rooms
        houseBuild.checkRoomOverlap(finalWorldFrame, Tileset.FLOOR);
        // Build wall
        houseBuild.buildTheWholeWallAndLockedDoor(finalWorldFrame, Tileset.FLOOR);
        // Set player
        int[] locatn = houseBuild.addPlayer(finalWorldFrame);
        ter.renderFrame(finalWorldFrame);
        System.out.println(TETile.toString(finalWorldFrame));
        playerMove(finalWorldFrame, locatn, filename);
    }

    public void playerMove(TETile[][] world, int[] locatn, String filename) {
        int x = locatn[0];
        int y = locatn[1];
        TETile[][] worldCopy = world;
        int[] locatnCopy = locatn;
        String strInput = "";
        strInput = solicitNCharsInput(1);
        while(true) {
            if(strInput.equals("")) strInput = solicitNCharsInput(1);
            if (strInput.equals("W")|| strInput.equals("w")) {
               if (worldCopy[x][y+1].character() == Tileset.FLOOR.character()) {
                   worldCopy[x][y+1] = Tileset.PLAYER;
                   worldCopy[x][y] = Tileset.FLOOR;
                   y += 1;
                   System.out.println(TETile.toString(worldCopy));
                   strInput = showHUDOftenAndReturnDirection(worldCopy);
               }
               else if (worldCopy[x][y+1].character() == Tileset.LOCKED_DOOR.character()) {
                   showFinalWords("You get out!");
                   worldCopy[x][y+1] = Tileset.LOCKED_DOOR;
                   worldCopy[x][y] = Tileset.FLOOR;
                   y += 1;
               }
               else {strInput = "";}
            }

            if (strInput.equals("S")|| strInput.equals("s")) {
                if (worldCopy[x][y-1].character() == Tileset.FLOOR.character()) {
                    worldCopy[x][y-1] = Tileset.PLAYER;
                    worldCopy[x][y] = Tileset.FLOOR;
                    y -= 1;
                    System.out.println(TETile.toString(worldCopy));
                    strInput = showHUDOftenAndReturnDirection(worldCopy);
                }
                else if (worldCopy[x][y-1].character() == Tileset.LOCKED_DOOR.character()) {
                    showFinalWords("You get out!");
                    worldCopy[x][y-1] = Tileset.LOCKED_DOOR;
                    worldCopy[x][y] = Tileset.FLOOR;
                    y -= 1;
                }
                else {strInput = ""; }
            }

            if (strInput.equals("A")|| strInput.equals("a")) {
                if (worldCopy[x-1][y].character() == Tileset.FLOOR.character()) {
                    worldCopy[x-1][y] = Tileset.PLAYER;
                    worldCopy[x][y] = Tileset.FLOOR;
                    x -= 1;
                    System.out.println(TETile.toString(worldCopy));
                    strInput = showHUDOftenAndReturnDirection(worldCopy);
                }
                else if (worldCopy[x-1][y].character() == Tileset.LOCKED_DOOR.character()) {
                    showFinalWords("You get out!");
                    worldCopy[x-1][y] = Tileset.LOCKED_DOOR;
                    worldCopy[x][y] = Tileset.FLOOR;
                    x -= 1;
                }
                else {strInput = "";}
            }

            if (strInput.equals("D")|| strInput.equals("d")) {
                if (worldCopy[x+1][y].character() == Tileset.FLOOR.character()) {
                    worldCopy[x+1][y] = Tileset.PLAYER;
                    worldCopy[x][y] = Tileset.FLOOR;
                    x += 1;
                    System.out.println(TETile.toString(worldCopy));
                    strInput = showHUDOftenAndReturnDirection(worldCopy);
                }
                else if (worldCopy[x+1][y].character() == Tileset.LOCKED_DOOR.character()) {
                    showFinalWords("You get out!");
                    worldCopy[x+1][y] = Tileset.LOCKED_DOOR;
                    worldCopy[x][y] = Tileset.FLOOR;
                    x += 1;
                }
                else {strInput = "";}
            }
            locatn[0] = x;
            locatn[1] = y;
            if (strInput.equals(":")){
                strInput += solicitNCharsInput(1);
                if (strInput.equals(":Q")|| strInput.equals(":q")) {
                    List<Object> list = new ArrayList<Object>();
                    list.add(world);
                    list.add(locatn);
                    try {
                        FileOutputStream file = new FileOutputStream(filename);
                        ObjectOutputStream out = new ObjectOutputStream(file);
                        out.writeObject(list);
                        out.close();
                        file.close();
                    }
                    catch(IOException ex)
                    {
                        System.out.println("IOException is caught");
                    }
                    showFinalWords("The game is saved and quit!");
                }
            }
        }
    }

    public void showFinalWords(String str) {
        //ter.initialize(WIDTH, HEIGHT, 0,0);
        StdDraw.clear(Color.BLACK); // Clear the offscreen canvas.
        StdDraw.setPenColor(StdDraw.WHITE); // Sets the pen color to the specified color white.
        Font fontN1 = new Font("TimesRoman", Font.PLAIN, 50); // draw font on the screen with size 30, type bold,
        // What is font name?
        StdDraw.setFont(fontN1); // Sets the font to the specified value.
        StdDraw.text(45, 40, str);
        StdDraw.show();
    }

    public String showHUDOftenAndReturnDirection(TETile[][] world) {
        String strInput = "";
        while(true) {
            strInput = ifInput();
            if (!strInput.equals(""))   {return strInput; }
            Point location = MouseInfo.getPointerInfo().getLocation();
            int x = (int)location.getX();
            int y = (int)location.getY();
            int xx = (int)location.getX();
            int yy = (int)location.getY();
            if(x==xx && y==yy) {
                x = x / 16;
                y = (y-69) / 16;
                showHUD(world, x, y);
            }
        }
    }

    public void drawStartPage() {
        StdDraw.setCanvasSize(40*16, 50*16); // Why we need 16?
        Font font0 = new Font("Monaco", Font.BOLD, 30); // Initialize the font.
        StdDraw.setFont(font0); // Set the font.
        StdDraw.setXscale(0, 45); // All drawing takes places in the unit square, with (0, 0) at lower left and (this.width, this.height) at upper right.
        StdDraw.setYscale(0, 50);
        StdDraw.enableDoubleBuffering(); // All drawing takes place on the offscreen canvas.
        StdDraw.clear(Color.BLACK); // Clear the offscreen canvas.
        StdDraw.setPenColor(StdDraw.WHITE); // Sets the pen color to the specified color white.
        Font font1 = new Font("TimesRoman", Font.PLAIN, 35); // draw font on the screen with size 30, type bold,
        // What is font name?
        StdDraw.setFont(font1); // Sets the font to the specified value.
        StdDraw.text(22, 40, "CS61B:  Get Out!");
        Font font2 = new Font("TimesRoman", Font.PLAIN, 20);
        StdDraw.setFont(font2);
        StdDraw.text(22, 30, "New Game (N)");
        StdDraw.text(22, 28, "Load Game (L)");
        StdDraw.text(22, 26, "Quit (Q)");
        StdDraw.show();
    }

    public void showHUD(TETile[][] world, int xx, int yy) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        //StdDraw.clear(new Color(0, 0, 0)); BLACK
        StdDraw.clear(StdDraw.BLACK);
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x, y);
            }
        }
        StdDraw.setPenColor(StdDraw.WHITE); // Sets the pen color to the specified color white.
        Font fontN2 = new Font("TimesRoman", Font.PLAIN, 16); // draw font on the screen with size 30, type bold,
        // What is font name?
        StdDraw.setFont(fontN2); // Sets the font to the specified value.
        if(xx>=0 && xx<=89 && yy>=0 && yy<=51) {
            StdDraw.text(3, 51, world[xx][51-yy].description());
        }
        else    {StdDraw.text(3, 51, "nothing");}
        StdDraw.show();
    }

    public TETile[][] gameMapGeneration(int WIDTH, int HEIGHT, int seedInt) {
        ter.initialize(WIDTH, HEIGHT, 0,0);
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        drawHouse houseBuild = new drawHouse(WIDTH, HEIGHT, seedInt);
        houseBuild.intializeTheWorld(finalWorldFrame);
        // Generate a world
        houseBuild.drawRandomNumberDistributionHouse(finalWorldFrame, Tileset.FLOOR);
        // Revise rooms
        houseBuild.checkRoomOverlap(finalWorldFrame, Tileset.FLOOR);
        // Build wall
        houseBuild.buildTheWholeWallAndLockedDoor(finalWorldFrame, Tileset.FLOOR);
        // Set player
        int[] locatn = houseBuild.addPlayer(finalWorldFrame);
        ter.renderFrame(finalWorldFrame);
        System.out.println(TETile.toString(finalWorldFrame));
        return finalWorldFrame;
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

    public String ifInput() {
        String input = "";
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            input += key;
        }
        return input;
    }

    public void flashSequence(String words) {
        StdDraw.clear(Color.BLACK); // Clear the offscreen canvas.
        StdDraw.setPenColor(StdDraw.WHITE); // Sets the pen color to the specified color white.
        Font fontN1 = new Font("TimesRoman", Font.PLAIN, 35); // draw font on the screen with size 30, type bold,
        // What is font name?
        StdDraw.setFont(fontN1); // Sets the font to the specified value.
        StdDraw.text(22, 40, "Input your seed:" + words);
        StdDraw.show();
        //TODO: Display each character in letters, making sure to blank the screen between letters
    }

    public void moveForString(TETile[][] finalWorldFrame, int[] locatn, String input, int i, String filename) {
        String moveStep = input.substring(i+1);
        int tryQ = moveStep.length();
        TETile[][] worldCopy = finalWorldFrame;
        System.out.println(moveStep);
        if (tryQ==1 || moveStep.charAt(tryQ-2) != ':') {
            moveDetail(worldCopy, locatn, moveStep, tryQ);
            ter.renderFrame(worldCopy);
        }
        else if (moveStep.charAt(tryQ-1) == 'Q'){
            moveDetail(worldCopy, locatn, moveStep, tryQ-2);
            ter.renderFrame(worldCopy);
            List<Object> list = new ArrayList<Object>();
            list.add(worldCopy);
            list.add(locatn);
            try {
                FileOutputStream file = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(list);
                out.close();
                file.close();
            } catch (IOException ex) {
                System.out.println("IOException is caught");
            }
        }
    }

    public void moveDetail(TETile[][] worldCopy, int[] locatn, String moveStep, int scope) {
        int j = 0;
        int x = locatn[0];
        int y = locatn[1];
        while (j < scope) {
            if (moveStep.charAt(j) == 'W') {
                if (worldCopy[x][y + 1].character() == Tileset.FLOOR.character()) {
                    worldCopy[x][y + 1] = Tileset.PLAYER;
                    worldCopy[x][y] = Tileset.FLOOR;
                    y += 1;
                    System.out.println(TETile.toString(worldCopy));
                }
            }

            if (moveStep.charAt(j) == 'S') {
                if (worldCopy[x][y - 1].character() == Tileset.FLOOR.character()) {
                    worldCopy[x][y - 1] = Tileset.PLAYER;
                    worldCopy[x][y] = Tileset.FLOOR;
                    y -= 1;
                    System.out.println(TETile.toString(worldCopy));
                }
            }

            if (moveStep.charAt(j) == 'A') {
                if (worldCopy[x - 1][y].character() == Tileset.FLOOR.character()) {
                    System.out.println(1);
                    worldCopy[x - 1][y] = Tileset.PLAYER;
                    worldCopy[x][y] = Tileset.FLOOR;
                    x -= 1;
                    System.out.println(2);
                    System.out.println(TETile.toString(worldCopy));
                }
            }

            if (moveStep.charAt(j) == 'D') {
                if (worldCopy[x + 1][y].character() == Tileset.FLOOR.character()) {
                    worldCopy[x + 1][y] = Tileset.PLAYER;
                    worldCopy[x][y] = Tileset.FLOOR;
                    x += 1;
                    System.out.println(TETile.toString(worldCopy));
                }
            }
            locatn[0] = x;
            locatn[1] = y;
            j++;
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        char choice = input.charAt(0);
        String filename = "save.ser";
        if (choice == 'N') {
            String afterN = input.substring(1);
            String seedIn = "";
            int i = 0;
            while (afterN.charAt(i) != 'S') {
                seedIn += afterN.charAt(i);
                i++;
            }

            /*Build the world*/
            int seed = Integer.parseInt(seedIn);
            ter.initialize(WIDTH, HEIGHT);
            TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
            drawHouse houseBuild = new drawHouse(WIDTH, HEIGHT, seed);
            houseBuild.intializeTheWorld(finalWorldFrame);
            // Generate a world
            houseBuild.drawRandomNumberDistributionHouse(finalWorldFrame, Tileset.FLOOR);
            // Revise rooms
            houseBuild.checkRoomOverlap(finalWorldFrame, Tileset.FLOOR);
            // Build wall
            houseBuild.buildTheWholeWallAndLockedDoor(finalWorldFrame, Tileset.FLOOR);
            int[] locatn = houseBuild.addPlayer(finalWorldFrame);
            System.out.println(TETile.toString(finalWorldFrame));
            /*Move*/
            moveForString(finalWorldFrame, locatn, afterN, i, filename);
            return finalWorldFrame;
        }
        else if (choice == 'L') {
            List<Object> load = new ArrayList<Object>();
            try {
                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(file);
                load = (List<Object>) in.readObject();
                in.close();
                file.close();
                ter.initialize(WIDTH, HEIGHT, 0,0);
                ter.renderFrame((TETile[][])load.get(0));
                TETile[][] loadTET = (TETile[][])load.get(0);
                int[] loadArra = (int[])load.get(1);
                moveForString(loadTET, loadArra, input, 0, filename);
                return loadTET;
            }
            catch(IOException ex)
            {
                System.out.println("IOException is caught");
            }
            catch(ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException is caught");
            }
        }
        else if (choice == 'Q') {
            showQuit();
        }
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        drawHouse houseBuild = new drawHouse(WIDTH, HEIGHT, 123);
        houseBuild.intializeTheWorld(finalWorldFrame);
        return finalWorldFrame;
    }
}
