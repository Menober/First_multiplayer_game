package Client;

/**
 * Stworzone przez Krzyśka dnia 2017-09-17.
 */
public class GameCamera {
    private KHandler handler;
    public float xOffset,yOffset;
    public GameCamera(KHandler handler, float xOffset, float yOffset){
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }


    public void checkBlankSpace(){
        if(xOffset < 0){
            xOffset = 0;
        }else if(xOffset > handler.kgui.width * 30 - handler.kgui.width){
            xOffset = handler.kgui.width * 30 - handler.kgui.width;
        }

        if(yOffset < 0){
            yOffset = 0;
        }else if(yOffset > handler.kgui.height * 30 - handler.kgui.height){
            yOffset = handler.kgui.height * 30 - handler.kgui.height ;
        }
    }

    public void centerOnEntity(Entity e){
        xOffset = e.getX() - handler.kgui.width / 2 + e.getWidth() / 2;
        yOffset = e.getY() - handler.kgui.height / 2 + e.getHeight() / 2;
        checkBlankSpace();
    }
}
