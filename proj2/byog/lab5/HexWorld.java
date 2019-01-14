package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    private static final long SEED = System.nanoTime();
    private static final Random RANDOM = new Random(SEED);

    /**
     * Add a hexagon at position (xx, yy) with length len filled by TETile of
     * type tileset in the whole world tiles.
     * @param xx x position
     * @param yy y position
     * @param len length of each side
     * @param tiles type of tileset
     */
    // add a hexagon at position (xx, yy) with length len filled by TETile of type tileset in the whole world tiles.
    public static void addHexagon(int xx, int yy, int len, TETile[][] tiles) {
        // fill this hexagon
        TETile randomTileHexagon = randomTile();
        for (int i = 0; i < len; i++) {
            fillRow(xx - i, yy - i, len + 2 * i, tiles, randomTileHexagon);
            fillRow(xx - i, yy - 2 * len + 1 + i, len + 2 * i, tiles, randomTileHexagon);
        }
    }

    // fill one row of len length in a hexagon with Tileset of type tileset.
    public static void fillRow(int x, int y, int len, TETile[][] tiles, TETile tileset) {
        for (int i = x; i < x + len; i++) {
            tiles[i][y] = tileset;
        }
    }

    // fill one column with num number of hexagons of 19 hexagons
    public static void fillColumn(int x, int y, int len, int num, TETile[][] tiles) {
        int count = 0;
        while (count < num) {
            addHexagon(x, y - count * 2 * len, len, tiles);
            count++;
        }
    }

    // fill total 19 hexagons
    public static void addTotalHexagons(int x, int y, int len, TETile[][] tiles) {
        for (int i = 0; i <  3; i++) {
            fillColumn(x -  i * (2 * len - 1), y - i * len, len, 5 - i, tiles);
            fillColumn(x + i * (2 * len - 1), y - i * len, len, 5 - i, tiles);
        }
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

    public static void intializeTheWorld(TETile[][] world) {
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT,6,1);

        TETile[][] hexagonWorld = new TETile[WIDTH][HEIGHT];
        intializeTheWorld(hexagonWorld);

        int length = 3;
        addTotalHexagons((WIDTH - 1) / 2, HEIGHT - 3, length, hexagonWorld);
        ter.renderFrame(hexagonWorld);
        System.out.println(TETile.toString(hexagonWorld));
    }

}
