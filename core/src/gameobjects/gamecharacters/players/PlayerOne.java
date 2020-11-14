package gameobjects.gamecharacters.players;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.collectibles.Torch;
import gameobjects.collectibles.TreasureMap;
import gameobjects.weapons.BirdWeapon;
import handlers.AnimationHandler;
import handlers.collectibles.RumHandler;
import handlers.enemies.BossHandler;
import helpers.RandomNumberGenerator;
import inventory.Inventory;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import loaders.marketloader.MarketLoader;
import loaders.rawbarloader.RawBarLoader;
import loaders.tradingpostloader.TradingPostLoader;
import maps.MapHandler;
import missions.MissionChests;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import missions.MissionThePoint;
import missions.MissionTradinPost;
import missions.MissionWewa;
import physics.Lighting.Explosion;
import physics.Lighting.Fire;
import screens.GameScreen;
import store.Store;
import ui.DeathUi;
import ui.GameOver;
import ui.MapUi;
import ui.collectibles.HealthUi;

/**
 * Jolly Roger.
 * 
 * The explosions are sloppy shit code but it works and I had to do it fast.
 * 
 * @author Fabulous Fellini
 *
 */
public class PlayerOne extends Player {
	
	private int currentMission = 10;

	private int locationMarkerIconFlashTimer                = 0;
	private final int LOCATION_MARKER_FLASH_TIMER_INCREMENT = 60;

	private int MISSION_GO_TO_TRADING_POST = 0;
	private int MISSION_GO_TO_RAW_BAR      = 1;
	private int MISSION_GO_TO_STUMP_HOLE   = 2;
	private int MISSION_GO_TO_WEWA         = 3;
	private int MISSION_THE_POINT          = 4;
	private int MISSION_COLLECT_BIRD       = 5;

	private MissionWewa missionWewa;

	// Explosion one.
	private boolean explosionsOneShouldBeRendered = false;
	private boolean explosionsOneShouldBeCreated  = false;
	private Explosion[] explosion              = new Explosion[5];
	private int explosionSize                  = 5;
	// Use this so explosions don't fire all at once.
	private int explosionOffsetTimer           = 0;

	// Use this to time the rendering of explosions.
	private int[] explosionFinishTimer   = new int[5];
	private int[] explosionStartValue    = new int[5];
	private final int EXPLOSION_MAX_TIME = 100;

	// Explosion two.
	private boolean explosionsTwoShouldBeRendered = false;
	private boolean explosionsTwoShouldBeCreated  = false;
	private Explosion[] explosionTwo              = new Explosion[5];
	// Use this so explosions don't fire all at once.
	private int explosionTwoOffsetTimer           = 0;

	// Use this to time the rendering of explosions.
	private int[] explosionTwoFinishTimer   = new int[5];
	private int[] explosionTwoStartValue    = new int[5];

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

	public static void resetGame() {
		playDeathSound           = false;
		lives                    = 0;
		isPoisoned               = false;
		shouldPlayExplosionMusic = false;
	}

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

		missionWewa = new MissionWewa();
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

