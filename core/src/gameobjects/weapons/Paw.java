package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.collectibles.GameObjectCollectible;
import gameobjects.gamecharacters.players.Player;
import handlers.CollisionHandler;
import helpers.GameAttributeHelper;
import helpers.GamePlayHelper;
import inventory.Inventory;
import loaders.ImageLoader;
import maps.MapHandler;
import screens.GameScreen;
import store.Store;
import ui.GameOver;
import ui.MapUi;
import ui.Win;

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

	private final int DEAD_TIME = 5000;
	private int deadTimer       = 0;

	private boolean screenShouldShake   = true;
	private int shakeTimer              = 0;
	private final int SCREEN_SHAKE_TIME = 100;

	private boolean displayPawOnScreen      = false;
	private int displayPawOnScreenTimer     = 0;
	private final int REEMERGE_DISPLAY_TIME = 100;
	
	public static void resetGame() {
		playCollectionSound = false;
		hasBeenUsed         = false;
		playAttackSound     = false;
	}

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public Paw() {
		this.x                              = GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 146;
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
				for (int k = 0; k < myGame.gameScreen.gruntHandler.gruntSpawner.length; k++) {
					if (myGame.gameScreen.gruntHandler.gruntSpawner[k].grunts != null) {
						for (int enemy = 0; enemy < myGame.gameScreen.gruntHandler.gruntSpawner[k].grunts.size(); enemy++) {
							if (myGame.gameScreen.gruntHandler.gruntSpawner[k].grunts.get(enemy) != null) {
								myGame.gameScreen.gruntHandler.gruntSpawner[k].grunts.get(enemy).setIsDead(true);
							}
						}
					}
				}
				haveKilledEnemies = true;
				screenShouldShake = true;
			}
			handleDeadTimer();
			handleScreenShakeTimer();

			if (screenShouldShake) {
				GameScreen.screenShake.shake(0.3f, 3);
			} 

			handleReEmergeDisplayTimer();
		}
	}

	private void handleReEmergeDisplayTimer() {
		if (displayPawOnScreen) {
			displayPawOnScreenTimer++;
			if (displayPawOnScreenTimer > REEMERGE_DISPLAY_TIME) {
				displayPawOnScreenTimer = 0;
				displayPawOnScreen = false;
			}
		}
	}

	private void handleScreenShakeTimer() {
		if (screenShouldShake) {
			shakeTimer++;
			if (shakeTimer > SCREEN_SHAKE_TIME) {
				shakeTimer        = 0;
				screenShouldShake = false;
			}
		}
	}

	private void handleDeadTimer() {
		deadTimer++;
		if (deadTimer > DEAD_TIME) {
			hasBeenUsed         = false;
			haveKilledEnemies   = false;
			deadTimer           = 0;
			playCollectionSound = true;
			displayPawOnScreen      = true;
			displayPawOnScreenTimer = 0;
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

			// Display the paw on screen when it comes back.
			if (displayPawOnScreen) {
				GameObject player     = myGame.getGameObject(Player.PLAYER_ONE);
				int weaponNameUiWidth = 10;
				batch.draw(
						imageLoader.paw, 
						player.getX(), 
						player.getY() - 3, 
						width, 
						-height
						);
				myGame.renderer.batch.draw(
						myGame.imageLoader.pawUi, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - 6, 
						weaponNameUiWidth, 
						-height
						);
				myGame.renderer.batch.draw(
						myGame.imageLoader.addedToInventory, 
						GameScreen.camera.position.x - myGame.getGameScreen().getViewportWidth() / myGame.getGameScreen().getDenominatorOffset() + 2, 
						(GameScreen.camera.position.y - myGame.getGameScreen().getVerticalHeight() / myGame.getGameScreen().getDenominatorOffset()) + GameScreen.camera.viewportHeight - 4, 
						weaponNameUiWidth, 
						-height
						);
			}
		}
	}
}
