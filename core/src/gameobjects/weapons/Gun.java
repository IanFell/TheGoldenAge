package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import handlers.collectibles.AmmoHandler;
import helpers.GamePlayHelper;
import inventory.Inventory;
import loaders.ImageLoader;
import loaders.bulletloader.BulletLoader;
import maps.MapHandler;
import store.Store;
import ui.GameOver;
import ui.MapUi;
import ui.OutOfAmmo;
import ui.Win;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Gun extends Weapon {

	public static boolean hasBeenCollected;

	public static boolean shouldNotRender = true;

	private int rotationAngle;

	private TextureRegion textureRegion;

	public static boolean playCollectionSound = false;
	
	public static void resetGame() {
		playCollectionSound = false;
		shouldNotRender     = true;
		hasBeenCollected    = false;
	}

	/**
	 * Constructor.
	 * 
	 * @param float       x
	 * @param float       y
	 * @param ImageLoader imgageLoader 
	 */
	public Gun(float x, float y, ImageLoader imageLoader) {
		super(x, y);
		this.width            = 1.0f;
		this.height           = 1.0f;
		rectangle.width       = width;
		rectangle.height      = height;
		hasBeenCollected      = false;
		this.rotationAngle    = 0;
		textureRegion         = new TextureRegion(imageLoader.gunLeft);
	}

	/**
	 * 
	 * @param int rotationAngle
	 */
	public void setRotationAngle(int rotationAngle) {
		this.rotationAngle = rotationAngle;
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);
		if (!hasBeenCollected) {
			// Don't need this, because the player now BUYS the gun from the Trading Post.
			/*
			CollisionHandler.checkIfPlayerHasCollidedWithGun(
					PlayerController.getCurrentPlayer(myGame),
					this
					); */
		} else {
			setRotationAngleDependingOnPlayerDirection(myGame.imageLoader);
		}

		rectangle.x = x;
		rectangle.y = y;

		if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.size() > 0) {
			if (
					Player.playerIsPerformingAttack && 
					myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof Gun && 
					AmmoHandler.ammoCount > 0 &&
					!Store.playerWantsToEnterStore
					) {
				if (AmmoHandler.ammoCount > 0) {
					BulletLoader.createBullet(myGame);
					Player.playerIsPerformingAttack = false;
				} else {
					// Make buzzer sound.
					OutOfAmmo.playBuzzerAudio = true;
				}
			} else if (Player.playerIsPerformingAttack && AmmoHandler.ammoCount <= 0) {
				OutOfAmmo.shouldRender = true;
			}
		}

		if (Inventory.allInventoryShouldBeRendered) {
			rotationAngle = 0;
		}
	}

	/**
	 * 
	 * @param ImageLoader imageLoader
	 */
	private void setRotationAngleDependingOnPlayerDirection(ImageLoader imageLoader) {
		int rotationAngle0Degrees  = 0;
		switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
		case DIRECTION_RIGHT:
			setRotationAngle(rotationAngle0Degrees);
			textureRegion = new TextureRegion(imageLoader.gunRight);
			break;
		case DIRECTION_LEFT:
			setRotationAngle(rotationAngle0Degrees);
			textureRegion = new TextureRegion(imageLoader.gunLeft);
			break;
		case DIRECTION_DOWN:
			setRotationAngle(rotationAngle0Degrees);
			textureRegion = new TextureRegion(imageLoader.gunDown);
			break;
		case DIRECTION_UP:
			setRotationAngle(rotationAngle0Degrees);
			textureRegion = new TextureRegion(imageLoader.gunUp);
			break;
		}
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 * @param MyGame        myGame
	 */
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (!GameOver.triggerGameOver && !Win.triggerWin) {
			int value = 1;
			if (GamePlayHelper.gameObjectIsWithinScreenBounds(this)) {
				// Only render gun if we have enough loot and have entered trading post to buy gun.
				if (TradingPost.hasBeenEntered) {
					if (!hasBeenCollected) {	
						if (!MapUi.mapShouldBeRendered && !Inventory.allInventoryShouldBeRendered) {
							/**
							 * This is commented out because now, player has to BUY the gun from the Trading Post, 
							 * and this is no longer needed.  
							 * 
							 * Keep it for now in case we change the gun back to a regular collectible.
							 */
							/*
						batch.draw(
								textureRegion, 
								x, 
								y, 
								width, 
								height, 
								width, 
								-height, 
								value, 
								value, 
								rotationAngle
								); */
						}
					} else if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) == this && Inventory.inventoryIsEquipped && !Inventory.allInventoryShouldBeRendered) {
						//System.out.println("getting here");
						batch.draw(
								textureRegion, 
								x, 
								y, 
								width, 
								height, 
								width, 
								-height, 
								value, 
								value, 
								rotationAngle
								);
					} else if (Inventory.allInventoryShouldBeRendered) {
						textureRegion = new TextureRegion(imageLoader.gunRight);
						batch.draw(
								textureRegion, 
								x, 
								y, 
								width, 
								height, 
								width, 
								-height, 
								value, 
								value, 
								0
								);
					}
				}
			}
		}
	}
}
