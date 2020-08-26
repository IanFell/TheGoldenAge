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

	private int poisonTimer;

	private final int POISON_TIMER_MAX = 2000;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public PoisonPlant(int x, int y) {
		super(x, y);
		this.width       = 1;
		this.height      = 1;
		rectangle.x      = x;
		rectangle.y      = y - height;
		rectangle.width  = width;
		rectangle.height = height;
		poisonTimer      = 0;
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void updateObject(GameObject player) {
		this.handlePoisonTimer();
		if (!Player.isInvincible) {
			handleCollision(player);
		}
	}

	private void handlePoisonTimer() {
		poisonTimer++;
		if (poisonTimer > POISON_TIMER_MAX) {
			poisonTimer = 0;
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
		batch.draw(imageLoader.shadow, x, y + 1, width, -height);
		if (poisonTimer < 200) {
			batch.draw(imageLoader.poisonPlantOne, x, y, width, -height);
		} else if (poisonTimer > 200 && poisonTimer < 400) {
			batch.draw(imageLoader.poisonPlantTwo, x, y, width, -height);
		} else if (poisonTimer > 400 && poisonTimer < 600) {
			batch.draw(imageLoader.poisonPlantThree, x, y, width, -height);
		} else if (poisonTimer > 600 && poisonTimer < 800) {
			batch.draw(imageLoader.poisonPlantFour, x, y, width, -height);
		} else if (poisonTimer > 800 && poisonTimer < 1000) {
			batch.draw(imageLoader.poisonPlantFive, x, y, width, -height);
		} else if (poisonTimer > 1000 && poisonTimer < 1200) {
			batch.draw(imageLoader.poisonPlantSix, x, y, width, -height);
		} else if (poisonTimer > 1200 && poisonTimer < 1400) {
			batch.draw(imageLoader.poisonPlantSeven, x, y, width, -height);
		} else if (poisonTimer > 1400 && poisonTimer < 1600) {
			batch.draw(imageLoader.poisonPlantEight, x, y, width, -height);
		} else if (poisonTimer > 1600 && poisonTimer < 1800) {
			batch.draw(imageLoader.poisonPlantNine, x, y, width, -height);
		} else /*if (poisonTimer > 450)*/ {
			batch.draw(imageLoader.poisonPlantTen, x, y, width, -height);
		} 
		//batch.draw(imageLoader.logs, x, y + 0.3f, width, -height);
	}
}
