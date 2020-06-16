package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import handlers.CollisionHandler;
import handlers.enemies.GiantHandler;
import helpers.GamePlayHelper;
import inventory.Inventory;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import maps.MapHandler;
import missions.MissionRawBar;
import physics.Lighting.Fire;
import ui.MapUi;

/**
 * This weapon acts like a boomerang.
 * 
 * @author Fabulous Fellini
 *
 */
public class MagicPearl extends Weapon {

	public static boolean isAttacking;
	public static boolean isMovingForward;

	// This hitbox is bigger than the object to ensure player catches it upon return.
	private Rectangle collisionWithPlayerUponReturnHitbox;

	public static boolean playCollectionSound = false;

	//private Fire fire;

	private float pearlStartThrowXPosition      = 0;
	private boolean setPearlThrowXPosition      = true;
	private boolean pearlHasReachedPeakDistance = false;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public MagicPearl(float x, float y) {
		super(x, y);
		float size                          = 0.75f;;
		this.width                          = size;
		this.height                         = size;
		collisionWithPlayerUponReturnHitbox = new Rectangle(0, 0, width + 2, height + 2);
		rectangle.width                     = width;
		rectangle.height                    = height;
		hasBeenCollected                    = false;
		isAttacking                         = false;
		isMovingForward                     = false;
		dx                                  = 0;
		dy                                  = 0;
		//fire                                = new Fire(0, 0, 1, 1, null, false);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		if (MissionRawBar.rawBarMissionComplete) {
			GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
			collisionWithPlayerUponReturnHitbox.x = x - 1;
			collisionWithPlayerUponReturnHitbox.y = y - 1;
			rectangle.x                           = x;
			rectangle.y                           = y;

			if (!hasBeenCollected) {
				//CollisionHandler.checkIfPlayerHasCollidedWithMagicPearl(player, this);
			} else {
				// Player has just thrown pearl.
				if (isAttacking && isMovingForward) {
					myGame.gameScreen.enemyHandler.checkProjectileCollision(myGame, this);
					myGame.gameScreen.gruntHandler.checkProjectileCollision(myGame, this);
					for (int i = 0; i < GiantHandler.giants.length; i++) {
						CollisionHandler.checkIfProjectileHasCollidedWithEnemy(GiantHandler.giants[i], this);
					}
					for (int i = 0; i < BossLoader.boss.length; i++) {
						CollisionHandler.checkIfProjectileHasCollidedWithBoss(BossLoader.boss[i], this);
					}

					//if (setPearlThrowXPosition) {
					//	pearlStartThrowXPosition = x;
					//	setPearlThrowXPosition = false;
					//}
					//if (!pearlHasReachedPeakDistance) {

					//}
					switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
					case DIRECTION_RIGHT:
						dx = 1;
						dy = 0;
						x += dx;
						y += dy;
						break;
					case DIRECTION_LEFT:
						dx = 1;
						dy = 0;
						x -= dx;
						y += dy;
						break;
					case DIRECTION_DOWN:
						dx = 0;
						dy = 1;
						x += dx;
						y += dy;
						break;
					case DIRECTION_UP:
						dx = 0;
						dy = 1;
						x += dx;
						y -= dy;
						break;
					}	
					//fire.setX(x);
					//fire.setY(y);
					//fire.updateObject(myGame, mapHandler);
				} else {
					isAttacking = false;
					switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
					case Player.DIRECTION_RIGHT:
						x = player.getX() + 1;
						y = player.getY();
						break;
					case Player.DIRECTION_LEFT:
						x = player.getX() - 1;
						y = player.getY();
						break;
					case Player.DIRECTION_UP:
						x = player.getX();
						y = player.getY() - 1;
						break;
					case Player.DIRECTION_DOWN:
						x = player.getX();
						y = player.getY();
						break;
					}
				}
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
		if (/*MissionRawBar.rawBarMissionComplete &&*/ GamePlayHelper.gameObjectIsWithinScreenBounds(this)) {
			if (!hasBeenCollected && !MapUi.mapShouldBeRendered) {
				/*batch.draw(
						imageLoader.oyster, 
						x, 
						y, 
						width, 
						-height
						);*/
			} else if ((myGame.getGameObject(Player.PLAYER_ONE).getInventory().inventory.get(Inventory.currentlySelectedInventoryObject) == this && Inventory.inventoryIsEquipped) || Inventory.allInventoryShouldBeRendered) {
				batch.draw(
						imageLoader.magicPearl, 
						x, 
						y, 
						width, 
						-height
						);
			}

			//if (isAttacking) {
			//	fire.renderObject(batch, imageLoader);
			//}
		}
	}
}
