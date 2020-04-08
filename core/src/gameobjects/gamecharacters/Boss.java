package gameobjects.gamecharacters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import handlers.CollisionHandler;
import loaders.ImageLoader;
import maps.MapHandler;
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
		
		bossHealth -= .01;
		bossHealthUi.updateBossHealthUi(this);
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
