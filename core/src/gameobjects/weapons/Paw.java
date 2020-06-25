package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.collectibles.GameObjectCollectible;
import gameobjects.gamecharacters.players.Player;
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
public class Paw extends GameObjectCollectible {

	public static boolean playCollectionSound = false;
	public static boolean hasBeenUsed         = false;
	public static boolean playAttackSound     = false;
	private boolean haveKilledEnemies         = false;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public Paw() {
		this.x                              = GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 46;
		this.y                              = GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 10;
		float size                          = 1f;
		this.width                          = size;
		this.height                         = size;
		rectangle.x                         = x;
		rectangle.y                         = y;
		rectangle.width                     = width;
		rectangle.height                    = height;
		hasBeenCollected                    = false;
		setMovement();
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		if (!hasBeenUsed) {
			CollisionHandler.checkIfPlayerHasCollidedWithPaw(myGame.getGameObject(Player.PLAYER_ONE), this);
			handleMovement();
		} else {
			if (!haveKilledEnemies) {
				for (int k = 0; k < myGame.gameScreen.enemyHandler.enemySpawner.length; k++) {
					if (myGame.gameScreen.enemyHandler.enemySpawner[k].enemies != null) {
						for (int enemy = 0; enemy < myGame.gameScreen.enemyHandler.enemySpawner[k].enemies.size(); enemy++) {
							if (myGame.gameScreen.enemyHandler.enemySpawner[k].enemies.get(enemy) != null) {
								myGame.gameScreen.enemyHandler.enemySpawner[k].enemies.get(enemy).setIsDead(true);
							}
						}
					}
				}
				haveKilledEnemies = true;
			}
		}
	}

	/**
	 * 
	 * @param SpriteBatch  batch
	 * @param ImageLoader  imageLoader
	 * @param MyGame       myGame
	 */
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(this) && !hasBeenUsed) {
			if (!hasBeenCollected && !MapUi.mapShouldBeRendered && !Inventory.allInventoryShouldBeRendered && !Store.storeShouldBeRendered) {
				batch.draw(
						imageLoader.paw, 
						x, 
						y, 
						width, 
						-height
						);
			}
			else if (Inventory.allInventoryShouldBeRendered) {
				batch.draw(
						imageLoader.paw, 
						x, 
						y, 
						width, 
						-height
						);
			} else if (myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) instanceof Paw) {
				batch.draw(
						imageLoader.paw, 
						x, 
						y, 
						width, 
						-height
						);
			}
		}
	}
}
