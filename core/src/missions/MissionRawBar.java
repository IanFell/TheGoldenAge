package missions;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import gameobjects.nature.Bubble;
import handlers.CollisionHandler;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import loaders.ImageLoader;
import screens.GameScreen;
import transitions.Transition;
import ui.LocationMarker;

/**
 * This mission will consist of three phases, where the player will have to collect a certain amount of 
 * oysters within a certain amount of time.
 *  
 * @author Fabulous Fellini
 *
 */
public class MissionRawBar extends Mission {

	// Time alloted for all phases of mission.
	public static final float MAX_MISSION_TIME_PHASE_ONE = 7f;

	private final int MAX_OYSTERS_SPAWNED                       = 100;
	public static final int NUMBER_OF_OYSTERS_NEEDED_PHASE_ONE  = 7;

	private int numberOfOystersNeededToWin;

	// Represents if PHASES are active, not necessarily entire mission.
	public static boolean phaseIsActive = true;

	// Represents ENTIRE mission, not just phases.
	public static boolean missionIsActive = false;

	private boolean initializeMission = true;

	// Each instance of this class in MissionHandler has their own phaseComplete.
	private boolean phaseComplete = false;

	public static boolean phasesAreInProgress = false;

	// Oysters will have a random x, y, and size.
	private double[] oysterX                   = new double[MAX_OYSTERS_SPAWNED];
	private double[] oysterY                   = new double[MAX_OYSTERS_SPAWNED];
	private double[] oysterSize                = new double[MAX_OYSTERS_SPAWNED];
	private float oystersCollected             = 10; // TODO set this back to 0.  It's at 10 for debugging.
	private final float OYSTER_VALUE           = 0.2f;
	private ArrayList<Boolean> collectedOyster = new ArrayList<Boolean>();

	public static float playerX;
	public static float playerY;
	private float playerSize = 1.5f;

	// Hitboxes for collision detection.
	private Rectangle[] oysterBounds = new Rectangle[MAX_OYSTERS_SPAWNED];
	private Rectangle playerBounds   = new Rectangle(0, 0, 0, 0);

	// Used for countdown timer.
	private float timeSeconds = 0f;
	private float phaseTimeLimit;

	public static final float MISSION_RAW_BAR_SPEED = 0.3f;

	public static boolean introHasCompleted = false;

	public static boolean rawBarMissionComplete = false;

	private boolean failMessageShouldDisplay = false;

	private int failMessageTimer = 0;

	private final int FAIL_MESSAGE_RENDER_MAX_VALUE = 50;

	public static boolean playCollectionSound = false;

	private static boolean setPlayer = true;

	// Used when player enters the raw bar to start the mission.
	public static boolean startMission = false;

	public static boolean locationMarkerHasBeenHit = false;

	private static LocationMarker locationMarker;

	private Transition transition;

	private boolean initializeTransition = true;

	private Bubble[] bubble = new Bubble[50];

