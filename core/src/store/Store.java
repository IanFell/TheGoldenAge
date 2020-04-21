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
	private final int SALE_GUN    = 1;

	private int[] saleItems = {SALE_HEARTS, SALE_GUN};

	/**
	 * 
	 * @param GameObject player
	 */
	public void updateStore(GameObject player) {}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param GameObject  player
	 */
	public void renderStore(SpriteBatch batch, ImageLoader imageLoader, GameObject player) {
		batch.draw(
				imageLoader.inventoryScreen, 
				player.getX() - 13.7f, 
				player.getY() + 8, 
				27.4f, 
				-GameScreen.camera.viewportHeight
				);
	}
}
