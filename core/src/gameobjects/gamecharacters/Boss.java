package gameobjects.gamecharacters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import handlers.CollisionHandler;
import helpers.RandomNumberGenerator;
import loaders.ImageLoader;
import maps.MapHandler;
import physics.Lighting.Explosion;
import ui.BossHealthUi;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Boss extends Enemy {

	public static final int WIDTH  = 2;
	public static final int HEIGHT = 5;

	private float maxDistanceFromPlayer = 5.0f;

	private boolean isAttacking           = false;
	private final int NUMBER_OF_ATTACKS   = 100;
	public static int currentAttackNumber = 0;

	private float attackSpeed = 0.5f;

	private BossHealthUi bossHealthUi;

	private final int BOSS_MAX_HEALTH = 5;
	private float bossHealth;

	private final float BOSS_DAMAGE_TAKEN_FROM_PLAYER = .05f;

	private boolean explosionsShouldBeRendered = false;
	private boolean explosionsShouldBeCreated  = false;
	private Explosion[] explosion              = new Explosion[5];
	private int explosionSize                  = 5;
	// Use this so explosions don't fire all at once.
	private int explosionOffsetTimer           = 0;

	// Use this to time the rendering of explosions.
	private int[] explosionFinishTimer   = new int[5];
	private int[] explosionStartValue    = new int[5];
	private final int EXPLOSION_MAX_TIME = 100;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 * @param float width
	 * @param float height
	 * @param int   direction
	 */
	public Boss(float x, float y, float width, float height, int direction) {
		super(x, y, width, height, direction);
		rectangle.width  = width;
		rectangle.height = height;
		bossHealthUi     = new BossHealthUi(x, y - 2);
		bossHealth       = BOSS_MAX_HEALTH;
		for (int i = 0; i < explosionFinishTimer.length; i++) {
			explosionFinishTimer[i] = 0;
		}
		int startValue = 0;
		for (int i = 0; i < explosionStartValue.length; i++) {
			explosionStartValue[i] = startValue;
			startValue += 10;
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.whiteSquare, x, y, width, height);
		bossHealthUi.renderBossHealthUi(batch, imageLoader, this);
		renderExplosions(batch, imageLoader);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderExplosions(SpriteBatch batch, ImageLoader imageLoader) {
		if (explosionsShouldBeRendered) {
			if (explosion[0] != null) {
				explosionFinishTimer[0]++;
				if (explosionOffsetTimer > explosionStartValue[0] && explosionFinishTimer[0] < EXPLOSION_MAX_TIME) {
					explosion[0].renderExplosion(batch, imageLoader);
				}
			}
			if (explosion[1] != null) {
				explosionFinishTimer[1]++;
				if (explosionOffsetTimer > explosionStartValue[1] && explosionFinishTimer[1] < EXPLOSION_MAX_TIME) {
					explosion[1].renderExplosion(batch, imageLoader);
				}
			}
			if (explosion[2] != null) {
				explosionFinishTimer[2]++;
				if (explosionOffsetTimer > explosionStartValue[2] && explosionFinishTimer[2] < EXPLOSION_MAX_TIME) {
					explosion[2].renderExplosion(batch, imageLoader);
				}
			}
			if (explosion[3] != null) {
				explosionFinishTimer[3]++;
				if (explosionOffsetTimer > explosionStartValue[3] && explosionFinishTimer[3] < EXPLOSION_MAX_TIME) {
					explosion[3].renderExplosion(batch, imageLoader);
				}
			}
			if (explosion[4] != null) {
				explosionFinishTimer[4]++;
				if (explosionOffsetTimer > explosionStartValue[4] && explosionFinishTimer[3] < EXPLOSION_MAX_TIME) {
					explosion[4].renderExplosion(batch, imageLoader);
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
		rectangle.x = x;
		rectangle.y = y;

		handleAttackTimer();

		GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
		if (isAttacking && currentAttackNumber < NUMBER_OF_ATTACKS) {
			handleAttack(player);
		} else {
			x = player.getX() + maxDistanceFromPlayer;
		}
		y = player.getY() - height / 2;

		bossHealth -= BOSS_DAMAGE_TAKEN_FROM_PLAYER;
		bossHealthUi.updateBossHealthUi(this);

		if (!dead) {
			checkDeath();
		}

		if (explosionsShouldBeCreated) {
			for (int i = 0; i < explosion.length; i++) {
				float xPos = (float) RandomNumberGenerator.generateRandomDouble(x - width, x + width);
				float yPos = (float) RandomNumberGenerator.generateRandomDouble(y, y + height);
				explosion[i] = new Explosion(xPos, yPos, explosionSize);
			}
			explosionsShouldBeCreated = false;
		}

		if (explosionsShouldBeRendered) {
			explosionOffsetTimer++;
			for (int i = 0; i < explosion.length; i++) {
				explosion[i].updateObject(myGame, mapHandler);
			}
		}
		checkForTheConclusionOfBossBattle();
	}

	/**
	 * If the last explosion has finished, boss battle is over.
	 */
	private void checkForTheConclusionOfBossBattle() {
		if (explosionFinishTimer[4] >= EXPLOSION_MAX_TIME) {
			//System.exit(0);
		}
	}

	private void checkDeath() {
		if (bossHealth < 0) {
			explosionsShouldBeCreated  = true;
			explosionsShouldBeRendered = true;
			dead                       = true;
		}
	}

	/**
	 * 
	 * @return float
	 */
	public float getBossHealth() {
		return bossHealth;
	}

	/**
	 * 
	 * @param GameObject player
	 */
	private void handleAttack(GameObject player) {
		if (bossIsToTheRightOfPlayer(player)) {
			if (x > player.getX()) {
				x -= attackSpeed;
			}
		} else if (bossIsToTheLeftOfPlayer(player)) {
			if (x < player.getX()) {
				x += attackSpeed;
			}
		}
		CollisionHandler.checkIfPlayerCollidedWithBoss(player, this);
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	private boolean bossIsToTheRightOfPlayer(GameObject player) {
		if (x > player.getX()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	private boolean bossIsToTheLeftOfPlayer(GameObject player) {
		if (x < player.getX()) {
			return true;
		}
		return false;
	}

	private void handleAttackTimer() {
		if (!isAttacking) {
			timer++;
			if (timer > 100) {
				isAttacking = true;
			}
		} else {
			timer = 0;
		}
	}

	/**
	 * 
	 * @param boolean isAttacking
	 */
	public void setAttacking(boolean isAttacking) {
		this.isAttacking = isAttacking;
	}
}
