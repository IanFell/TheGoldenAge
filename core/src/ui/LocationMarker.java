package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import loaders.ImageLoader;

/**
 * This class represents the flashing skull to tell player where to go during a mission.
 * 
 * @author Fabulous Fellini
 *
 */
public class LocationMarker {

	public static boolean playSound                 = false;
	public static boolean playBeepSound             = false;
	public static boolean playerIsWithinSoundBounds = false;

	private int flashTimer;

	private Rectangle locator;
	private Rectangle hitBox;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public LocationMarker(float x, float y) {
		int size  = 1;
		locator   = new Rectangle(
				x, 
				y,
				size, 
				size
				);
		hitBox   = new Rectangle(
				x, 
				y,
				size, 
				size
				);
		flashTimer = 0;
	}

	/**
	 * 
	 * @return Rectangle
	 */
	public Rectangle getLocator() {
		return locator;
	}

	private void handleFlashTimer() {
		flashTimer++;
		if (flashTimer > 39) {
			flashTimer = 0;
		}
		if (flashTimer == 0 && playerIsWithinSoundBounds) {
			playBeepSound = true;
		}
	}

	public void updateObject() {
		handleFlashTimer();
		hitBox.x = locator.x;
		hitBox.y = locator.y;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		// Render shadow.
		batch.draw(
				imageLoader.skullShadow, 
				locator.x, 
				locator.y + 1,
				locator.width, 
				-locator.height
				);
		// Render skull.
		batch.draw(
				imageLoader.locationSkull, 
				locator.x, 
				locator.y,
				locator.width, 
				-locator.height
				);
		//renderHitBox(batch, imageLoader);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderHitBox(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.whiteSquare, 
				hitBox.x, 
				hitBox.y,
				hitBox.width, 
				-hitBox.height
				);
	}

	/**
	 * This method determines if skull should be rendered (flashing).
	 * 
	 * @return boolean
	 */
	public boolean timerValuesAreCorrectToFlash() {
		if (flashTimer < 19) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderLootMarker(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.lootAlternate, 
				locator.x, 
				locator.y,
				locator.width, 
				-locator.height
				);
	}
}
