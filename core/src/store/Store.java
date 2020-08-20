package store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import gameobjects.GameObject;
import handlers.AnimationHandler;
import input.controllers.ControllerInput;
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
	
	public static boolean playBuzzerAudio = false;

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

	private int itemSize      = 1;
	private int coinSize      = 1;
	private int numberSize    = 1;
	private float nameUiWidth = 4.5f;
	private int nameUiHeight  = 1;

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
			if (shortOnLootTimer < 1) {
				playBuzzerAudio = true;
			}
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
					player.getX() - 7, 
					player.getY(), 
					14, 
					-4
					);
		}

		if (storeShouldBeRendered && playerWantsToEnterStore) {
			// Place a black square in the background to cover the remaining part of the screen.
			batch.draw(
					imageLoader.blackSquare,
					player.getX() - 13.7f, 
					player.getY() + GameScreen.camera.viewportHeight / 2, 
					GameScreen.camera.viewportWidth, 
					-GameScreen.camera.viewportHeight
					);
			batch.draw(
					imageLoader.storeUi, 
					player.getX() - 13.7f, 
					player.getY() + 8, 
					GameScreen.camera.viewportWidth, 
					-GameScreen.camera.viewportHeight
					);

			float playerX = player.getX();
			float playerY = player.getY();
			renderItems(batch, imageLoader, playerX, playerY + 5);
			renderSquareOverSelectedItem(batch, imageLoader, playerX - 1.4f, playerY + 6.2f);
			renderItemNames(batch, imageLoader, playerX - 13, playerY + 6.3f);
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
	private void renderSquareOverSelectedItem(SpriteBatch batch, ImageLoader imageLoader, float xPos, float yPos) {
		switch (ControllerInput.storeObjectNumber) {
		case SALE_HEARTS:
			batch.draw(
					imageLoader.transparentSquare, 
					xPos - 11.5f, 
					yPos, 
					4.3f, 
					-3.8f
					);
			break;
		case SALE_RUM:
			batch.draw(
					imageLoader.transparentSquare, 
					xPos - 11.5f + 4.7f, 
					yPos, 
					3.7f, 
					-3.8f
					);
			break;
		case SALE_GUN:
			batch.draw(
					imageLoader.transparentSquare, 
					xPos - 11.5f + 4.5f + 4.3f, 
					yPos, 
					4.0f, 
					-3.8f
					);
			break;
		case SALE_PEARL:
			batch.draw(
					imageLoader.transparentSquare, 
					xPos - 11.5f + 4.5f + 4.3f + 4.3f, 
					yPos, 
					4.0f, 
					-3.8f
					);
			break;
		case SALE_AMMO:
			batch.draw(
					imageLoader.transparentSquare, 
					xPos - 11.5f + 4.5f + 4.3f + 4.3f + 4.3f, 
					yPos, 
					4.0f, 
					-3.8f
					);
			break;
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param float       xPos
	 * @param float       yPos
	 */
	private void renderItemNames(SpriteBatch batch, ImageLoader imageLoader, float xPos, float yPos) {
		if (heartUnlocked) {
			batch.draw(
					imageLoader.healthUi, 
					xPos, 
					yPos, 
					nameUiWidth, 
					-nameUiHeight
					);
		}
		if (rumUnlocked) {
			batch.draw(
					imageLoader.rumUi, 
					xPos + nameUiWidth + 0.2f, 
					yPos, 
					nameUiWidth - 0.6f, 
					-nameUiHeight
					);
		}
		if (gunUnlocked && !gunPurchased) {
			batch.draw(
					imageLoader.gunUi, 
					xPos + nameUiWidth * 2 - 0.2f, 
					yPos, 
					nameUiWidth - 0.3f, 
					-nameUiHeight
					);
		}
		if (pearlUnlocked && !pearlPurchased) {
			batch.draw(
					imageLoader.magicPearlUi, 
					xPos + nameUiWidth * 3 - 0.4f, 
					yPos, 
					nameUiWidth - 0.3f, 
					-nameUiHeight
					);
		}
		if (ammoUnlocked) {
			batch.draw(
					imageLoader.ammoUi, 
					xPos + nameUiWidth * 4 - 0.4f, 
					yPos, 
					nameUiWidth - 0.3f, 
					-nameUiHeight
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
					imageLoader.numberBlack[5], 
					xPos - 10.2f, 
					yPos, 
					numberSize, 
					-numberSize
					);
		}
		// Rum: Cost 15.
		if (rumUnlocked) {
			batch.draw(
					imageLoader.numberBlack[1], 
					xPos - 6.2f, 
					yPos, 
					numberSize, 
					-numberSize
					);
			batch.draw(
					imageLoader.numberBlack[5], 
					xPos - 5.6f, 
					yPos, 
					numberSize, 
					-numberSize
					);
		}
		// Gun: Cost 10.
		if (gunUnlocked && !gunPurchased) {
			batch.draw(
					imageLoader.numberBlack[1], 
					xPos - 1.8f, 
					yPos, 
					numberSize, 
					-numberSize
					);
			batch.draw(
					imageLoader.numberBlack[0], 
					xPos - 1.0f, 
					yPos, 
					numberSize, 
					-numberSize
					);
		}
		// Magic Pearl: Cost 10.
		if (pearlUnlocked && !pearlPurchased) {
			batch.draw(
					imageLoader.numberBlack[1], 
					xPos + 2.8f, 
					yPos, 
					numberSize, 
					-numberSize
					);
			batch.draw(
					imageLoader.numberBlack[0], 
					xPos + 3.5f, 
					yPos, 
					numberSize, 
					-numberSize
					);
		}
		// Ammo: Cost 5 (for 5 ammo).
		if (ammoUnlocked) {
			batch.draw(
					imageLoader.numberBlack[5], 
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
			batch.draw(imageLoader.lootAlternate, xPos - 11.5f, yPos, coinSize, coinSize);
			/*
			AnimationHandler.renderAnimation(
					batch, 
					elapsedTime, 
					animation, 
					xPos - 11.5f, 
					yPos, 
					coinSize,
					-coinSize,
					imageLoader, 
					AnimationHandler.OBJECT_TYPE_LOOT
					); */
		}
		// Loot object for rum.
		if (rumUnlocked) {
			batch.draw(imageLoader.lootAlternate, xPos - 7.5f, yPos, coinSize, coinSize);
			/*
			AnimationHandler.renderAnimation(
					batch, 
					elapsedTime, 
					animation, 
					xPos - 7.5f, 
					yPos, 
					coinSize,
					-coinSize,
					imageLoader, 
					AnimationHandler.OBJECT_TYPE_LOOT
					); */
		}
		// Loot object for gun.
		if (gunUnlocked && !gunPurchased) {
			batch.draw(imageLoader.lootAlternate, xPos - 3.3f, yPos, coinSize, coinSize);
			/*
			AnimationHandler.renderAnimation(
					batch, 
					elapsedTime, 
					animation, 
					xPos - 3.3f, 
					yPos, 
					coinSize,
					-coinSize,
					imageLoader, 
					AnimationHandler.OBJECT_TYPE_LOOT
					); */
		}
		// Loot object for magic pearl.
		if (pearlUnlocked && !pearlPurchased) {
			batch.draw(imageLoader.lootAlternate, xPos + 1.3f, yPos, coinSize, coinSize);
			/*
			AnimationHandler.renderAnimation(
					batch, 
					elapsedTime, 
					animation, 
					xPos + 1.3f, 
					yPos, 
					coinSize,
					-coinSize,
					imageLoader, 
					AnimationHandler.OBJECT_TYPE_LOOT
					); */
		}
		// Loot object for ammo.
		if (ammoUnlocked) {
			batch.draw(imageLoader.lootAlternate, xPos + 5.5f, yPos, coinSize, coinSize);
			/*
			AnimationHandler.renderAnimation(
					batch, 
					elapsedTime, 
					animation, 
					xPos + 5.5f, 
					yPos, 
					coinSize,
					-coinSize,
					imageLoader, 
					AnimationHandler.OBJECT_TYPE_LOOT
					); */
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

	public static void resetStore() {
		gunHasBeenPurchasedAtStore     = false;
		playerWantsToEnterStore        = false;
		shouldDisplayEnterStoreMessage = false;
	}
}
