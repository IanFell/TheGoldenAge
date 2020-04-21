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

	public static boolean mouseIsClickingOnPurchasingObject = false;

	public static boolean storeShouldBeRendered = false;

	public static boolean gunHasBeenPurchasedAtStore = false;

	/**
	 * 
	 * @param GameObject player
	 */
	//public void updateStore(GameObject player) {}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param GameObject  player
	 */
	public void renderStore(SpriteBatch batch, ImageLoader imageLoader, GameObject player) {
		if (storeShouldBeRendered) {
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
