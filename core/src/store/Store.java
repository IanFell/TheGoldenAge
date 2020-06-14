package store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gameobjects.GameObject;
import handlers.AnimationHandler;
import loaders.ImageLoader;
import missions.MissionRawBar;
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
	private final int SALE_PEARL  = 3;
	private final int SALE_AMMO   = 4;

	private int[] saleItems = {SALE_HEARTS, SALE_RUM, SALE_GUN, SALE_PEARL, SALE_AMMO};

	private boolean heartUnlocked       = false;
	private boolean rumUnlocked         = false;
	private boolean gunUnlocked         = false;
	public static boolean pearlUnlocked = false;
	public static boolean ammoUnlocked  = false;

	public static boolean gunPurchased   = false;
	public static boolean pearlPurchased = false;

	// Use these to make the store work.
	public static boolean mouseIsClickingOnPurchasingObject = false;
	public static boolean storeShouldBeRendered             = false;
	public static boolean gunHasBeenPurchasedAtStore        = false;
	public static boolean playerWantsToEnterStore           = false;
	// False for game to work.  True to debug.
	public static boolean storeIsUnlocked                   = false; 
	public static boolean shouldDisplayEnterStoreMessage    = false;

	private int itemSize   = 1;
	private int coinSize   = 1;
	private int numberSize = 1;

	public static boolean playSound = false;

	public static boolean playerIsShortOnLootMessageShouldRender = false;
	private int shortOnLootTimer                                 = 0;
	private final int SHORT_ON_LOOT_MAX_DISPLAY_VALUE            = 30;

	/**
	 * Constructor.
	 */
	public Store() {
		textureAtlas = new TextureAtlas(Gdx.files.internal("artwork/ui/loot.atlas"));
		animation    = new Animation <TextureRegion> (AnimationHandler.ANIMATION_SPEED_LOOT, textureAtlas.getRegions());
	}

	public void updateStore() {
		if (playerIsShortOnLootMessageShouldRender) {
			shortOnLootTimer++;
			if (shortOnLootTimer >= SHORT_ON_LOOT_MAX_DISPLAY_VALUE) {
				shortOnLootTimer                       = 0;
				playerIsShortOnLootMessageShouldRender = false;
			}
		}
		handleUnlockingOfItems();
	}

	private void handleUnlockingOfItems() {
		if (storeIsUnlocked) {
			heartUnlocked = true;
			rumUnlocked   = true;
			gunUnlocked   = true;
		}

		if (MissionRawBar.rawBarMissionComplete) {
			pearlUnlocked = true;
			ammoUnlocked  = true;
		}
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
			renderPrices(batch, imageLoader, playerX, playerY + 1);

			// Render this if player doesn't have enough loot to buy item.
			if (playerIsShortOnLootMessageShouldRender) {
				batch.draw(
						imageLoader.objectiveNotEnoughLoot, 
						playerX - 5, 
						playerY, 
						10, 
						-2
						);
			}

			batch.draw(
					imageLoader.objectiveExitStore, 
					playerX, 
					playerY - 2.5f, 
					5, 
					-1
					);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param float       xPos
	 * @param float       yPos
	 */
	private void renderPrices(SpriteBatch batch, ImageLoader imageLoader, float xPos, float yPos) {
		// Heart: Cost 5.
		if (heartUnlocked) {
			batch.draw(
					imageLoader.numberWhite[5], 
					xPos - 10.2f, 
					yPos, 
					numberSize, 
					-numberSize
					);
		}
		// Rum: Cost 15.
		if (rumUnlocked) {
			batch.draw(
					imageLoader.numberWhite[1], 
					xPos - 6.2f, 
					yPos, 
					numberSize, 
					-numberSize
					);
			batch.draw(
					imageLoader.numberWhite[5], 
					xPos - 5.6f, 
					yPos, 
					numberSize, 
					-numberSize
					);
		}
		// Gun: Cost 10.
		if (gunUnlocked && !gunPurchased) {
			batch.draw(
					imageLoader.numberWhite[1], 
					xPos - 1.8f, 
					yPos, 
					numberSize, 
					-numberSize
					);
			batch.draw(
					imageLoader.numberWhite[0], 
					xPos - 1.0f, 
					yPos, 
					numberSize, 
					-numberSize
					);
		}
		// Magic Pearl: Cost 10.
		if (pearlUnlocked && !pearlPurchased) {
			batch.draw(
					imageLoader.numberWhite[1], 
					xPos + 2.8f, 
					yPos, 
					numberSize, 
					-numberSize
					);
			batch.draw(
					imageLoader.numberWhite[0], 
					xPos + 3.5f, 
					yPos, 
					numberSize, 
					-numberSize
					);
		}
		// Ammo: Cost 5 (for 5 ammo).
		if (ammoUnlocked) {
			batch.draw(
					imageLoader.numberWhite[5], 
					xPos + 7.0f, 
					yPos, 
					numberSize, 
					-numberSize
					);
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
		if (heartUnlocked) {
			batch.draw(
					imageLoader.heart, 
					xPos - 11.2f, 
					yPos, 
					itemSize, 
					-itemSize
					);
		}
		if (rumUnlocked) {
			batch.draw(
					imageLoader.rum, 
					xPos - 6.9f, 
					yPos, 
					itemSize, 
					-itemSize
					);
		}
		if (gunUnlocked && !gunPurchased) {
			batch.draw(
					imageLoader.gunRight, 
					xPos - 2.7f, 
					yPos, 
					itemSize, 
					-itemSize
					);
		}
		if (pearlUnlocked && !pearlPurchased) {
			batch.draw(
					imageLoader.magicPearl, 
					xPos + 1.7f, 
					yPos, 
					itemSize, 
					-itemSize
					);
		}
		if (ammoUnlocked) {
			batch.draw(
					imageLoader.ammo, 
					xPos + 6.0f, 
					yPos, 
					itemSize, 
					-itemSize
					);
		}
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
		if (heartUnlocked) {
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
		}
		// Loot object for rum.
		if (rumUnlocked) {
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
		}
		// Loot object for gun.
		if (gunUnlocked && !gunPurchased) {
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
		}
		// Loot object for magic pearl.
		if (pearlUnlocked && !pearlPurchased) {
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
		}
		// Loot object for ammo.
		if (ammoUnlocked) {
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
		}
		// Loot object for last item.
		/*
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
		 */
	}
}
