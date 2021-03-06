package gameobjects.gamecharacters.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import handlers.AnimationHandler;
import handlers.CollisionHandler;
import helpers.GameAttributeHelper;
import loaders.ImageLoader;
import maps.MapHandler;
import physics.Lighting.Explosion;
import screens.GameScreen;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Giant extends Enemy {

	private final float LEFT_BOUNDARY  = GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 43;
	private final float RIGHT_BOUNDARY = GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 62;

	private final float JUMP_TIMER_MAX_VALUE = 50;
	private final float EXECUTE_JUMP_VALUE   = 25;
	private final float PEAK_JUMP_VALUE      = 37.5f;

	private int jumpTimer;

	private float shadowY;

	private final int GIANT_EXPLOSION_SIZE = 5;

	public static boolean playLandingSound = false;

	public static boolean playGiantDeathSound = false;

	private boolean deathSoundHasPlayed = false;

	private Rectangle landingSoundBoundary;

	private boolean screenShouldShake   = true;
	private int shakeTimer              = 0;
	private final int SCREEN_SHAKE_TIME = 20; 

	private int deathTimer                = 0;
	private final int AMOUNT_OF_TIME_DEAD = 200;

	private int explosionTimer = 0;

	public static void resetGame() {
		playLandingSound    = false;
		playGiantDeathSound = false;
	}

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 * @param float width
	 * @param float height
	 * @param int   direction
	 */
	public Giant(float x, float y, float width, float height, int direction) {
		super(x, y, width, height, direction);
		walkDownTexture  = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/grunt/gruntRight.atlas"));
		walkUpTexture    = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/grunt/gruntLeft.atlas"));
		walkRightTexture = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/grunt/gruntRight.atlas"));
		walkLeftTexture  = new TextureAtlas(Gdx.files.internal("artwork/gamecharacters/grunt/gruntLeft.atlas"));

		float animationSpeed = AnimationHandler.ANIMATION_SPEED_ENEMY;
		walkDownAnimation    = new Animation <TextureRegion> (animationSpeed, walkDownTexture.getRegions());
		walkUpAnimation      = new Animation <TextureRegion> (animationSpeed, walkUpTexture.getRegions());
		walkRightAnimation   = new Animation <TextureRegion> (animationSpeed, walkRightTexture.getRegions());
		walkLeftAnimation    = new Animation <TextureRegion> (animationSpeed, walkLeftTexture.getRegions());

		dx = -0.2f;
		if (direction == DIRECTION_RIGHT) {
			dx = -dx;
		}
		dy = 0;

		jumpTimer = 0;

		rectangle.width  = width;
		rectangle.height = height;

		shadowY = y - 1.5f;

		int boundarySize     = 26;
		landingSoundBoundary = new Rectangle(x - 13, y - 13, boundarySize, boundarySize);
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void handleJumping(MyGame myGame) {
		jumpTimer++;
		if (jumpTimer > JUMP_TIMER_MAX_VALUE) {
			jumpTimer = 0;
			// Re-align shadow with giant after jump.
			shadowY          = y - 1.5f;

			if (CollisionHandler.playerIsWithinSoundBoundsOfGiant(myGame.getGameObject(Player.PLAYER_ONE), landingSoundBoundary)) {
				playLandingSound = true;
			}
			screenShouldShake = true;
		}
		handleLandingScreenShakeTimer();

		// Execute jump.
		if (jumpTimer > EXECUTE_JUMP_VALUE) {
			x += dx;
			y += dy;
			screenShouldShake = false;
		}
		// Come up or down.
		if (jumpTimer > PEAK_JUMP_VALUE) {
			dy = 0.1f;
		} else {
			dy = -0.1f;
		}
	}

	private void handleLandingScreenShakeTimer() {
		if (screenShouldShake) {
			shakeTimer++;
			if (shakeTimer > SCREEN_SHAKE_TIME) {
				shakeTimer        = 0;
				screenShouldShake = false;
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
		if (enemiesShouldExecuteAi()) {
			rectangle.x = x;
			rectangle.y = y - height;

			landingSoundBoundary.x = x - 10;
			landingSoundBoundary.y = y - 10;

			if (explosion != null) {
				explosion.setX(x);
				explosion.setY(y);
			}

			handleJumping(myGame);

			// Handle direction change.
			if (x < LEFT_BOUNDARY) {
				dx = -dx;
			} else if (x > RIGHT_BOUNDARY) {
				dx = -dx;
			}

			direction = DIRECTION_RIGHT;
			if (dx < 0) {
				direction = DIRECTION_LEFT;
			}

			GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
			// If player is within bounds and the giant lands a jump, shake the screen.
			if (screenShouldShake && CollisionHandler.playerIsWithinSoundBoundsOfGiant(player, landingSoundBoundary)) {
				GameScreen.screenShake.shake(0.3f, 3);
			} 

			CollisionHandler.checkIfPlayerhasCollidedWithGiant(player, this);
		}
		respawn();

		if (dead) {
			handleDeathExplosion(GIANT_EXPLOSION_SIZE);
		}
	}

	/**
	 * 
	 * @param int explosionSize
	 */
	protected void handleDeathExplosion(int explosionSize) {
		if (dead) {
			if (explosionShouldBeCreated) {
				explosion                = new Explosion(rectangle.x, rectangle.y + height, explosionSize);
				explosionShouldBeCreated = false;
			}
		}
	}

	private void respawn() {
		if (dead) {
			deathTimer++;
			if (deathTimer > AMOUNT_OF_TIME_DEAD) {
				dead           = false;
				deathTimer     = 0;
				timer          = 0;
				explosionTimer = 0;
			}
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		updateElapsedTime();
		if (!dead) {
			renderEnemyShadow(batch, imageLoader, 5, 2, shadowY);
			batch.setColor(Color.BLUE);
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
			batch.setColor(Color.WHITE);
			// Uncomment to debug attackBoundary.
			//batch.draw(imageLoader.whiteSquare, attackBoundary.x, attackBoundary.y, attackBoundary.width, attackBoundary.height);
			// Uncomment to draw enemy hit box.
			//batch.draw(imageLoader.whiteSquare, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		} else {
			if (explosion != null) {
				if (explosionTimer < 1) {
					playGiantDeathSound = true;
				}
				explosionTimer++;
				if (explosionTimer < MAX_DEATH_ANIMATION_VALUE) {
					explosion.renderExplosion(batch, imageLoader);
				}
			}
		}
	}
}
