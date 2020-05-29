package gameobjects.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import gameobjects.GameObject;
import loaders.ImageLoader;
import missions.MissionThePoint;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class TreasureMap extends GameObject {

	private Rectangle rectangle;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public TreasureMap(int x, int y) {
		this.x      = x;
		this.y      = y;
		int size    = 1;
		this.width  = size;
		this.height = size;
		rectangle   = new Rectangle(x, y, width, height);
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void update(GameObject player) {
		if (rectangle.overlaps(player.rectangle)) {
			MissionThePoint.missionThePointComplete = true;
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.whiteSquare, x, y, width, height);
	}
}
