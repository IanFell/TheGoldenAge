package gameobjects.gamecharacters.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
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

	private float originX;
	private float originY;
	private float angle;



	public static final int WIDTH  = 3;
	public static final int HEIGHT = 5;

	private float attackSpeed = 0.5f;

	private BossHealthUi bossHealthUi;

	private final int BOSS_MAX_HEALTH = 25;
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

	// Used for health meter.  It will get brighter by this percent each time boss is hit.
	public static float percentToChangeAlphaEachHit;

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

		percentToChangeAlphaEachHit = BOSS_DAMAGE_TAKEN_FROM_PLAYER / BOSS_MAX_HEALTH;    

		this.originX = x;
		this.originY = y;
		timer        = 0;
		angle        = 0;
	}

	/**
	 * 
	 * @return BossHealthUi
	 */
	public BossHealthUi getBossHealthUi() {
		return bossHealthUi;
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
			//renderEnemyShadow(batch, imageLoader, width, height / 2, y + height - 1.75f);
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
			batch.draw(texture, x, y, width, -height);
			// Uncomment to draw enemy hit box.
			//batch.draw(imageLoader.whiteSquare, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
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

		BossHealthUi.shouldDisplay = false;

		if (enemiesShouldExecuteAi()) {
			rectangle.x = x;
			rectangle.y = y - height;

			handleAttackTimer();

			GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
			if (!dead) {
				BossHealthUi.shouldDisplay = true;
				checkDeath();
				if (!Player.isInvincible) { 
					handleAttack(player);
				}
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
		if (timer > 100 && timer < 200) {
			spin(player);
		} 
		if (timer > 300 && timer < 400) {
			ram(player);
		}  
		if(timer > 500 && timer < 600) {
			spin(player);
		}
		if (timer > 700 && timer < 715) {
			jumpAttack(player, timer, 1);
		}
		if (timer > 730 && timer < 745) {
			jumpAttack(player, timer, 2);
		}
		if (timer > 760 && timer < 775) {
			jumpAttack(player, timer, 3);
		}
		if (timer > 790 && timer < 805) {
			jumpAttack(player, timer, 4);
		}
		if (timer > 820 && timer < 835) {
			jumpAttack(player, timer, 5);
		}	
		if (timer > 900 && timer < 1000) {
			ram(player);
		}
		if (timer > 1000) {
			timer = 0;
		}
	}

	/**
	 * 
	 * @param GameObject player
	 * @param int        timerStartValue
	 * @param int        attackNumber
	 */
	private void jumpAttack(GameObject player, int timerStartValue, int attackNumber) {
		if (bossIsToTheRightOfPlayer(player)) {
			dx = -0.2f;
		}
		if (bossIsToTheLeftOfPlayer(player)) {
			dx = 0.2f;
		}
		dy = -0.2f;
		if (attackNumber == 1) {
			if (timer > 707) {
				dy = 0.2f;
			}
		}
		else if (attackNumber == 2) {
			if (timer > 737) {
				dy = 0.2f;
			}
		}
		else if (attackNumber == 3) {
			if (timer > 767) {
				dy = 0.2f;
			}
		}
		else if (attackNumber == 4) {
			if (timer > 797) {
				dy = 0.2f;
			}
		}
		else if (attackNumber == 5) {
			if (timer > 827) {
				dy = 0.2f;
			}
		}
		x += dx;
		y += dy;
	}

	/**
	 * 
	 * @param GameObject player
	 */
	private void spin(GameObject player) {
		// Spin boss.
		float angleValue = 0.8f;
		angle += angleValue;
		float radius = 3;
		x = (float) (originX - Math.cos(angle) * radius);
		y = (float) (originY + Math.sin(angle) * radius);

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
	 */
	private void ram(GameObject player) {
		if (bossIsToTheRightOfPlayer(player)) {
			x -= attackSpeed;
		} else if (bossIsToTheLeftOfPlayer(player)) {
			x += attackSpeed;
		}
		if (bossIsToTheTopOfPlayer(player)) {
			y += attackSpeed;
		} else if (bossIsToTheBottomOfPlayer(player)) {
			y -= attackSpeed;
		}
		CollisionHandler.checkIfPlayerCollidedWithBoss(player, this);
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	private boolean bossIsToTheRightOfPlayer(GameObject player) {
		return x > player.getX();
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	private boolean bossIsToTheLeftOfPlayer(GameObject player) {
		return x < player.getX();
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	private boolean bossIsToTheTopOfPlayer(GameObject player) {
		return y < player.getY();
	}

	/**
	 * 
	 * @param GameObject player
	 * @return boolean
	 */
	private boolean bossIsToTheBottomOfPlayer(GameObject player) {
		return y > player.getY();
	}

	private void handleAttackTimer() {
		timer++;
	}
}
