package Client;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {
	
	private static final int width = 30, height = 30;

	
	public static BufferedImage dirt, grass, stone;
	public static BufferedImage player, anotherplayer;


	public static void init(){

		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/sheet.png"));

		dirt = sheet.crop(0, 0, width, height);
		grass = sheet.crop(width * 1, 0, width, height);
		stone = sheet.crop(width * 2, 0, width, height);
		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/player.png"));
		player=sheet.crop(width*0,0,width,height);


		sheet = new SpriteSheet(ImageLoader.loadImage("/textures/anotherplayer.png"));
		anotherplayer=sheet.crop(width*0,0,width,height);

	}
	
}
