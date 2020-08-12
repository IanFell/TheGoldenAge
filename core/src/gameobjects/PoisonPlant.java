package gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.gamecharacters.players.Player;
import gameobjects.nature.NatureObject;
import handlers.CollisionHandler;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class PoisonPlant extends NatureObject {

	public static boolean playPoisonAudio = false;

	public static boolean shouldCheckCollision = true;
	public static int collisionTimer           = 0;

	public static boolean playPoisonSound = false;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public PoisonPlant(int x, int y) {
		super(x, y);
		this.width       = 2;
		this.height      = 2;
		rectangle.x      = x;
		rectangle.y      = y - height;
		rectangle.width  = width;
		rectangle.height = height;
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void updateObject(GameObject player) {
		//handleAnimationTimer();
		if (!Player.isInvincible) {
			handleCollision(player);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 */
	private void handleCollision(GameObject player) {
		if (shouldCheckCollision) {
			CollisionHandler.checkIfPlayerHasCollidedWithPoisonPlant(player, this);
			shouldCheckCollision = false;
		}
		if (!shouldCheckCollision) {
			collisionTimer++;
			if (collisionTimer > 50) {
				shouldCheckCollision = true;
				collisionTimer       = 0;
			}
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.poisonPlantOne, x, y, width, -height);
		/*
		if (animationTimer < 5) {
			batch.draw(imageLoader.poisonPlantOne, x, y, width, -height);
		} else if (animationTimer > 5 && animationTimer < 10) {
			batch.draw(imageLoader.poisonPlantTwo, x, y, width, -height);
		} else if (animationTimer > 10 && animationTimer < 15) {
			batch.draw(imageLoader.poisonPlantThree, x, y, width, -height);
		} else if (animationTimer > 15 && animationTimer < 20) {
			batch.draw(imageLoader.poisonPlantTwo, x, y, width, -height);
		} else if (animationTimer > 20 && animationTimer < 25) {
			batch.draw(imageLoader.poisonPlantFour, x, y, width, -height);
		} else if (animationTimer > 25 && animationTimer < 30) {
			batch.draw(imageLoader.poisonPlantThree, x, y, width, -height);
		} else if (animationTimer > 30 && animationTimer < 35) {
			batch.draw(imageLoader.poisonPlantTwo, x, y, width, -height);
		} else {
			batch.draw(imageLoader.poisonPlantOne, x, y, width, -height);
		} */
	}
}