	private void handleDeathExplosionSetup() {
		if (getHealth() <= 0 && lives == 1) {
			explosionsTwoShouldBeRendered   = true;
			explosionsTwoShouldBeCreated    = true;
			playDeathSound                  = true;
			DeathUi.deathUiShouldBeRendered = true;
		}

		if (getHealth() <= 0 && lives == 0) {
			explosionsOneShouldBeRendered   = true;
			explosionsOneShouldBeCreated    = true;
			playDeathSound                  = true;
			DeathUi.deathUiShouldBeRendered = true;
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
		handleWalking(myGame);
		handleJumping(myGame);
		handleBounceBack();

		handleDeathExplosionSetup();

		handlePoison();

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

		//simulateDeath(myGame, this);

		if (Inventory.inventoryHasStartedCollection) {
			inventory.updateInventory(x, y, mapHandler);
		}

		handleTextures();
		setPlayerAnimations();

		handleDeathExplosion(myGame, mapHandler);

		// Make sure we can't use weapons when inventory or map or store is open.
		if (Inventory.allInventoryShouldBeRendered || MapUi.mapShouldBeRendered || Store.playerWantsToEnterStore || Store.shouldDisplayEnterStoreMessage) {
			playerIsPerformingAttack = false;
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	private void handleDeathExplosion(MyGame myGame, MapHandler mapHandler) {

		// Explosion one.
		if (explosionsOneShouldBeCreated) { 
			for (int i = 0; i < explosion.length; i++) {
				float xPos   = (float) RandomNumberGenerator.generateRandomDouble(x - width, x + width);
				float yPos   = (float) RandomNumberGenerator.generateRandomDouble(y, y + height);
				explosion[i] = new Explosion(xPos, yPos, explosionSize);
			}
			explosionsOneShouldBeCreated = false;
		}

		if (explosionsOneShouldBeRendered && explosionFinishTimer[4] < EXPLOSION_MAX_TIME) {
			explosionOffsetTimer++;
			for (int i = 0; i < explosion.length; i++) {
				if (explosion[i] != null) {
					explosion[i].updateObject(myGame, mapHandler);
				}
			}
			GameScreen.screenShake.shake(0.3f,  3);
		} else if (explosionFinishTimer[4] > EXPLOSION_MAX_TIME) {
			explosionsOneShouldBeRendered  = true;
		}

		// Explosion two.
		if (explosionsTwoShouldBeCreated) { 
			for (int i = 0; i < explosionTwo.length; i++) {
				float xPos      = (float) RandomNumberGenerator.generateRandomDouble(x - width, x + width);
				float yPos      = (float) RandomNumberGenerator.generateRandomDouble(y, y + height);
				explosionTwo[i] = new Explosion(xPos, yPos, explosionSize);
			}
			explosionsTwoShouldBeCreated = false;
		}

		if (explosionsTwoShouldBeRendered && explosionTwoFinishTimer[4] < EXPLOSION_MAX_TIME) {
			explosionTwoOffsetTimer++;
			for (int i = 0; i < explosionTwo.length; i++) {
				if (explosionTwo[i] != null) {
					explosionTwo[i].updateObject(myGame, mapHandler);
				}
			}
			GameScreen.screenShake.shake(0.3f,  3);
		} else if (explosionTwoFinishTimer[4] > EXPLOSION_MAX_TIME) {
			explosionsTwoShouldBeRendered  = true;
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
				HealthUi.heartsShouldFlashWhite = true;

				if (health <= 0) {
					DeathUi.deathUiShouldBeRendered = true;
				}
			} else {
				HealthUi.heartsShouldFlashWhite = false;
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

		if (isPoisoned) {
			batch.setColor(Color.GREEN);
		} 

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

		// Reset batch if player isn't poisoned.
		batch.setColor(Color.WHITE);

		if (hasTorch) {	
			torch.renderObject(batch, imageLoader);
		}

		renderOar(batch, imageLoader);

		if (isPoisoned) {
			batch.draw(imageLoader.poisonCover, x, y, width, -height);
		}
		//renderHitBox(batch, imageLoader);

		renderExplosions(batch, imageLoader);

		renderLocationMarkerGoalIcon(batch, imageLoader);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderLocationMarkerGoalIcon(SpriteBatch batch, ImageLoader imageLoader) {
		int locationSkullSize = 1;
		float locationSkullX  = 0;
		float locationSkullY  = 0;
		currentMission        = getCurrentMission();
		float offset          = 4.0f;

		/*
		locationMarkerIconFlashTimer++;
		if (locationMarkerIconFlashTimer > LOCATION_MARKER_FLASH_TIMER_INCREMENT) {
			locationMarkerIconFlashTimer = 0;
		} */

		//if (locationMarkerIconFlashTimer % 30 == 0) {
		if (currentMission == MISSION_GO_TO_TRADING_POST) {
			GameObject tradingPost = TradingPostLoader.tradingPost;
			if (tradingPost.getX() < x) {
				locationSkullX = x - offset;
			} else {
				locationSkullX = x + offset;
			}
			if (tradingPost.getY() < y) {
				locationSkullY = y - offset;
			} else {
				locationSkullY = y + offset;
			}
		} else if (currentMission == MISSION_GO_TO_RAW_BAR) {
			GameObject rawbar = RawBarLoader.rawbar;
			if (rawbar.getX() < x) {
				locationSkullX = x - offset;
			} else {
				locationSkullX = x + offset;
			}
			if (rawbar.getY() < y) {
				locationSkullY = y - offset;
			} else {
				locationSkullY = y + offset;
			}
		} else if (currentMission == MISSION_GO_TO_STUMP_HOLE) {
			GameObject stump = MissionStumpHole.stumps.get(1);
			if (stump.getX() < x) {
				locationSkullX = x - offset;
			} else {
				locationSkullX = x + offset;
			}
			if (stump.getY() < y) {
				locationSkullY = y - offset;
			} else {
				locationSkullY = y + offset;
			}
		} else if (currentMission == MISSION_GO_TO_WEWA) {
			GameObject market = MarketLoader.market;
			if (market.getX() < x) {
				locationSkullX = x - offset;
			} else {
				locationSkullX = x + offset;
			}
			if (market.getY() < y) {
				locationSkullY = y - offset;
			} else {
				locationSkullY = y + offset;
			}
		} else if (currentMission == MISSION_THE_POINT) {
			GameObject boss = BossLoader.boss[BossHandler.THE_POINT];
			if (boss.getX() < x) {
				locationSkullX = x - offset;
			} else {
				locationSkullX = x + offset;
			}
			if (boss.getY() < y) {
				locationSkullY = y - offset;
			} else {
				locationSkullY = y + offset;
			}
		} else if (currentMission == MISSION_COLLECT_BIRD) {
			GameObject stump = MissionStumpHole.stumps.get(1);
			if (stump.getX() < x) {
				locationSkullX = x - offset;
			} else {
				locationSkullX = x + offset;
			}
			if (stump.getY() < y) {
				locationSkullY = y - offset;
			} else {
				locationSkullY = y + offset;
			}
		}
		//}
		batch.draw(imageLoader.locationSkull, locationSkullX, locationSkullY, locationSkullSize, -locationSkullSize);
	}

	private int getCurrentMission() {
		int currentMission = 10; // TODO CHANGE THIS WHEN COMPLETE
		if (MissionChests.chestMissionIsComplete && !MissionTradinPost.locationMarkerHasBeenHit) {
			currentMission = MISSION_GO_TO_TRADING_POST;
		} else if (BossLoader.boss[BossHandler.TRADIN_POST].isDead() && !MissionRawBar.locationMarkerHasBeenHit) {
			currentMission = MISSION_GO_TO_RAW_BAR;
		} else if (BossLoader.boss[BossHandler.APALACHICOLA].isDead() && !MissionStumpHole.stumpHoleMissionComplete) {
			currentMission = MISSION_GO_TO_STUMP_HOLE;
		} else if (BossLoader.boss[BossHandler.STUMP_HOLE].isDead() && !MissionWewa.wewaMissionComplete) {
			currentMission = MISSION_GO_TO_WEWA;
		} else if (MissionWewa.wewaMissionComplete && !MissionThePoint.missionThePointComplete) {
			currentMission = MISSION_THE_POINT;
		} 
		return currentMission;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderExplosions(SpriteBatch batch, ImageLoader imageLoader) {
		if (explosionsOneShouldBeRendered) {
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

		if (explosionsTwoShouldBeRendered) {
			if (explosionTwo[0] != null) {
				explosionTwoFinishTimer[0]++;
				if (explosionTwoOffsetTimer > explosionTwoStartValue[0] && explosionTwoFinishTimer[0] < EXPLOSION_MAX_TIME) {
					explosionTwo[0].renderExplosion(batch, imageLoader);
				}
			}
			if (explosionTwo[1] != null) {
				explosionTwoFinishTimer[1]++;
				if (explosionTwoOffsetTimer > explosionTwoStartValue[1] && explosionTwoFinishTimer[1] < EXPLOSION_MAX_TIME) {
					explosionTwo[1].renderExplosion(batch, imageLoader);
				}
			}
			if (explosionTwo[2] != null) {
				explosionTwoFinishTimer[2]++;
				if (explosionTwoOffsetTimer > explosionTwoStartValue[2] && explosionTwoFinishTimer[2] < EXPLOSION_MAX_TIME) {
					explosionTwo[2].renderExplosion(batch, imageLoader);
				}
			}
			if (explosionTwo[3] != null) {
				explosionTwoFinishTimer[3]++;
				if (explosionTwoOffsetTimer > explosionTwoStartValue[3] && explosionTwoFinishTimer[3] < EXPLOSION_MAX_TIME) {
					explosionTwo[3].renderExplosion(batch, imageLoader);
				}
			}
			if (explosionTwo[4] != null) {
				explosionTwoFinishTimer[4]++;
				if (explosionTwoOffsetTimer > explosionTwoStartValue[4] && explosionTwoFinishTimer[4] < EXPLOSION_MAX_TIME) {
					explosionTwo[4].renderExplosion(batch, imageLoader);
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
