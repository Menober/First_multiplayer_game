package Client;

import java.awt.*;

/**
 * Stworzone przez Krzyśka dnia 2017-09-12.
 */
public class Mapa {
    public String nazwa;
    public int szerokosc,wysokosc;
    public int [][] mapa;
    public KHandler handler;
    public EntityManager entityManager;
    public Mapa(String nazwa,int szerokosc,int wysokosc, int [][] mapa,KHandler handler){
        this.nazwa=nazwa;
        this.szerokosc=szerokosc;
        this.wysokosc=wysokosc;
        this.mapa=mapa;
        this.handler=handler;
        entityManager = new EntityManager(handler, new Player(handler, 90, 90));
    }
    public void wyswietlMape(){
        for(int i=0;i<wysokosc;i++) {
            for (int j = 0; j < szerokosc; j++)
                System.out.print(mapa[i][j]);
            System.out.println();
        }

        }
        public void tick(){
        entityManager.tick();
        }
    public void render(Graphics g){
        int xStart = (int) Math.max(0, handler.gameCamera.xOffset / 30);
        int xEnd = (int) Math.min(szerokosc, (handler.gameCamera.xOffset + handler.kgui.width )/ 30 + 1);
        int yStart = (int) Math.max(0, handler.gameCamera.yOffset / 30);
        int yEnd = (int) Math.min(wysokosc, (handler.gameCamera.yOffset + handler.kgui.height )/ 30 + 1);

        for(int y = yStart;y < yEnd;y++){
            for(int x = xStart;x < xEnd;x++){
                getTile(x, y).render(g, (int) (x * 30 - handler.gameCamera.xOffset),
                        (int) (y * 30 - handler.gameCamera.yOffset));
            }
        }
        // Items
     //   itemManager.render(g);
        //Entities
        entityManager.render(g);
    }
    public Tile getTile(int x, int y){
        if(x < 0 || y < 0 || x >= handler.kgui.width || y >= handler.kgui.height)
            return Tile.grassTile;

        Tile t = Tile.tiles[mapa[y][x]];
        if(t == null)
            return Tile.dirtTile;
        return t;
    }
        public void stringNaMape(String smapa){
            String [] array=smapa.split("\\|");
            this.nazwa=array[1];
            this.wysokosc=Integer.valueOf(array[2]);
            this.szerokosc=Integer.valueOf(array[3]);
           this.mapa=new int[wysokosc][szerokosc];
            for(int i=0;i<wysokosc;i++)
            {
                for(int j=0;j<szerokosc;j++)
                {
                    mapa[i][j]=Integer.valueOf(array[szerokosc*i+j+4]);
                }
            }

    }
}
