package physics.Lighting;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.collectibles.shadows.AmmoShadow;
import gameobjects.collectibles.shadows.HeartShadow;
import gameobjects.collectibles.shadows.RumShadow;
import handlers.collectibles.AmmoHandler;
import handlers.collectibles.HeartHandler;
import handlers.collectibles.RumHandler;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CollectibleShadowHandler {

	private HeartShadow[] heartShadow = new HeartShadow[HeartHandler.MAX_AMOUNT_HEARTS_ALLOWED];
	private RumShadow[] rumShadow     = new RumShadow[RumHandler.MAX_AMOUNT_RUM_ALLOWED];
	private AmmoShadow[] ammoShadow   = new AmmoShadow[AmmoHandler.MAX_AMOUNT_AMMO_ALLOWED_IN_WORLD_TO_COLLECT];

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

		for (int i = 0; i < RumHandler.MAX_AMOUNT_RUM_ALLOWED; i++) {
			rumShadow[i] = new RumShadow(
					RumHandler.rum.get(i).getX(),
					RumHandler.rum.get(i).getY() + 1.5f,
					RumHandler.rum.get(i).getWidth(),
					RumHandler.rum.get(i).getHeight(),
					imageLoader.rumShadow
					);
		}

		for (int i = 0; i < AmmoHandler.MAX_AMOUNT_AMMO_ALLOWED_IN_WORLD_TO_COLLECT; i++) {
			ammoShadow[i] = new AmmoShadow(
					AmmoHandler.ammo.get(i).getX(),
					AmmoHandler.ammo.get(i).getY() + 1.3f,
					AmmoHandler.ammo.get(i).getWidth(),
					AmmoHandler.ammo.get(i).getHeight(),
					imageLoader.ammoShadow
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
		for (int i = 0; i < RumHandler.MAX_AMOUNT_RUM_ALLOWED; i++) {
			if (!RumHandler.rum.get(i).hasBeenCollected) {
				rumShadow[i].renderObject(batch, imageLoader);
			}
		}
		for (int i = 0; i < AmmoHandler.MAX_AMOUNT_AMMO_ALLOWED_IN_WORLD_TO_COLLECT; i++) {
			if (!AmmoHandler.ammo.get(i).hasBeenCollected) {
				ammoShadow[i].renderObject(batch, imageLoader);
			}
		}
	}
}
