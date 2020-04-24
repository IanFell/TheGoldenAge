package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.collectibles.shadows.HeartShadow;
import handlers.collectibles.HeartHandler;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CollectibleShadowHandler {

	private HeartShadow[] heartShadow = new HeartShadow[HeartHandler.MAX_AMOUNT_HEARTS_ALLOWED];

	/**
	 * Constructor.
	 * 
	 * @param ImageLoader imageLoader
	 */
	public CollectibleShadowHandler(ImageLoader imageLoader) {
		for (int i = 0; i < HeartHandler.MAX_AMOUNT_HEARTS_ALLOWED; i++) {
			heartShadow[i] = new HeartShadow(
					HeartHandler.hearts.get(i).getX(),
					HeartHandler.hearts.get(i).getY() + 1,
					HeartHandler.hearts.get(i).getWidth(),
					HeartHandler.hearts.get(i).getHeight(),
					imageLoader.heartShadow
					);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderCollectibleShadows(SpriteBatch batch, ImageLoader imageLoader) {
		for (int i = 0; i < HeartHandler.MAX_AMOUNT_HEARTS_ALLOWED; i++) {
			if (!HeartHandler.hearts.get(i).hasBeenCollected) {
				heartShadow[i].renderObject(batch, imageLoader);
			}
		}

	}
}
