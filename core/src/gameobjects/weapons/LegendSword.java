package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import gameobjects.gamecharacters.enemies.Boss;
import gameobjects.gamecharacters.players.Player;
import gameobjects.gamecharacters.players.PlayerOne;
import handlers.CollisionHandler;
import handlers.enemies.GiantHandler;
import helpers.GamePlayHelper;
import inventory.Inventory;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import maps.MapHandler;
import ui.GameOver;
import ui.Win;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class LegendSword extends Weapon {

	// This variable is basically so it's easier to see which color sword is being created. 
	private int color;

	private int rotationAngle;
	public static boolean playSound;
	private TextureRegion textureRegionFull;
	private TextureRegion textureRegionHalf;

	/**
	 * Constructor.
	 * 
	 * @param int           x
	 * @param int           y
	 * @param TextureRegion textureRegionFull
	 * @param TextureRegion textureReguionHalf
	 */
	public LegendSword(int x, int y, int color, TextureRegion textureRegionFull, TextureRegion textureRegionHalf) {
		super(x, y);
		this.width                = 1;
		this.height               = 1;
		this.rectangle.width      = width;
		this.rectangle.height     = height;
		hasBeenCollected          = false;
		this.color                = color;
		this.rotationAngle        = 0;
		playSound                 = false;
		this.textureRegionFull    = textureRegionFull;
		this.textureRegionHalf    = textureRegionHalf;
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		if (!GameOver.triggerGameOver && !Win.triggerWin) {
			if (GamePlayHelper.gameObjectIsWithinScreenBounds(this)) {
				if (hasBeenCollected || Inventory.allInventoryShouldBeRendered) {
					if (!Player.isInWater || Inventory.allInventoryShouldBeRendered) { 
						batch.draw(
								textureRegionFull, 
								x, 
								y, 
								width / 2, 
								height / 2, 
								width, 
								-height, 
								1, 
								1, 
								rotationAngle
								); 
					} else if (Player.isInWater && Inventory.allInventoryShouldBeRendered) {
						batch.draw(
								textureRegionFull, 
								x, 
								y, 
								width / 2, 
								height / 2, 
								width, 
								-height, 
								1, 
								1, 
								rotationAngle
								); 
					} else if (Player.isInWater && !Inventory.allInventoryShouldBeRendered) {
						float xPos = x;
						float yPos = y - 1;
						switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
						case DIRECTION_UP:
							xPos = xPos + 1;
							break;
						case DIRECTION_DOWN:
							xPos = xPos + 1;
							break;
						}
						batch.draw(
								textureRegionFull, 
								xPos, 
								yPos, 
								width / 2, 
								height / 2, 
								width, 
								-height, 
								1, 
								1, 
								rotationAngle
								); 
					}
				} else {
					batch.draw(
							textureRegionHalf, 
							x, 
							y, 
							width / 2, 
							height / 2, 
							width, 
							-height, 
							1, 
							1, 
							rotationAngle
							); 
					int plantSize = 1;
					batch.draw(imageLoader.plant, x, y + 0.5f, plantSize, -plantSize);
				}
			}
			//renderHitBox(batch, imageLoader);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderHitBox(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.whiteSquare, 
				rectangle.x, 
				rectangle.y, 
				rectangle.width, 
				rectangle.height
				);
	}

	/**
	 * 
	 * @param MyGame     myGamet
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);

		if (!hasBeenCollected) {
			this.rectangle.x = x;
			this.rectangle.y = y - height;
			CollisionHandler.checkIfPlayerHasCollidedWithLegendSword(
					PlayerController.getCurrentPlayer(myGame),
					this
					);
		} else {
			height = 2;
			setRotationAngleDependingOnPlayerDirection();
			updateHitBox();
			checkCollisionWithAllEnemies(myGame);
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void checkCollisionWithAllEnemies(MyGame myGame) {
		myGame.gameScreen.enemyHandler.checkWeaponCollision(myGame, this);
		myGame.gameScreen.gruntHandler.checkWeaponCollision(myGame, this);
		CollisionHandler.checkIfWeaponHasCollidedWithGiant(GiantHandler.giants[0], this);
		CollisionHandler.checkIfWeaponHasCollidedWithGiant(GiantHandler.giants[1], this);
		CollisionHandler.checkIfWeaponHasCollidedWithGiant(GiantHandler.giants[2], this);
		for (int i = 0; i < BossLoader.boss.length; i++) {
			CollisionHandler.checkIfWeaponHasCollidedWithBoss((Boss) BossLoader.boss[i], this);
		}
	}

	private void setRotationAngleDependingOnPlayerDirection() {
		switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
		case DIRECTION_RIGHT:
			setRotationAngle(-90);
			break;
		case DIRECTION_LEFT:
			setRotationAngle(90);
			break;
		case DIRECTION_DOWN:
			setRotationAngle(0);
			break;
		case DIRECTION_UP:
			setRotationAngle(180);
			break;
		}
	}

	/**
	 * Change hit box x, y, width, and height to match the shape of rotated object.
	 */
	private void updateHitBox() {
		float offsetValue = 0.5f;
		switch (PlayerOne.playerDirections.get(PlayerOne.playerDirections.size() - 1)) {
		case DIRECTION_RIGHT:
			this.rectangle.x      = x - height - offsetValue;
			this.rectangle.y      = y + offsetValue + 0.2f;
			this.rectangle.width  = height;
			this.rectangle.height = width / 2;
			break;
		case DIRECTION_LEFT:
			this.rectangle.x      = x + height - offsetValue;
			this.rectangle.y      = y + offsetValue + 0.2f;
			this.rectangle.width  = height;
			this.rectangle.height = width / 2;
			break;
		case DIRECTION_DOWN:
			this.rectangle.x      = x + 0.2f;
			this.rectangle.y      = y - height;
			this.rectangle.width  = width / 2;
			this.rectangle.height = height;
			break;
		case DIRECTION_UP:
			this.rectangle.x      = x + 0.2f;
			this.rectangle.y      = y + height;
			this.rectangle.width  = width / 2;
			this.rectangle.height = height;
			break;
		}
	}

	/**
	 * 
	 * @param int rotationAngle
	 */
	public void setRotationAngle(int rotationAngle) {
		this.rotationAngle = rotationAngle;
	}
}