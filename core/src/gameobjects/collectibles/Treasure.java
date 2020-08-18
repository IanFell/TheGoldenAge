package gameobjects.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import gameobjects.GameObject;
import loaders.ImageLoader;
import missions.MissionBlacksIsland;
import missions.MissionFinalFight;
import ui.Win;

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
		rectangle        = new Rectangle(x, y - height, width, height);
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
			MissionFinalFight.finalFightShouldBeSetup       = true;
			
			// Win the game.
			Win.triggerWin = true;
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
			batch.draw(imageLoader.chestClosed, x, y, width, -height);
		}
	}
}
