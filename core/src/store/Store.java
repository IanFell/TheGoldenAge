package store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gameobjects.GameObject;
import handlers.AnimationHandler;
import loaders.ImageLoader;
import screens.GameScreen;
import ui.TextBasedUiParent;

/**
 * Extend TextBasedUiParent so we can use the animation and number variables.
 * 
 * @author Fabulous Fellini
 *
 */
public class Store extends TextBasedUiParent {

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
	public static boolean storeIsUnlocked                   = true; 
	public static boolean shouldDisplayEnterStoreMessage    = false;

	private int itemSize = 1;
	private int coinSize = 1;

	/**
	 * Constructor.
	 */
	public Store() {
		textureAtlas = new TextureAtlas(Gdx.files.internal("artwork/ui/loot.atlas"));
		animation    = new Animation <TextureRegion> (AnimationHandler.ANIMATION_SPEED_LOOT, textureAtlas.getRegions());
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param GameObject  player
	 */
	public void renderStore(SpriteBatch batch, ImageLoader imageLoader, GameObject player) {

		updateElapsedTime();

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

			float playerX = player.getX();
			float playerY = player.getY();
			renderItems(batch, imageLoader, playerX, playerY + 5);
			renderCoins(batch, imageLoader, playerX, playerY);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param float       xPos
	 * @param float       yPos
	 */
	private void renderItems(SpriteBatch batch, ImageLoader imageLoader, float xPos, float yPos) {
		batch.draw(
				imageLoader.heart, 
				xPos - 11.2f, 
				yPos, 
				itemSize, 
				-itemSize
				);
		batch.draw(
				imageLoader.rum, 
				xPos - 6.9f, 
				yPos, 
				itemSize, 
				-itemSize
				);
		batch.draw(
				imageLoader.gunRight, 
				xPos - 2.7f, 
				yPos, 
				itemSize, 
				-itemSize
				);
		batch.draw(
				imageLoader.oyster, 
				xPos + 1.7f, 
				yPos, 
				itemSize, 
				-itemSize
				);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param float       xPos
	 * @param float       yPos
	 */
	private void renderCoins(SpriteBatch batch, ImageLoader imageLoader, float xPos, float yPos) {
		// Loot object for health.
		AnimationHandler.renderAnimation(
				batch, 
				elapsedTime, 
				animation, 
				xPos - 11.2f, 
				yPos, 
				coinSize,
				-coinSize,
				imageLoader, 
				AnimationHandler.OBJECT_TYPE_LOOT
				);

		// Loot object for rum.
		AnimationHandler.renderAnimation(
				batch, 
				elapsedTime, 
				animation, 
				xPos - 7.0f, 
				yPos, 
				coinSize,
				-coinSize,
				imageLoader, 
				AnimationHandler.OBJECT_TYPE_LOOT
				);

		// Loot object for gun.
		AnimationHandler.renderAnimation(
				batch, 
				elapsedTime, 
				animation, 
				xPos - 2.8f, 
				yPos, 
				coinSize,
				-coinSize,
				imageLoader, 
				AnimationHandler.OBJECT_TYPE_LOOT
				);

		// Loot object for magic pearl.
		AnimationHandler.renderAnimation(
				batch, 
				elapsedTime, 
				animation, 
				xPos + 1.8f, 
				yPos, 
				coinSize,
				-coinSize,
				imageLoader, 
				AnimationHandler.OBJECT_TYPE_LOOT
				);

		// Loot object for next item.
		AnimationHandler.renderAnimation(
				batch, 
				elapsedTime, 
				animation, 
				xPos + 6.0f, 
				yPos, 
				coinSize,
				-coinSize,
				imageLoader, 
				AnimationHandler.OBJECT_TYPE_LOOT
				);

		// Loot object for last item.
		AnimationHandler.renderAnimation(
				batch, 
				elapsedTime, 
				animation, 
				xPos + 10.5f, 
				yPos, 
				coinSize,
				-coinSize,
				imageLoader, 
				AnimationHandler.OBJECT_TYPE_LOOT
				);
	}
}
