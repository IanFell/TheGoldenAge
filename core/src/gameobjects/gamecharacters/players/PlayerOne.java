package gameobjects.gamecharacters.players;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.collectibles.Torch;
import handlers.AnimationHandler;
import handlers.collectibles.RumHandler;
import helpers.RandomNumberGenerator;
import inventory.Inventory;
import loaders.ImageLoader;
import maps.MapHandler;
import physics.Lighting.Explosion;
import physics.Lighting.Fire;
import ui.GameOver;

/**
 * Jolly Roger.
 * 
 * @author Fabulous Fellini
 *
 */
public class PlayerOne extends Player {

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

	public static boolean isPoisoned = false;
	private int poisonTimer          = 0;

	private final int MAX_LIVES       = 3;
	public static int lives           = 0;

	private int oarTimer = 0;

	private float animationSpeed;

	private final float BOUNCE_BACK_STARTING_VALUE = 0.7f;
	private float bounceBackIncrement              = BOUNCE_BACK_STARTING_VALUE;
	private boolean isBouncingBack                 = false;

	private Torch torch;

	/**
	 * Keeps a list of player one's coordinates and direction.  
	 * This is used to determine the paths of player two and player three.
	 */
	public static ArrayList<Float> playerOneXPositions = new ArrayList<Float>();
	public static ArrayList<Float> playerOneYPositions = new ArrayList<Float>();
	public static ArrayList<Integer> playerDirections  = new ArrayList<Integer>();
	
	public static boolean playDeathSound = false;

