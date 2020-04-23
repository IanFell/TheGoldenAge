package store;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import loaders.ImageLoader;
import screens.GameScreen;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Store {

	private final int SALE_HEARTS = 0;
	private final int SALE_RUM    = 1;
	private final int SALE_GUN    = 2;

	private int[] saleItems = {SALE_HEARTS, SALE_RUM, SALE_GUN};

	// Use these to make the store work.
	public static boolean mouseIsClickingOnPurchasingObject = false;
	public static boolean storeShouldBeRendered             = false;
	public static boolean gunHasBeenPurchasedAtStore        = false;
	public static boolean playerWantsToEnterStore           = false;
	// False for game to work.  True to debug.
	public static boolean storeIsUnlocked                   = false; 
	public static boolean shouldDisplayEnterStoreMessage    = false;

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param GameObject  player
	 */
	public void renderStore(SpriteBatch batch, ImageLoader imageLoader, GameObject player) {
		if (playerWantsToEnterStore) {
			storeShouldBeRendered = true;
		}

		if (shouldDisplayEnterStoreMessage) {
			batch.draw(
					imageLoader.objectiveEnterStore, 
					player.getX() - 5, 
					player.getY(), 
					10, 
					-2
					);
		}

		if (storeShouldBeRendered && playerWantsToEnterStore) {
			batch.draw(
					imageLoader.storeUi, 
					player.getX() - 13.7f, 
					player.getY() + 8, 
					27.4f, 
					-GameScreen.camera.viewportHeight
					);

			batch.draw(
					imageLoader.heart, 
					player.getX() - 11.2f, 
					player.getY() + 5, 
					1, 
					-1
					);
			batch.draw(
					imageLoader.rum, 
					player.getX() - 6.9f, 
					player.getY() + 5, 
					1, 
					-1
					);
			batch.draw(
					imageLoader.gunRight, 
					player.getX() - 2.7f, 
					player.getY() + 5, 
					1, 
					-1
					);
		}
	}
}