	/**
	 * Constructor.
	 * 
	 * @param int   numberOfOystersNeededToWin
	 * @param float phaseTimeLimit
	 */
	public MissionRawBar(int numberOfOystersNeededToWin, float phaseTimeLimit) {
		this.numberOfOystersNeededToWin = numberOfOystersNeededToWin;
		this.phaseTimeLimit             = phaseTimeLimit;
		for (int i = 0 ; i < MAX_OYSTERS_SPAWNED; i++) {
			oysterBounds[i] = new Rectangle(0, 0, 0, 0);
			collectedOyster.add(false);
		}

		locationMarker = new LocationMarker(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 40, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 45
				);

		// Only set the player once.
		if (setPlayer) {
			playerX   = locationMarker.getLocator().getX();
			playerY   = locationMarker.getLocator().getY();
			setPlayer = false;
		}

		for (int i = 0; i < bubble.length; i++) {
			float bubbleSize               = 1;
			double randomSizeDetermination = RandomNumberGenerator.generateRandomDouble(0, 100);
			if (randomSizeDetermination < 50) {
				bubbleSize = 0.5f;
			}
			float bubbleDy               = 0.3f;
			double randomDyDetermination = RandomNumberGenerator.generateRandomDouble(0, 100);
			if (randomDyDetermination < 50) {
				bubbleDy = 0.1f;
			}
			int xOffset           = 15;
			double xPlanePosition = RandomNumberGenerator.generateRandomDouble(locationMarker.getLocator().getX() - xOffset, locationMarker.getLocator().getX() + xOffset);
			int yOffset           = 10;
			double yPlanePosition = RandomNumberGenerator.generateRandomDouble(locationMarker.getLocator().getY() - yOffset, locationMarker.getLocator().getY() + yOffset);
			bubble[i] = new Bubble((int) xPlanePosition, (int) yPlanePosition, bubbleSize, bubbleDy);
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void initializeMission(MyGame myGame) {
		int cameraOffset = 10;
		for (int i = 0 ; i < MAX_OYSTERS_SPAWNED; i++) {
			oysterX[i]             = RandomNumberGenerator.generateRandomDouble(
					GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 40 - cameraOffset, 
					GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 40 + cameraOffset
					);
			oysterY[i]             = RandomNumberGenerator.generateRandomDouble(
					GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 45 - cameraOffset, 
					GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 45 + cameraOffset
					);
			oysterSize[i]          = RandomNumberGenerator.generateRandomDouble(0.2d, 0.8d);
			oysterBounds[i].x      = (float) oysterX[i];
			oysterBounds[i].y      = (float) oysterY[i];
			oysterBounds[i].width  = (float) oysterSize[i];
			oysterBounds[i].height = (float) oysterSize[i];
		}
		playerBounds.width  = playerSize;
		playerBounds.height = playerSize;
		missionComplete     = false;
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void updateMission(MyGame myGame) {
		if (initializeMission) {
			initializeMission(myGame);
			initializeMission = false;
		}

		if (phasesAreInProgress) {
			if (rawBarMissionComplete) {
				missionIsActive     = false;
				phasesAreInProgress = false;
			}
			updatePhases(myGame);
		} else {
			locationMarker.updateObject();
			if (CollisionHandler.playerHasCollidedWithLocationMarker(myGame.getGameObject(Player.PLAYER_ONE), locationMarker)) {
				phasesAreInProgress        = true;
				locationMarkerHasBeenHit   = true;
				MissionRawBar.startMission = true;
			}
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public static void updateLocationMarker(MyGame myGame) {
		locationMarker.updateObject();
		if (CollisionHandler.playerHasCollidedWithLocationMarker(myGame.getGameObject(Player.PLAYER_ONE), locationMarker)) {
			phasesAreInProgress        = true;
			locationMarkerHasBeenHit   = true;
			MissionRawBar.startMission = true;
		}
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	private void updatePhases(MyGame myGame) {
		if (initializeTransition) {
			transition           = new Transition(myGame);
			initializeTransition = false;
		}
		transition.updateTransition();

		playerBounds.x = playerX;
		playerBounds.y = playerY;

		for (int i = 0 ; i < MAX_OYSTERS_SPAWNED; i++) {
			if (playerBounds.overlaps(oysterBounds[i]) && collectedOyster.get(i).equals(false)) {
				collectedOyster.set(i, true);
				oystersCollected += OYSTER_VALUE;
				playCollectionSound = true;
			}
		}

		for (int i = 0; i < bubble.length; i++) {
			bubble[i].updateObject(myGame);
		}

		handleCountdownTimer();
		handleFailMessageTimer();
		checkForMissionComplete();

		// This makes the location marker NOT beep during mission.  DO NOT REMOVE.
		LocationMarker.playBeepSound = false;
	}

	private void handleFailMessageTimer() {
		if (failMessageShouldDisplay) {
			failMessageTimer++;
			if (failMessageTimer > FAIL_MESSAGE_RENDER_MAX_VALUE) {
				failMessageShouldDisplay = false;
				failMessageTimer         = 0;
			}
		}
	}

	/**
	 * Countdown until timeSeconds exceeds phaseTimeLimit.  Technically it counts up.
	 */
	private void handleCountdownTimer() {
		timeSeconds +=Gdx.graphics.getRawDeltaTime();
		if(timeSeconds > phaseTimeLimit){    
			resetMissionForRetry();
		}
	}

	private void resetMissionForRetry() {
		for (int i = 0 ; i < MAX_OYSTERS_SPAWNED; i++) {
			collectedOyster.set(i, false);
		}
		timeSeconds              = 0;
		oystersCollected         = 0;
		failMessageTimer         = 0;
		failMessageShouldDisplay = true;
	}

	private void checkForMissionComplete() {
		if (oystersCollected >= numberOfOystersNeededToWin) {
			missionComplete       = true;
			phaseComplete         = true;
			phasesAreInProgress   = false;
			rawBarMissionComplete = true;
		}
	}

	/**
	 * 
	 * @return boolean
	 */
	public boolean isPhaseComplete() {
		return phaseComplete;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	private void renderPhases(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		// Draw background.
		batch.draw(
				imageLoader.sandTile, 
				GameScreen.camera.position.x - GameScreen.camera.viewportWidth / 2, 
				GameScreen.camera.position.y + GameScreen.camera.viewportHeight / 2,
				GameScreen.camera.viewportWidth, 
				-GameScreen.camera.viewportHeight
				);

		// Draw oysters.
		for (int i = 0 ; i < MAX_OYSTERS_SPAWNED; i++) {
			// Only draw oysters if they are alive.
			if (collectedOyster.get(i).equals(false)) {
				batch.draw(
						imageLoader.oyster, 
						(float) oysterX[i], 
						(float) oysterY[i],
						(float) oysterSize[i], 
						(float) -oysterSize[i]
						);
			}
		}

		// Draw player.
		if (playerX > GameScreen.cameraX + 2.5f) {
			batch.draw(
					imageLoader.playerLeft, 
					playerX, 
					playerY,
					playerSize, 
					-playerSize
					);
		} else {
			batch.draw(
					imageLoader.playerRight, 
					playerX, 
					playerY,
					playerSize, 
					-playerSize
					);
		} 

		// Draw water.
		batch.draw(
				imageLoader.missionTransparentBlueSquare, 
				GameScreen.camera.position.x - GameScreen.camera.viewportWidth / 2, 
				GameScreen.camera.position.y + GameScreen.camera.viewportHeight / 2,
				GameScreen.camera.viewportWidth, 
				-GameScreen.camera.viewportHeight
				);

		renderMeters(batch, imageLoader);

		if (failMessageShouldDisplay) {
			renderFailMessage(batch, imageLoader, myGame);
		}

		for (int i = 0; i < bubble.length; i++) {
			bubble[i].renderObject(batch, imageLoader);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	private void renderMeters(SpriteBatch batch, ImageLoader imageLoader) {
		// Oyster value meter.
		float x = GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 51;
		float y = GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 48;
		renderValueMeter(batch, x, y, -numberOfOystersNeededToWin, imageLoader.blackSquare);
		renderValueMeter(batch, x, y, -oystersCollected, imageLoader.whiteSquare);

		// Time limit meter.
		x = GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 49;
		y = GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 48;
		renderValueMeter(batch, x, y, -phaseTimeLimit, imageLoader.blackSquare);
		renderValueMeter(batch, x, y, -timeSeconds, imageLoader.whiteSquare);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	private void renderFailMessage(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		batch.draw(
				imageLoader.objectiveTryAgain, 
				myGame.getGameObject(Player.PLAYER_ONE).getX() - 2, 
				myGame.getGameObject(Player.PLAYER_ONE).getY() - 0.5f,
				4, 
				-1
				);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 */
	@Override
	public void renderMission(SpriteBatch batch, ImageLoader imageLoader, MyGame myGame) {
		if (phasesAreInProgress) {
			renderPhases(batch, imageLoader, myGame);
			if (transition != null) {
				transition.renderTransition(batch, imageLoader);
			}
		} else {
			if (locationMarker.timerValuesAreCorrectToFlash()) {
				locationMarker.renderObject(batch, imageLoader);
			}
		}
	}
}
