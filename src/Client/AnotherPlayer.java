package Client;

import java.awt.*;

/**
 * Stworzone przez Krzyśka dnia 2017-09-19.
 */
public class AnotherPlayer {
    public int id;
    public String nazwa;
    public int x,y;
    public int lvl;
    KHandler handler;
    int width=30,height=30;

    public AnotherPlayer(KHandler handler,String nazwa,int id,int lvl,int x,int y){
       this.handler=handler;
        this.nazwa=nazwa;
        this.id=id;
        this.lvl=lvl;
        this.x=x;
        this.y=y;

    }
    public void setX(int x){
        this.x=x;
    }
    public void setY(int y){
        this.y=y;
    }

    public void tick() {

    }


    public void render(Graphics g) {
        g.drawImage(Assets.anotherplayer, (int) (x - handler.gameCamera.xOffset), (int) (y - handler.gameCamera.yOffset), width, height, null);

    }
}
