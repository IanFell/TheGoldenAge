package gameobjects.nature.shockplant;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import gameobjects.nature.NatureObject;
import handlers.CollisionHandler;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ShockPlant extends NatureObject {

	private int animationTimer = 0;
	
	public static boolean playSparkAudio = false;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public ShockPlant(int x, int y) {
		super(x, y);
		this.width       = 2;
		this.height      = 2;
		rectangle.x      = x;
		rectangle.y      = y - height;
		rectangle.width  = width;
		rectangle.height = height;
	}

	private void handleAnimationTimer() {
		animationTimer++;
		if (animationTimer > 40) {
			animationTimer = 0;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void updateObject(GameObject player) {
		handleAnimationTimer();
		CollisionHandler.checkIfPlayerHasCollidedWithShockPlant(player, this);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		if (animationTimer < 5) {
			batch.draw(imageLoader.shockPlants[0], x, y, width, -height);
		} else if (animationTimer > 5 && animationTimer < 10) {
			batch.draw(imageLoader.shockPlants[1], x, y, width, -height);
		} else if (animationTimer > 10 && animationTimer < 15) {
			batch.draw(imageLoader.shockPlants[2], x, y, width, -height);
		} else if (animationTimer > 15 && animationTimer < 20) {
			batch.draw(imageLoader.shockPlants[3], x, y, width, -height);
		} else if (animationTimer > 20 && animationTimer < 25) {
			batch.draw(imageLoader.shockPlants[4], x, y, width, -height);
		} else if (animationTimer > 25 && animationTimer < 30) {
			batch.draw(imageLoader.shockPlants[5], x, y, width, -height);
		} else if (animationTimer > 30 && animationTimer < 35) {
			batch.draw(imageLoader.shockPlants[6], x, y, width, -height);
		} else {
			batch.draw(imageLoader.shockPlants[7], x, y, width, -height);
		}
	}
}
