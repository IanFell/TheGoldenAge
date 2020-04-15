package gameobjects.gamecharacters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import handlers.AnimationHandler;
import handlers.CollisionHandler;
import helpers.RandomNumberGenerator;
import loaders.ImageLoader;
import maps.MapHandler;
import physics.Lighting.Explosion;
import screens.GameScreen;
import ui.BossHealthUi;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Boss extends Enemy {

	public static final int WIDTH  = 3;
	public static final int HEIGHT = 5;

	private float maxDistanceFromPlayer = 5.0f;

	private boolean isAttacking           = false;
	private final int NUMBER_OF_ATTACKS   = 100;
	public static int currentAttackNumber = 0;

	private float attackSpeed = 0.5f;

	private BossHealthUi bossHealthUi;

	private final int BOSS_MAX_HEALTH = 7;
	private float bossHealth;

	public final static float BOSS_DAMAGE_TAKEN_FROM_PLAYER = 1f; // 0.08f

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

	// Use this for the rumble during explosion after boss dies.
	public static boolean shouldPlayExplosionMusic = false;

	private boolean battleMusicHasStarted = false;

	// When boss gets hit, play the grunt sound.
	public static boolean playGruntSound = false;
	
	private int animationTimer;
	
	private final int ANIMATION_OVER_VALUE = 9;

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
		rectangle.width       = width;
		rectangle.height      = height;
		bossHealthUi          = new BossHealthUi(x, y - 2);
		bossHealth            = BOSS_MAX_HEALTH;
		battleMusicHasStarted = false;
		for (int i = 0; i < explosionFinishTimer.length; i++) {
			explosionFinishTimer[i] = 0;
		}
		int startValue = 0;
		for (int i = 0; i < explosionStartValue.length; i++) {
			explosionStartValue[i] = startValue;
			startValue += 10;
		}
		/*
		walkDownTexture  = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/grunt/gruntRight.atlas"));
		walkUpTexture    = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/grunt/gruntLeft.atlas"));
		walkRightTexture = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/grunt/gruntRight.atlas"));
		walkLeftTexture  = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/grunt/gruntLeft.atlas"));

		float animationSpeed = 7/15f;
		walkDownAnimation    = new Animation <TextureRegion> (animationSpeed, walkDownTexture.getRegions());
		walkUpAnimation      = new Animation <TextureRegion> (animationSpeed, walkUpTexture.getRegions());
		walkRightAnimation   = new Animation <TextureRegion> (animationSpeed, walkRightTexture.getRegions());
		walkLeftAnimation    = new Animation <TextureRegion> (animationSpeed, walkLeftTexture.getRegions());
		*/
		animationTimer = 0;
	}

	/**
	 * 
	 * @param boolean battleMusicHasStarted
	 */
	public void setBattleMusicHasStarted(boolean battleMusicHasStarted) {
		this.battleMusicHasStarted = battleMusicHasStarted;
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isBattleMusicHasStarted() {
		return battleMusicHasStarted;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		if (!dead) {
			// We need to do this instead of using super() because we need to render the boss lower so it works.
			updateElapsedTime();
			renderEnemyShadow(batch, imageLoader, width, height / 2, y + height - 1.75f);
			/*
			AnimationHandler.renderAnimation(
					batch, 
					elapsedTime, 
					getCurrentAnimation(), 
					x, 
					y + height, 
					width,
					height,
					imageLoader, 
					AnimationHandler.OBJECT_TYPE_ENEMY
					);*/
			Texture texture = imageLoader.bossLeft01;
			if (animationTimer > ANIMATION_OVER_VALUE / 2) {
				// Switch to second image for animation.
				texture = imageLoader.bossLeft02;
			}
			batch.draw(texture, x, y + height, width, -height);
			// Uncomment to draw enemy hit box.
			//batch.draw(imageLoader.whiteSquare, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
			bossHealthUi.renderBossHealthUi(batch, imageLoader, this);
		} else {
			renderExplosions(batch, imageLoader);
		}
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
				if (explosionOffsetTimer > explosionStartValue[4] && explosionFinishTimer[4] < EXPLOSION_MAX_TIME) {
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
		if (!dead) {
			if (isAttacking && currentAttackNumber < NUMBER_OF_ATTACKS) {
				handleAttack(player);
			} else {
				x = player.getX() + maxDistanceFromPlayer;
			}
			y = player.getY() - height / 2;
		} 

		bossHealthUi.updateBossHealthUi(this);

		if (!dead) {
			checkDeath();
		}

		if (explosionsShouldBeCreated) {
			for (int i = 0; i < explosion.length; i++) {
				float xPos   = (float) RandomNumberGenerator.generateRandomDouble(x - width, x + width);
				float yPos   = (float) RandomNumberGenerator.generateRandomDouble(y, y + height);
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

		// Shake screen during explosion.
		if (shouldPlayExplosionMusic) {
			GameScreen.screenShake.shake(0.3f,  3);
		} 
		
		handleAnimationTimer();
	}
	
	private void handleAnimationTimer() {
		animationTimer++;
		if (animationTimer > ANIMATION_OVER_VALUE) {
			animationTimer = 0;
		}
	}

	private void checkDeath() {
		if (bossHealth < 0) {
			explosionsShouldBeCreated  = true;
			explosionsShouldBeRendered = true;
			dead                       = true;
			shouldPlayExplosionMusic   = true;
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
	 * @param float bossHealth
	 */
	public void setBossHealth(float bossHealth) {
		this.bossHealth = bossHealth;
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
