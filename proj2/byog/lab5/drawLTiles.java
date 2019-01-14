package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.management.relation.RelationNotFoundException;
import java.util.Random;

public class drawLTiles {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private static final long SEED = System.nanoTime();
    private static final Random RANDOM = new Random(SEED);

    /** Draw a L line
     *
     */
    public static void drawLLine(int horizontalLen, int verticalLen, int xx, int yy, TETile[][] tiles, TETile tileset) {
        // Draw horizontal part of L line
        int randX = RANDOM.nextInt(2);
        if (randX == 0) {
            for (int i = 0; i < horizontalLen; i++) {
                tiles[xx + i][yy] = tileset;
            }
        }
        else {
            for (int i = 0; i < horizontalLen; i++) {
                tiles[xx - i][yy] = tileset;
            }
        }

        // Draw vertical part of L line
        int randY = RANDOM.nextInt(2);
        if (randY == 0) {
            for (int i = 0; i < verticalLen; i++) {
                tiles[xx][yy + i] = tileset;
            }
        }
        else {
            for (int i = 0; i < verticalLen; i++) {
                tiles[xx][yy - i] = tileset;
            }
        }
        System.out.println(horizontalLen  + " " + verticalLen);
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
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] lLineWorld = new TETile[WIDTH][HEIGHT];
        intializeTheWorld(lLineWorld);

        int hori =  2 + RANDOM.nextInt(10);
        int verti = 2 + RANDOM.nextInt(10);

        drawLLine(hori, verti, 30, 30, lLineWorld, Tileset.WALL);

        ter.renderFrame(lLineWorld);
        System.out.println(TETile.toString(lLineWorld));
    }
}
