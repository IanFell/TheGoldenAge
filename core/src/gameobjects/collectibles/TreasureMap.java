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

	private boolean hasBeenCollected;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public TreasureMap(int x, int y) {
		this.x           = x;
		this.y           = y;
		int size         = 2;
		this.width       = size;
		this.height      = size;
		rectangle        = new Rectangle(x, y - height, width, height);
		hasBeenCollected = false;
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void update(GameObject player) {
		if (rectangle.overlaps(player.rectangle) && !hasBeenCollected) {
			MissionThePoint.missionThePointComplete = true;
			hasBeenCollected                        = true;
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		if (!hasBeenCollected) {
			// TODO PUT IN SHADOW
			batch.draw(imageLoader.treasureMapRight, x, y, width, -height);
		}
	}
}
