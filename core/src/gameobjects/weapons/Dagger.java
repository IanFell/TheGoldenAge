package gameobjects.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.collectibles.GameObjectCollectible;
import gameobjects.gamecharacters.enemies.Boss;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import handlers.CollisionHandler;
import handlers.enemies.GiantHandler;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
import inventory.Inventory;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import maps.MapHandler;
import store.Store;
import ui.GameOver;
import ui.MapUi;
import ui.Win;

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
		this.x           = GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 31;
		this.y           = GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 37.5f;
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
		} else {
			rectangle.x = x;
			rectangle.y = y;
			myGame.gameScreen.enemyHandler.checkWeaponCollision(myGame, this);
			myGame.gameScreen.gruntHandler.checkWeaponCollision(myGame, this);
			for (int i = 0; i < BossLoader.boss.length; i++) {
				CollisionHandler.checkIfDaggerHasCollidedWithBoss((Boss) BossLoader.boss[i], this);
			}
			CollisionHandler.checkIfDaggerHasCollidedWithGiant(GiantHandler.giants[0], this);
			CollisionHandler.checkIfDaggerHasCollidedWithGiant(GiantHandler.giants[1], this);
			CollisionHandler.checkIfDaggerHasCollidedWithGiant(GiantHandler.giants[2], this);
		}
	}

	/**
	 * 
	 * @param SpriteBatch  batch
	 * @param ImageLoader  imageLoader
	 * @param MyGame       myGame
	 */
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (!GameOver.triggerGameOver && !Win.triggerWin) {
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
					int daggerDirection   = PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1);
					float xPosition       = 0;
					float yPosition       = 0;
					Texture daggerTexture = imageLoader.daggerUp;
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
						//Player.playerIsPerformingAttack = false;
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
							switch (daggerDirection) {
							case Player.DIRECTION_RIGHT:
								xPosition = x - 2.7f;
								yPosition = y + 1.7f;
								// Compensate for player being larger if he's invincible.
								if (Player.isInvincible) {
									xPosition = x - 2.5f;
								}
								daggerTexture = imageLoader.daggerRight;
								break;
							case Player.DIRECTION_LEFT:
								xPosition = x + 2.7f;
								yPosition = y + 1.7f;
								daggerTexture = imageLoader.daggerLeft;
								break;
							case Player.DIRECTION_DOWN:
								xPosition = x;
								yPosition = y;
								daggerTexture = imageLoader.daggerDown;
								break;
							case Player.DIRECTION_UP:
								xPosition = x;
								yPosition = y + 3;
								// Compensate for player being larger if he's invincible.
								if (Player.isInvincible) {
									yPosition = y + 2;
								}
								daggerTexture = imageLoader.daggerUp;
								break;
							}
						}
						batch.draw(daggerTexture, xPosition, yPosition, width, -height);
					}
				}
			}
		}
	}
}
