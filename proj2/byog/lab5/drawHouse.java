package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import byog.Core.RandomUtils;
import org.junit.Test;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.lang.reflect.WildcardType;
import java.util.Random;

public class drawHouse {
    private static int WIDTH;
    private static int HEIGHT;

    private static long SEED;
    private static Random RANDOM = new Random();

    private static final RandomUtils randUnit = new RandomUtils();

    public drawHouse() {
        WIDTH = 65;
        HEIGHT = 35;
        SEED = System.nanoTime();
        RANDOM = new Random(SEED);
    }

    public drawHouse(int width, int height, int seed) {
        WIDTH = width;
        HEIGHT = height;
        SEED = seed;
        RANDOM = new Random(SEED);
    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.GRASS;
            case 3: return Tileset.FLOOR;
            case 4: return Tileset.MOUNTAIN;
            default: return Tileset.NOTHING;
        }
    }

    /**
     * Initialize the world.
     * @param world
     */
    public static void intializeTheWorld(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Draw a rectangular house of houseWidth at least 3 width and of houseLength at least 3
     * length house at left-up position (xx, yy).
     * @param xx x position
     * @param yy y position
     * @param houseWidth width of house
     * @param houseLength length of house
     * @param tiles Tile world
     * @param tileset type of tile
     */
    public static void drawOneRectangularHouse(int xx, int yy, int houseWidth, int houseLength, TETile[][] tiles, TETile tileset) {
        // fills in a block houseWidth tiles wide by houseLength tiles tall
        for (int i = 0; i < houseWidth; i++) {
            for (int j = 0; j < houseLength; j++) {
                tiles[xx + i][yy + j] = tileset;
            }
        }
    }

    /**
     * If the room at least 6 / 7 free space, return true.
     * @param xx
     * @param yy
     * @param len1
     * @param len2
     * @param tiles
     * @param tileset
     * @return
     */
    public static boolean computeOverlapArea(int xx, int yy, int len1, int len2, TETile[][] tiles, TETile tileset) {
        int area = len1 * len2;
        int count = 0;
        for (int i = xx; i < xx + len1; i++) {
            for (int j = yy; j < yy + len2; j++) {
                if (tiles[i][j] == tileset)   count++;
            }
        }
        double percent = (double) count / area;
        return (percent <= 1.0 / 7) && (percent > 4.0 / 49);
    }

    /**
     * Find a proper position for room.
     * @param len1
     * @param len2
     * @param tiles
     * @param tileset
     * @return
     */
    public static int[] findLocation(int len1, int len2, TETile[][] tiles, TETile tileset) {
        int[] locatn = new int[2];
        int i = 0;
        int j = 0;
        boolean choose = true;
        while (choose) {
            i = RANDOM.nextInt(WIDTH - 10);
            j = RANDOM.nextInt(HEIGHT - 10);
            choose = !computeOverlapArea(i, j, len1, len2, tiles, tileset);
        }
        locatn[0] = i;
        locatn[1] = j;
        return locatn;
    }

    /**
     * Draw each room at the position.
     * @param Num
     * @param len1
     * @param len2
     * @param tiles
     * @param tileset
     */
    public static void drawEachRandomLocatnHouses(int Num, int len1, int len2, TETile[][] tiles, TETile tileset) {
        int count = 0;
        int xx, yy;
        for (int i = 0; i < Num; i++) {
            int[] loctn = findLocation(len1, len2, tiles, tileset);
            xx = loctn[0];
            yy = loctn[1];
            drawOneRectangularHouse(xx, yy, len1, len2, tiles, tileset);
        }
    }

    /**
     * Draw total N number of houses.
     * @param houseNum
     * @param tiles
     * @param tileset
     */
    public static void drawNNumberHouses(int[] houseNum, TETile[][] tiles, TETile tileset) {
        drawEachRandomLocatnHouses(houseNum[0], 3, 7, tiles, tileset);
        drawEachRandomLocatnHouses(houseNum[1], 3, 9, tiles, tileset);
        drawEachRandomLocatnHouses(houseNum[2], 4, 10, tiles, tileset);
        drawEachRandomLocatnHouses(houseNum[3], 4, 8, tiles, tileset);
        drawEachRandomLocatnHouses(houseNum[4], 3, 11, tiles, tileset);
        drawEachRandomLocatnHouses(houseNum[5], 6, 4, tiles, tileset);
        drawEachRandomLocatnHouses(houseNum[6], 7, 7, tiles, tileset);
    }

    /**
     * Draw random position of houses satisfied uniform distribution with random size in pareto distribution，the
     * number of the house is in gaussian distribution.
     */
    public static void drawRandomNumberDistributionHouse(TETile[][] tiles, TETile tileset) {
        int houseNum[] = new int[7];
        int digit;
        int houseTotalNum = (int) randUnit.gaussian(RANDOM, 40.0, 3.0);
        for (int i = 0; i < houseTotalNum; i++) {
            digit = (int) randUnit.pareto(RANDOM);
            switch (digit) {
                case 0:
                    houseNum[0]++;
                    break;
                case 1:
                    houseNum[1]++;
                    break;
                case 2:
                    houseNum[2]++;
                    break;
                case 3:
                    houseNum[3]++;
                    break;
                case 4:
                    houseNum[4]++;
                    break;
                case 5:
                    houseNum[5]++;
                    break;
                default:
                    houseNum[6]++;
                    break;
            }
        }
        boolean right = true;
        while (right) {
            int xx = RANDOM.nextInt(WIDTH - 10);
            int yy = RANDOM.nextInt(HEIGHT - 10);
            int choose = RANDOM.nextInt(2);
            if (choose == 0 && (xx + 7 - 1) < WIDTH && (yy + 7 - 1) < HEIGHT) {
                drawOneRectangularHouse(xx, yy, 7, 7, tiles, tileset);
                right = false;
            }
            if (choose == 1 && (xx + 7 - 1) < WIDTH && (yy + 7 - 1) < HEIGHT) {
                drawOneRectangularHouse(xx, yy, 7, 7, tiles, tileset);
                right = false;
            }
        }
        drawNNumberHouses(houseNum, tiles, tileset);
    }

    /**
     * Check if all pixels around position (xx, yy) are tileset.
     * @param xx
     * @param yy
     * @param tiles
     * @param tileset
     * @return
     */
    public static boolean fullPixel(int xx, int yy, TETile[][] tiles, TETile tileset) {
        boolean full = true;
        labelA:
        for (int i = xx - 1; i < xx + 2; i++){
            for (int j = yy - 1; j < yy + 2; j++){
                if (tiles[i][j] != tileset) {
                    full = false;
                    break labelA;
                }
            }
        }
        return full;
    }

    /**
     * Fill all pixels around position (xx, yy).
     * @param xx
     * @param yy
     * @param tiles
     * @param tileset
     */
    public static void fillAroundPixel(int xx, int yy, TETile[][] tiles, TETile tileset) {
        for (int i = xx - 1; i < xx + 2; i++){
            for (int j = yy - 1; j < yy + 2; j++){
                tiles[i][j] = tileset;
            }
        }
    }

    /**
     * Check around four squares to make sure they overlap.
     * @param xx
     * @param yy
     * @param tiles
     * @param tileset
     */
    public static void checkAroundSquareOneByOne(int xx, int yy, TETile[][] tiles, TETile tileset) {
        if (fullPixel(xx, yy, tiles, tileset)) {
            if (fullPixel(xx - 2, yy + 2, tiles, tileset)) {
                if (fullPixel(xx, yy + 2, tiles, tileset) || fullPixel(xx - 2, yy, tiles, tileset)) return;
                else {
                    int rand = RANDOM.nextInt(2);
                    if (rand == 0)  fillAroundPixel(xx, yy + 2, tiles, tileset);
                    else    fillAroundPixel(xx - 2, yy, tiles, tileset);
                }
            }
            if (fullPixel(xx - 2, yy - 2, tiles, tileset)) {
                if (fullPixel(xx, yy - 2, tiles, tileset) || fullPixel(xx - 2, yy, tiles, tileset)) return;
                else {
                    int rand = RANDOM.nextInt(2);
                    if (rand == 0)  fillAroundPixel(xx, yy - 2, tiles, tileset);
                    else    fillAroundPixel(xx - 2, yy, tiles, tileset);
                }
            }
            if (fullPixel(xx + 2, yy + 2, tiles, tileset)) {
                if (fullPixel(xx, yy + 2, tiles, tileset) || fullPixel(xx + 2, yy, tiles, tileset)) return;
                else {
                    int rand = RANDOM.nextInt(2);
                    if (rand == 0)  fillAroundPixel(xx, yy + 2, tiles, tileset);
                    else    fillAroundPixel(xx + 2, yy, tiles, tileset);
                }
            }
            if (fullPixel(xx + 2, yy - 2, tiles, tileset)) {
                if (fullPixel(xx + 2, yy, tiles, tileset) || fullPixel(xx, yy - 2, tiles, tileset)) return;
                else {
                    int rand = RANDOM.nextInt(2);
                    if (rand == 0)  fillAroundPixel(xx, yy - 2, tiles, tileset);
                    else    fillAroundPixel(xx + 2, yy, tiles, tileset);
                }
            }
        }
        else return ;
    }

    /**
     * Check each pixel and revise the room overlap, or there would be gaps between some rooms and hallways.
     * @param tiles
     * @param tileset
     */
    public static void checkRoomOverlap(TETile[][] tiles, TETile tileset) {
            for (int i = 3; i < WIDTH - 5; i++) {
                for (int j = 3; j < HEIGHT - 5; j++) {
                    checkAroundSquareOneByOne(i, j, tiles, tileset);
                }
            }
        }

    /**
     * Check whether there is any Nothing pixel around position (xx, yy).
     * @param xx
     * @param yy
     * @param tiles
     * @param tileset
     * @return
     */
    public  static boolean checkNothing(int xx, int yy, TETile[][] tiles, TETile tileset) {
        boolean haveNothing = false;
        labelA:
        for (int i = xx - 1; i < xx + 2; i++) {
            for (int j = yy - 1; j < yy + 2; j++) {
                if (tiles[i][j] == Tileset.NOTHING) {
                    haveNothing = true;
                    break labelA;
                }
            }
        }
        return haveNothing;
    }

    /**
     * Build the walls for four sides.
     * @param tiles
     * @param tileset
     */
    public static void buildTheWallAtFourSides(TETile[][] tiles, TETile tileset) {
        for (int i = 0; i < WIDTH; i++) {
            if (tiles[i][0] == tileset) tiles[i][0] = Tileset.WALL;
            if (tiles[i][HEIGHT - 1] == tileset) tiles[i][HEIGHT - 1] = Tileset.WALL;
        }
        for (int i = 0; i < HEIGHT; i++) {
            if (tiles[0][i] == tileset) tiles[0][i] = Tileset.WALL;
            if (tiles[WIDTH - 1][i] == tileset) tiles[WIDTH - 1][i] = Tileset.WALL;
        }
    }

    /**
     * Build the world for other pixels.
     * @param tiles
     * @param tileset
     */
    public static void buildTheWallOther(TETile[][] tiles, TETile tileset) {
        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                if (tiles[i][j] == tileset && checkNothing(i, j, tiles, tileset))    tiles[i][j] = Tileset.WALL;
            }
        }
    }

    public static void addLockedDoor(TETile[][] tiles) {
        for (int i = 1; i < WIDTH - 1; i++) {
            for (int j = 1; j < HEIGHT - 1; j++) {
                if(checkPlaceForDoor(i, j, tiles) && tiles[i][j] == Tileset.WALL) {
                    tiles[i][j] = Tileset.LOCKED_DOOR;
                    return;
                }
            }
        }
    }

    public static int[] addPlayer(TETile[][] tiles) {
        int[] locatn = new int[]{0,0};
        for (int i = WIDTH - 2; i > 5; i--) {
            for (int j = HEIGHT - 2; j > 5; j--) {
                if(checkPlaceForDoor(i, j, tiles) && tiles[i][j] == Tileset.FLOOR) {
                    tiles[i][j] = Tileset.PLAYER;
                    locatn[0] = i;
                    locatn[1] = j;
                    return locatn;
                }
            }
        }
        return locatn;
    }

    public static boolean checkPlaceForPlayer(int xx, int yy, TETile[][] tiles) {
        if (tiles[xx][yy + 1] == Tileset.FLOOR) return true;
        if (tiles[xx][yy - 1] == Tileset.FLOOR) return true;
        if (tiles[xx + 1][yy] == Tileset.FLOOR) return true;
        if (tiles[xx - 1][yy] == Tileset.FLOOR) return true;
        else return false;
    }


    public static boolean checkPlaceForDoor(int xx, int yy, TETile[][] tiles) {
        if (tiles[xx][yy + 1] == Tileset.FLOOR) return true;
        if (tiles[xx][yy - 1] == Tileset.FLOOR) return true;
        if (tiles[xx + 1][yy] == Tileset.FLOOR) return true;
        if (tiles[xx - 1][yy] == Tileset.FLOOR) return true;
        else return false;
    }

    /**
     * Build the walls for the whole world and locked door.
     * @param tiles
     * @param tileset
     */
    public static void buildTheWholeWallAndLockedDoor(TETile[][] tiles, TETile tileset) {
        buildTheWallAtFourSides(tiles, tileset);
        buildTheWallOther(tiles, tileset);
        addLockedDoor(tiles);
    }

}
