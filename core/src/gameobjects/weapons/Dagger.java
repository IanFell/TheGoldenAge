package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.collectibles.GameObjectCollectible;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import handlers.CollisionHandler;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
import inventory.Inventory;
import loaders.ImageLoader;
import maps.MapHandler;
import store.Store;
import ui.MapUi;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Dagger extends GameObjectCollectible {

	public static boolean playCollectionSound = false;

	/**
	 * Constructor.
	 */
	public Dagger() {
		super();
		this.x           = GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 46;
		this.y           = GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 10;
		int size         = 1;
		this.width       = size;
		this.height      = size;
		rectangle.x      = x;
		rectangle.y      = y;
		rectangle.width  = width;
		rectangle.height = height;
		hasBeenCollected = false;
		setMovement();
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		if (!hasBeenCollected) {
			CollisionHandler.checkIfPlayerHasCollidedWithDagger(myGame.getGameObject(Player.PLAYER_ONE), this);
			handleMovement();
		}

	}

	/**
	 * 
	 * @param SpriteBatch  batch
	 * @param ImageLoader  imageLoader
	 * @param MyGame       myGame
	 */
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(this)) {
			if (!hasBeenCollected && !MapUi.mapShouldBeRendered && !Inventory.allInventoryShouldBeRendered && !Store.storeShouldBeRendered) {
				batch.draw(
						imageLoader.daggerUp, 
						x, 
						y, 
						width, 
						-height
						);
			}
			else if (Inventory.allInventoryShouldBeRendered) {
				batch.draw(
						imageLoader.daggerUp, 
						x, 
						y, 
						width, 
						-height
						);
			} else if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof Dagger) {
				float xPosition = 0;
				float yPosition = 0;
				if (Player.playerIsPerformingAttack) {
					if (Player.isInWater) {
						/*
						switch (Player.direction) {
						case Player.DIRECTION_RIGHT:
							xPosition = x + 4.5f;
							yPosition = y - 0.5f;
							break;
						case Player.DIRECTION_LEFT:
							xPosition = x - 4.5f;
							yPosition = y - 0.5f;
							break;
						case Player.DIRECTION_DOWN:
							xPosition = x - 0.3f;
							yPosition = y + height + 2;
							break;
						case Player.DIRECTION_UP:
							xPosition = x - 0.3f;
							yPosition = y - height - 3;
							break; 
						} */
					} else {
						/*
						switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
						case Player.DIRECTION_RIGHT:
							xPosition = x + 4;
							yPosition = y - 1.5f;
							break;
						case Player.DIRECTION_LEFT:
							xPosition = x - 4.5f;
							yPosition = y - 1.5f;
							break;
						case Player.DIRECTION_DOWN:
							xPosition = x - 0.3f;
							yPosition = y + height + 1;
							break;
						case Player.DIRECTION_UP:
							xPosition = x - 0.3f;
							yPosition = y - height - 4;
							break;
						}*/
					}
					Player.playerIsPerformingAttack = false;
				} else {
					if (Player.isInWater) {
						/*
						switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
						case Player.DIRECTION_RIGHT:
							xPosition = x + 3;
							yPosition = y - 0.5f;
							break;
						case Player.DIRECTION_LEFT:
							xPosition = x - 3.5f;
							yPosition = y - 0.5f;
							break;
						case Player.DIRECTION_DOWN:
							xPosition = x - 0.3f;
							yPosition = y + height + 1;
							break;
						case Player.DIRECTION_UP:
							xPosition = x - 0.3f;
							yPosition = y - height - 2;
							break;
						}*/
					} else {
						switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
						case Player.DIRECTION_RIGHT:
							xPosition = x - 3;
							yPosition = y + 1;
							// Compensate for player being larger if he's invincible.
							if (Player.isInvincible) {
								xPosition = x - 1;
							}
							break;
						case Player.DIRECTION_LEFT:
							xPosition = x + 3;
							yPosition = y + 1;
							break;
						case Player.DIRECTION_DOWN:
							xPosition = x;
							yPosition = y;
							break;
						case Player.DIRECTION_UP:
							xPosition = x;
							yPosition = y + 3;
							// Compensate for player being larger if he's invincible.
							if (Player.isInvincible) {
								yPosition = y + 2;
							}
							break;
						}
					}
					batch.draw(
							imageLoader.daggerUp, 
							xPosition, 
							yPosition, 
							width, 
							-height
							);
				}
			}
		}
	}
}
