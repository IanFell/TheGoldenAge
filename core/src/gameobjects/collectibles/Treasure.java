package gameobjects.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import gameobjects.GameObject;
import loaders.ImageLoader;
import missions.MissionBlacksIsland;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Treasure extends GameObject {

	private Rectangle rectangle;

	private boolean hasBeenCollected;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public Treasure(int x, int y) {
		this.x           = x;
		this.y           = y;
		int size         = 3;
		this.width       = size;
		this.height      = size;
		rectangle        = new Rectangle(x, y, width, height);
		hasBeenCollected = false;
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void update(GameObject player) {
		if (rectangle.overlaps(player.rectangle) && !hasBeenCollected) {
			MissionBlacksIsland.missionBlacksIslandComplete = true;
			hasBeenCollected                                = true;
			System.exit(0);
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
			batch.draw(imageLoader.chestClosed, x, y, width, height);
		}
	}
}
