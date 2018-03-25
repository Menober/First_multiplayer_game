package Client;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Stworzone przez Krzyśka dnia 2017-09-18.
 */
public class Player extends Creature {

    public Player(KHandler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        bounds.x = 22;
        bounds.y = 44;
        bounds.width = 19;
        bounds.height = 19;

    }

    @Override
    public void tick() {

        //Movement

        try {
            getInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        move();

        handler.gameCamera.centerOnEntity(this);

    }


    private void getInput() throws IOException {
        xMove = 0;
        yMove = 0;
/*

        if(handler.keyManager.up)
            yMove = -speed;
        if(handler.keyManager.down)
            yMove = speed;
        if(handler.keyManager.left)
            xMove = -speed;
        if(handler.keyManager.right)
            xMove = speed;*/

        if(handler.keyManager.up)
        {
         handler.klient.move("UP");
        }

        if(handler.keyManager.down)
        {
            handler.klient.move("DOWN");
        }

        if(handler.keyManager.left)
        {
            handler.klient.move("LEFT");
        }

        if(handler.keyManager.right)
        {
            handler.klient.move("RIGHT");
        }
    }

    @Override
    public void render(Graphics g) {

            g.drawImage(Assets.player, (int) (x - handler.gameCamera.xOffset), (int) (y - handler.gameCamera.yOffset), width, height, null);

    }




}
