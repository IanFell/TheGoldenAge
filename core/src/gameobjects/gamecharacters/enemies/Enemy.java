package gameobjects.gamecharacters.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import gameobjects.GameObject;
import gameobjects.gamecharacters.GameCharacter;
import gameobjects.gamecharacters.players.Player;
import handlers.AnimationHandler;
import handlers.CollisionHandler;
import handlers.holehandler.HoleHandler;
import helpers.RandomNumberGenerator;
import inventory.Inventory;
import loaders.ImageLoader;
import maps.MapHandler;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import physics.Lighting.Explosion;
import store.Store;
import ui.MapUi;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Enemy extends GameCharacter {

	private final float ENEMY_RADIUS   = 1;
	protected final float BOSS_RADIUS  = 3;

	private final int ATTACK_SPIN = 0;
	private final int ATTACK_RAM  = 1;
	// This will be reset to one of the above.
	private int attackToPerform   = 0;

	private float ramSpeed = 0.5f;

	protected float originX;
	protected float originY;
	protected float spinAngle;
	private boolean setOrigin = true;

	public int direction;

	private Rectangle attackBoundary;

	/**
	 * How long explosion animation plays after enemy has been killed.
	 * This is so it wont loop.
	 */
	public final static int MAX_DEATH_ANIMATION_VALUE = 16;

	public final static int DAMAGE_INFLICTED = -1;

	protected Explosion explosion;
	protected boolean dead;
	private boolean willAttack;
	private float speed;
	private int stoppedValue = 0;

	public static boolean playDeathSound = false;

	private boolean isInWater = false;

	// Death explosion variable.
	protected boolean explosionShouldBeCreated;

	private final int ENEMY_EXPLOSION_SIZE = 2;

	/**
	 * Constructor.
	 * 
	 * @param float   x
	 * @param float   y
	 * @param float   width
	 * @param float   height
	 * @param int     direction
	 */
	public Enemy(float x, float y, float width, float height, int direction) {
		this.x                   = x;
		this.y                   = y;
		this.width               = width;
		this.height              = height;
		this.direction           = direction;
		dx                       = stoppedValue;
		dy                       = stoppedValue;
		speed                    = 0.05f;
		rectangle.width          = width;
		rectangle.height         = height;
		playSound                = false;
		dead                     = false;
		explosionShouldBeCreated = true;
		// The height of the rectangle is to reflect attackBoundary if player is below enemy.
		attackBoundary           = new Rectangle(x, y, 3, 4);

		// Enemy will either attack player, or get within attackBoundary and stop.
		willAttack = false;
		int randomAttackValue = RandomNumberGenerator.generateRandomInteger(2);
		if (randomAttackValue < 1) {
			willAttack = true;
		}

		// Choose attack to perform.
		/*
		if (willAttack) {
			int attack = RandomNumberGenerator.generateRandomInteger(100);
			attackToPerform = ATTACK_SPIN;
			if (attack < 50) {
				attackToPerform = ATTACK_RAM;
			}
		} */

		walkDownTexture  = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/enemy/enemyDown.atlas"));
		walkUpTexture    = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/enemy/enemyUp.atlas"));
		walkRightTexture = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/enemy/enemyRight.atlas"));
		walkLeftTexture  = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/enemy/enemyLeft.atlas"));

		walkDownAnimation    = new Animation <TextureRegion> (AnimationHandler.ANIMATION_SPEED_ENEMY, walkDownTexture.getRegions());
		walkUpAnimation      = new Animation <TextureRegion> (AnimationHandler.ANIMATION_SPEED_ENEMY, walkUpTexture.getRegions());
		walkRightAnimation   = new Animation <TextureRegion> (AnimationHandler.ANIMATION_SPEED_ENEMY, walkRightTexture.getRegions());
		walkLeftAnimation    = new Animation <TextureRegion> (AnimationHandler.ANIMATION_SPEED_ENEMY, walkLeftTexture.getRegions());
	}

	/**
	 * 
	 * @param float speed
	 */
	@Override
	public void moveRight(float speed) {
		setX(getX() + speed);
		setEnemyDirection(DIRECTION_RIGHT);
		setDx(-getDx());
	}

	/**
	 * 
	 * @param float speed
	 */
	@Override
	public void moveLeft(float speed) {
		setX(getX() - speed);
		setEnemyDirection(DIRECTION_LEFT);
		setDx(-getDx());
	}

	/**
	 * 
	 * @param float speed
	 */
	@Override
	public void moveDown(float speed) {
		setY(getY() + speed * 2);
		setEnemyDirection(DIRECTION_DOWN);
		setDy(-getDy());
	}

	/**
	 * 
	 * @param float speed
	 */
	@Override
	public void moveUp(float speed) {
		setY(getY() - speed * 2);
		setEnemyDirection(DIRECTION_UP);
		setDy(-getDy());
	}

	/**
	 * 
	 * @return int
	 */
	public int getTimer() {
		return timer;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * 
	 * @param boolean dead
	 */
	public void setIsDead(boolean dead) {
		this.dead = dead;
	}

	/**
	 * Don't use GameObject.getDirection() because it is associated with the Player direction.  
	 * Currently this is working, so don't change it.
	 * 
	 * @return int
	 */
	public int getEnemyDirection() {
		return direction;
	}

	/**
	 * Don't use GameObject.getDirection() because it is associated with the Player direction.  
	 * Currently this is working, so don't change it.
	 * 
	 * @param int direction
	 */
	public void setEnemyDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param float       width
	 * @param float       height
	 * @param float       shadowY
	 */
	protected void renderEnemyShadow(SpriteBatch batch, ImageLoader imageLoader, float width, float height, float shadowY) {
		batch.draw(imageLoader.shadow, x, shadowY, width, height);
	}

	/**
	 * 
	 * @param boolean
	 */
	public void setInWater(boolean isInWater) {
		this.isInWater = isInWater;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isInWater() {
		return isInWater;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderBoat(SpriteBatch batch, ImageLoader imageLoader) {
		switch (direction) {
		case DIRECTION_LEFT:
			batch.draw(imageLoader.boatSide, x - 2, y + 1.3f, 4, -3);
			break;
		case DIRECTION_RIGHT:
			batch.draw(imageLoader.boatSide, x - 1, y + 1.3f, 4, -3);
			break;
		case DIRECTION_UP:
			batch.draw(imageLoader.boatDown, x - 1, y + 1, 3, -4);
			break;
		case DIRECTION_DOWN:
			batch.draw(imageLoader.boatDown, x - 1, y + 2, 3, -4);
			break;
		}
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		updateElapsedTime();
		if (!dead) {
			renderEnemyShadow(batch, imageLoader, width, height / 2, y - 0.25f);
			if (isInWater) {
				renderBoat(batch, imageLoader);
			}
			AnimationHandler.renderAnimation(
					batch, 
					elapsedTime, 
					getCurrentAnimation(), 
					x, 
					y, 
					width,
					height,
					imageLoader, 
					AnimationHandler.OBJECT_TYPE_ENEMY
					);
			// Uncomment to debug attackBoundary.
			//batch.draw(imageLoader.whiteSquare, attackBoundary.x, attackBoundary.y, attackBoundary.width, attackBoundary.height);
			// Uncomment to draw enemy hit box.
			//batch.draw(imageLoader.whiteSquare, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		} else {
			if (explosion != null) {
				if (timer < MAX_DEATH_ANIMATION_VALUE) {
					explosion.renderExplosion(batch, imageLoader);
				}
			}
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);
		rectangle.x      = x;
		rectangle.y      = y - height;
		// attackBoundary is a few pixels around enemy.
		attackBoundary.x = x - 1;
		attackBoundary.y = y - 2;

		if (enemiesShouldExecuteAi()) {
			executeAI(myGame);
		}

		CollisionHandler.checkIfEnemyHasCollidedWithPlayer(this, (Player) PlayerController.getCurrentPlayer(myGame));

		handleDeathExplosion(ENEMY_EXPLOSION_SIZE);
	}

	/**
	 * Don't execute AI if screen is covered.
	 * 
	 * @return boolean
	 */
	public boolean enemiesShouldExecuteAi() {
		if (
				!dead && 
				!Inventory.allInventoryShouldBeRendered &&
				!Store.shouldDisplayEnterStoreMessage &&
				!MapUi.mapShouldBeRendered &&
				!MissionRawBar.phasesAreInProgress &&
				!MissionStumpHole.missionIsActive &&
				!HoleHandler.playerIsInHole
				) {
			return true;
		}
		return false;
	}

	/**
	 * If enemy is dead, create explosion and start explosion timer.
	 * 
	 * @param int explosionSize
	 */
	protected void handleDeathExplosion(int explosionSize) {
		if (dead) {
			timer++;
			if (explosionShouldBeCreated) {
				explosion                = new Explosion(x, y, explosionSize);
				explosionShouldBeCreated = false;
			}
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void executeAI(MyGame myGame) {
		//pathFind(myGame);
		if (attackBoundary.overlaps(PlayerController.getCurrentPlayer(myGame).rectangle)) {
			if (willAttack) {
				spin(myGame.getGameObject(Player.PLAYER_ONE), ENEMY_RADIUS);
			} 
		} else {
			pathFind(myGame);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 */
	protected void ram(GameObject player) {
		if (bossIsToTheRightOfPlayer(player)) {
			x -= ramSpeed;
		} else if (bossIsToTheLeftOfPlayer(player)) {
			x += ramSpeed;
		}
		if (bossIsToTheTopOfPlayer(player)) {
			y += ramSpeed;
		} else if (bossIsToTheBottomOfPlayer(player)) {
			y -= ramSpeed;
		}
	}

	/**
	 * TODO Right now, the enemy will only attack the player once.  
	 * set "setOrigin" to true somewhere if we want enemy to repeat action.
	 * Right now it seems fair they only attack once since there can be a lot of them at once.
	 * 
	 * @param GameObject player
	 * @param float      radius
	 */
	protected void spin(GameObject player, float radius) {
		if (setOrigin) {
			originX   = x;
			originY   = y;
			setOrigin = false;
		}
		// Spin enemy.
		float angleValue = 0.8f;
		spinAngle += angleValue;
		x = (float) (originX - Math.cos(spinAngle) * radius);
		y = (float) (originY + Math.sin(spinAngle) * radius);

		// Also make him move towards player slowly.
		float movementValue = 0.2f;
		if (bossIsToTheRightOfPlayer(player)) {
			x       -= movementValue;
			originX -= movementValue;
		}
		if (bossIsToTheLeftOfPlayer(player)) {
			x       += movementValue;
			originX += movementValue;
		}
		if (bossIsToTheTopOfPlayer(player)) {
			y       += movementValue;
			originY += movementValue;
		}
		if (bossIsToTheBottomOfPlayer(player)) {
			y       -= movementValue;
			originY -= movementValue;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	protected boolean bossIsToTheRightOfPlayer(GameObject player) {
		return x > player.getX();
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	protected boolean bossIsToTheLeftOfPlayer(GameObject player) {
		return x < player.getX();
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	protected boolean bossIsToTheTopOfPlayer(GameObject player) {
		return y < player.getY();
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	protected boolean bossIsToTheBottomOfPlayer(GameObject player) {
		return y > player.getY();
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void attackPlayer(MyGame myGame) {
		float playerX = PlayerController.getCurrentPlayer(myGame).getX();
		float playerY = PlayerController.getCurrentPlayer(myGame).getY();
		if (willAttack) {
			if (x < playerX) {
				dx = speed;
			} else if (x > playerX) {
				dx = -speed;
			}
			if (y < playerY) {
				dy = speed;
			} else if (y > playerY) {
				dy = -speed;
			}
		}
	}

	/**
	 * This method just deals with direction.
	 * Collisions with tiles are located in the MapRenderer class.
	 * 
	 * @param MyGame myGame
	 */
	private void pathFind(MyGame myGame) {
		x += dx;
		y += dy;
		int changeDirection = RandomNumberGenerator.generateRandomInteger(100);
		if (changeDirection > 90) {
			changeDirection(myGame);
		}
	}

	/**
	 * This behaves differently than the moveUp(), moveDown(), moveLeft(), and moveRight() methods.
	 * Do not use those methods to move enemy within this method.
	 * 
	 * @param MyGame myGame
	 */
	private void changeDirection(MyGame myGame) {
		if (direction == DIRECTION_LEFT || direction == DIRECTION_RIGHT) {
			if (y < PlayerController.getCurrentPlayer(myGame).getY()) {
				dx        = stoppedValue;
				dy        = speed;
				direction = DIRECTION_DOWN;
			} else {
				dx        = stoppedValue;
				dy        = -speed;
				direction = DIRECTION_UP;
			}
		} else {
			if (x < PlayerController.getCurrentPlayer(myGame).getX()) {
				dx        = speed;
				dy        = stoppedValue;
				direction = DIRECTION_RIGHT;
			} else {
				dx        = -speed;
				dy        = stoppedValue;
				direction = DIRECTION_LEFT;
			}
		}
	}
}