	/**
	 * Constructor.
	 * 
	 * @param String name
	 * @param MyGame myGame
	 * @param int    playerNumber
	 */
	public PlayerOne(String name, MyGame myGame, int playerNumber) {
		super(name, myGame, playerNumber);
		playerLoot = 0;
		torch      = new Torch(0, 0);

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
	 * @param int loot
	 */
	@Override
	public void updatePlayerLoot(int loot) {
		playerLoot += loot;
	}

	/**
	 * Convert the current amount of player loot into a string to display on screen.
	 * 
	 * @return String
	 */
	@Override
	public String convertPlayerLootToString() {
		return Integer.toString(playerLoot);
	}

	/**
	 * Convert the current amount of player rum into a string to display on screen.
	 * 
	 * @return String
	 */
	@Override
	public String convertPlayerRumToString() {
		return Integer.toString(RumHandler.rumCount);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);
		handleWalking(myGame);
		handleJumping(myGame);
		handleBounceBack();

		if (getHealth() <= 0) {
			explosionsShouldBeRendered = true;
			explosionsShouldBeCreated  = true;
			playDeathSound             = true;
		}

		if (getHealth() <= 0 && lives < 3) {
			//setLifeState(myGame, PLAYER_ONE);
			resetHealthForNewLife();
			lives++;
		}

		if (lives == MAX_LIVES) {
			// Game over is actually reset in MyGame.
			GameOver.triggerGameOver = true;
		}

		if (hasTorch) {	
			torch.updateObject(myGame, mapHandler);
			Fire.playSound = true;
		}

		handlePoison();

		//simulateDeath(myGame, this);

		if (Inventory.inventoryHasStartedCollection) {
			inventory.updateInventory(x, y, mapHandler);
		}

		handleTextures();
		setPlayerAnimations();

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
				if (explosion[i] != null) {
					explosion[i].updateObject(myGame, mapHandler);
				}
			}
		}
	}

	private void handlePoison() {
		handlePoisonTimer();
		handlePoisonHealth();
	}

	private void handlePoisonHealth() {
		if (isPoisoned) {
			if (poisonTimer == 50 || poisonTimer == 100 || poisonTimer == 150 || poisonTimer == 200) {
				health--;
			}
		}
	}

	private void handlePoisonTimer() {
		if (isPoisoned) {
			poisonTimer++;
			if (poisonTimer > 200) {
				poisonTimer = 0;
				isPoisoned  = false;
			}
		}
	}

	/**
	 * Handles player animations, depending on the life state.
	 * Each life state will draw a different player.
	 */
	private void setPlayerAnimations() {
		if (lifeState == LIFE_STATE_TWO) {
			setAnimations(
					new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerTwoRight.atlas")),
					new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerTwoLeft.atlas")),
					new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerTwoUp.atlas")),
					new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerTwo.atlas"))	
					);
		} 
		if (lifeState == LIFE_STATE_THREE) {
			setAnimations(
					new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerThreeRight.atlas")),
					new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerThreeLeft.atlas")),
					new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerThreeUp.atlas")),
					new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerThreeDown.atlas"))	
					);
		} 
	}

	/**
	 * This handles bouncing back of player after he collides and attacks and enemy.
	 * 
	 * If player bounces back offscreen he will never stop because he never hits the ground.
	 */
	private void handleBounceBack() {
		if (isBouncingBack) {
			switch (direction) {
			case DIRECTION_LEFT:
				x += bounceBackIncrement;
				break;
			case DIRECTION_RIGHT:
				x -= bounceBackIncrement;
				break;
			case DIRECTION_UP:
				y += bounceBackIncrement;
				break;
			case DIRECTION_DOWN:
				y -= bounceBackIncrement;
				break;
			}
		} else {
			bounceBackIncrement = BOUNCE_BACK_STARTING_VALUE;
			isBouncingBack = false;
		}

		if (jumpingAction == ON_GROUND) {
			isBouncingBack = false;
		}
	}

	/**
	 * 
	 * @param boolean isBouncingBack
	 */
	public void setBouncingBack(boolean isBouncingBack) {
		this.isBouncingBack = isBouncingBack;
	}

	private void handleTextures() {
		if (Inventory.inventoryIsEquipped) {
			// Make player spin if jumping.
			if (jumpingAction == Player.DESCENDING_JUMP) {
				animationSpeed = AnimationHandler.ANIMATION_SPEED_PLAYER_DESCEND_JUMP;
				setAnimations(
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerJumpRight.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerJumpLeft.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerJumpUp.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerJumpDown.atlas"))
						);
			} else {
				animationSpeed = AnimationHandler.ANIMATION_SPEED_PLAYER;
				setAnimations(
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerRightGun.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerLeftGun.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerUpGun.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerDownGun.atlas"))
						);
			}
		} else {
			// Make player spin if jumping.
			if (jumpingAction == Player.DESCENDING_JUMP) {
				animationSpeed = AnimationHandler.ANIMATION_SPEED_PLAYER_DESCEND_JUMP;
				setAnimations(
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerJumpRight.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerJumpLeft.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerJumpUp.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerJumpDown.atlas"))
						);
			} else {
				animationSpeed = AnimationHandler.ANIMATION_SPEED_PLAYER;
				setAnimations(
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerRightRed.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerLeftRed.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerUpRed.atlas")),
						new TextureAtlas(Gdx.files.internal(playerRenderingPrefix + "playerDownRed.atlas"))
						);
			}
		}
	}

	/**
	 * 
	 * @param TextureAtlas walkRight
	 * @param TextureAtlas walkLeft
	 * @param TextureAtlas walkUp
	 * @param TextureAtlas walkDown
	 */
	private void setAnimations(TextureAtlas walkRight, TextureAtlas walkLeft, TextureAtlas walkUp, TextureAtlas walkDown) {
		walkRightTexture   = walkRight;
		walkLeftTexture    = walkLeft;
		walkDownTexture    = walkDown;
		walkUpTexture      = walkUp;
		walkRightAnimation = new Animation <TextureRegion> (animationSpeed, walkRightTexture.getRegions());
		walkLeftAnimation  = new Animation <TextureRegion> (animationSpeed, walkLeftTexture.getRegions());
		walkDownAnimation  = new Animation <TextureRegion> (animationSpeed, walkDownTexture.getRegions());
		walkUpAnimation    = new Animation <TextureRegion> (animationSpeed, walkUpTexture.getRegions());
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		super.renderObject(batch, imageLoader);

		renderBoat(batch, imageLoader);

		if (!Player.isInvincible) {
			AnimationHandler.renderAnimation(
					batch, 
					elapsedTime, 
					getCurrentAnimation(), 
					x, 
					y, 
					width,
					height,
					imageLoader, 
					AnimationHandler.OBJECT_TYPE_PLAYER
					);
		} else {
			if (Player.invincibilityTimer % 2 == 0) {
				AnimationHandler.renderAnimation(
						batch, 
						elapsedTime, 
						getCurrentAnimation(), 
						x, 
						y, 
						width,
						height,
						imageLoader, 
						AnimationHandler.OBJECT_TYPE_PLAYER
						);
			}
		}

		if (hasTorch) {	
			torch.renderObject(batch, imageLoader);
		}

		renderOar(batch, imageLoader);

		if (isPoisoned) {
			batch.draw(imageLoader.poisonCover, x, y, width, -height);
		}
		//renderHitBox(batch, imageLoader);

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
				if (explosionOffsetTimer > explosionStartValue[4] && explosionFinishTimer[4] < EXPLOSION_MAX_TIME) {
					explosion[4].renderExplosion(batch, imageLoader);
				} 
			}
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderOar(SpriteBatch batch, ImageLoader imageLoader) {
		Texture texture = imageLoader.oarDown[1];
		if (isInWater && Player.playerIsMoving) {
			oarTimer++;
			switch (direction) {
			case DIRECTION_LEFT:
				if(oarTimer <= 10) {
					texture = imageLoader.oarLeft[0];
				}
				else if (oarTimer > 10 && oarTimer < 20) {
					texture = imageLoader.oarLeft[1];
				}
				else if (oarTimer >= 20 && oarTimer < 30) {
					texture = imageLoader.oarLeft[2];
				}
				else if (oarTimer >= 30 && oarTimer < 40) {
					texture = imageLoader.oarLeft[3];
				}
				else if (oarTimer >= 40 && oarTimer < 50) {
					texture = imageLoader.oarLeft[4];
				}
				else if (oarTimer >= 50 && oarTimer < 60) {
					texture = imageLoader.oarLeft[5];
				}
				else {
					oarTimer = 0;
				}
				break;
			case DIRECTION_RIGHT:
				if(oarTimer <= 10) {
					texture = imageLoader.oarRight[0];
				}
				else if (oarTimer > 10 && oarTimer < 20) {
					texture = imageLoader.oarRight[1];
				}
				else if (oarTimer >= 20 && oarTimer < 30) {
					texture = imageLoader.oarRight[2];
				}
				else if (oarTimer >= 30 && oarTimer < 40) {
					texture = imageLoader.oarRight[3];
				}
				else if (oarTimer >= 40 && oarTimer < 50) {
					texture = imageLoader.oarRight[4];
				}
				else if (oarTimer >= 50 && oarTimer < 60) {
					texture = imageLoader.oarRight[5];
				}
				else {
					oarTimer = 0;
				}
				break;
			case DIRECTION_UP:
				if(oarTimer <= 10) {
					texture = imageLoader.oarUp[0];
				}
				else if (oarTimer > 10 && oarTimer < 20) {
					texture = imageLoader.oarUp[1];
				}
				else if (oarTimer >= 20 && oarTimer < 30) {
					texture = imageLoader.oarUp[2];
				}
				else {
					oarTimer = 0;
				}
				break;
			case DIRECTION_DOWN:
				if(oarTimer <= 10) {
					texture = imageLoader.oarDown[0];
				}
				else if (oarTimer > 10 && oarTimer < 20) {
					texture = imageLoader.oarDown[1];
				}
				else if (oarTimer >= 20 && oarTimer < 30) {
					texture = imageLoader.oarDown[2];
				}
				else {
					oarTimer = 0;
				}
				break;
			}
			batch.draw(texture, x - 1.3f, y + 1.75f, 4, -4);
		} else if (Player.isInWater && !Player.playerIsMoving) {
			switch (direction) {
			case DIRECTION_LEFT:
				texture = imageLoader.oarLeft[2];
				break;
			case DIRECTION_RIGHT:
				texture = imageLoader.oarRight[2];
				break;
			case DIRECTION_UP:
				texture = imageLoader.oarUp[1];
				break;
			case DIRECTION_DOWN:
				texture = imageLoader.oarDown[1];
				break;
			}
			batch.draw(texture, x - 1.3f, y + 1.75f, 4, -4);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderBoat(SpriteBatch batch, ImageLoader imageLoader) {
		if (isInWater) {
			if (isInvincible) {
				switch (direction) {
				case DIRECTION_LEFT:
					batch.draw(imageLoader.boatSide, x - 2, y + 1.3f, 4, -3);
					break;
				case DIRECTION_RIGHT:
					batch.draw(imageLoader.boatSide, x, y + 1.3f, 4, -3);
					break;
				case DIRECTION_UP:
					batch.draw(imageLoader.boatDown, x - 0.5f, y + 1, 3, -4);
					break;
				case DIRECTION_DOWN:
					batch.draw(imageLoader.boatDown, x - 0.5f, y + 2, 3, -4);
					break;
				}
			} else {
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
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param GameObject player
	 */
	@Override
	protected void simulateDeath(MyGame myGame, GameObject player) {
		health--;
	}

	/**
	 * Only save player coordiantes if player is moving.  
	 * This is so player two and three stop and move with player one.
	 * 
	 * @param MyGame myGame
	 */
	@Override
	protected void handleWalking(MyGame myGame) {
		savePlayerCurrentPositionAndDirection(x, y, playerOneXPositions, playerOneYPositions, playerDirections);
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	@Override
	protected void handleJumping(MyGame myGame) {
		super.handleJumping(myGame);
		if (jumpingAction == Player.DESCENDING_JUMP) {
			switch (direction) {
			case DIRECTION_LEFT:
				dx = -0.3f;
				dy = 0.01f;
				break;
			case DIRECTION_RIGHT:
				dx = 0.3f;
				dy = 0.01f;
				break;
			case DIRECTION_UP:
				dx = 0;
				dy = -0.3f;
				break;
			case DIRECTION_DOWN:
				dx = 0;
				dy = 0.3f;
				break;
			}
			x += dx;
			y += dy;
		} else {
			dx = 0;
			dy = 0;
		}
	}
}
