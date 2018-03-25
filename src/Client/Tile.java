package Client;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Stworzone przez Krzyśka dnia 2017-09-17.
 */
public class Tile {

    //STATIC STUFF HERE

    public static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile(1);
    public static Tile dirtTile = new DirtTile(2);
    public static Tile rockTile = new RockTile(3);

    //CLASS

    public static final int TILEWIDTH = 30, TILEHEIGHT = 30;

    protected BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id){
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public void tick(){

    }

    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILEWIDTH, TILEHEIGHT, null);
    }

    public boolean isSolid(){
        return false;
    }

    public int getId(){
        return id;
    }

}
