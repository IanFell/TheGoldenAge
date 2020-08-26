package ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import cutscenes.CutSceneBird;
import gameobjects.GameObject;
import gameobjects.stationarygameobjects.buildings.TradingPost;
import gameobjects.weapons.Gun;
import handlers.enemies.BossHandler;
import loaders.ImageLoader;
import loaders.bossloader.BossLoader;
import missions.MissionCauldron;
import missions.MissionChests;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import missions.MissionThePoint;
import missions.MissionTradinPost;
import missions.MissionWewa;
import store.Store;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ObjectiveUi {

	// Use this to flash objective.
	private int flashTimer = 0;

	private final int VALUE_TO_FLASH    = 7;
	private final int RESET_TIMER_VALUE = 17;

	private final int WIDTH  = 9;
	private final int HEIGHT = 2;

	private Texture objectiveTexture;

	/**
	 * Handles objective change audio.
	 */
	public static boolean playObjectiveChangeGoToTradingPost  = false;
	public static boolean playObjectiveChangeBuyTheGun        = false;
	public static boolean playObjectiveChangeEnterTradingPost = false;
	public static boolean playObjectiveChangeGoToRawBar       = false;
	public static boolean playObjectiveChangeCollectOysters   = false;
	public static boolean playObjectiveChangeGoToStumpHole    = false;
	public static boolean playObjectiveChangeCollectFeathers  = false;
	public static boolean playObjectiveChangeCollectTheBird   = false;
	public static boolean playObjectiveChangeGoToWewa         = false;
	public static boolean playObjectiveChangeFindTheCauldron  = false;
	public static boolean playObjectiveChangeThePoint         = false;
	public static boolean playObjectiveChangeTreasure         = false;

	/**
	 * Handles objective change audio.
	 */
	private boolean objectiveChangeGoToTradingPostHasBeenPlayed  = false;
	private boolean objectiveChangeBuyTheGunHasBeenPlayed        = false;
	private boolean objectiveChangeEnterTradingPostHasBeenPlayed = false;
	private boolean objectiveChangeGoToRawBarHasBeenPlayed       = false;
	private boolean objectiveChangeCollectOystersHasBeenPlayed   = false;
	private boolean objectiveChangeGoToStumpHoleHasBeenPlayed    = false;
	private boolean objectiveChangeCollectFeathersHasBeenPlayed  = false;
	private boolean objectiveChangeCollectTheBirdHasBeenPlayed   = false;
	private boolean objectiveChangeGoToWewaHasBeenPlayed         = false;
	private boolean objectiveChangeFindTheCauldronHasBeenPlayed  = false;
	private boolean objectiveChangeThePointHasBeenPlayed         = false;
	private boolean objectiveChangeTreasureHasBeenPlayed         = false;

	/**
	 * Constructor.
	 */
	public ObjectiveUi() {
		objectiveTexture = null;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 * @param GameOjbect  player
	 */
	public void renderUi(
			SpriteBatch batch, 
			ImageLoader imageLoader, 
			MyGame myGame, 
			GameObject player
			) {
		float xPos   = player.getX() + 3;
		float yPos   = player.getY() - 4;
		float width  = WIDTH;
		float height = HEIGHT;
		
		float backgroundWidth = width;
		float backgroundX     = xPos;
		
		if (objectiveTexture != null) {
			if (objectiveTexture.equals(imageLoader.objectiveKillTheBoss)) {
				xPos            = player.getX() + 5.5f;
				yPos            = yPos + 1;
				width           = width + 2;
				height          = height + 2;
				backgroundWidth = width - 2;
			}
			if (objectiveTexture.equals(imageLoader.objectiveTradinPost)) {
				xPos            = xPos - 2.5f;
				yPos            = yPos + 1;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width + 1;
			}
			if (objectiveTexture.equals(imageLoader.objectiveEnterTheTradingPost)) {
				xPos            = xPos - 2.5f;
				yPos            = yPos + 1;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width + 1;
			}
			if (objectiveTexture.equals(imageLoader.objectiveBuyTheGun)) {
				xPos            = player.getX() + 5;
				yPos            = yPos - 1;
				backgroundX     = xPos + 2;
				backgroundWidth = width - 3;
			}
			if (objectiveTexture.equals(imageLoader.objectiveRawBar)) {
				xPos            = player.getX() + 2;
				yPos            = yPos + 2;
				width           = width + 2;
				height          = height + 2;
				backgroundWidth = width + 1;
			}
			if (objectiveTexture.equals(imageLoader.objectiveCollectLoot)) {
				xPos            = player.getX() + 4;
				yPos            = yPos + 1.5f;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width - 3;
			}
			if (objectiveTexture.equals(imageLoader.objectiveCollectOysters)) {
				xPos            = player.getX() + 3;
				yPos            = yPos + 1;
				width           = width + 3;
				height          = height + 2;
				backgroundWidth = width;
			}
			if (objectiveTexture.equals(imageLoader.objectiveCollectFeathers)) {
				xPos            = player.getX() + 3;
				yPos            = yPos + 1;
				width           = width + 2;
				height          = height + 2;
				backgroundWidth = width + 0.5f;
			}
			if (objectiveTexture.equals(imageLoader.objectiveGoToWewa)) {
				xPos            = player.getX() + 3;
				yPos            = yPos + 1;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width - 3;
			}
			if (objectiveTexture.equals(imageLoader.objectiveStumpHole)) {
				xPos            = xPos - 2;
				yPos            = yPos + 1;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width + 0.8f;
			}
			if (objectiveTexture.equals(imageLoader.objectiveCollectTheBird)) {
				xPos            = xPos - 2;
				yPos            = yPos + 1;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width;
			}
			if (objectiveTexture.equals(imageLoader.objectiveFindTheCauldron)) {
				xPos            = xPos - 2;
				yPos            = yPos + 1;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width - 0.2f;
			}
			if (objectiveTexture.equals(imageLoader.objectiveCollectTheMapAtThePoint)) {
				xPos            = xPos - 2;
				yPos            = yPos + 1;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width - 0.5f;
			}
			if (objectiveTexture.equals(imageLoader.objectiveFindTheTreasureAtBlacksIsland)) {
				xPos            = xPos - 3;
				yPos            = yPos + 1;
				width           = width + 4;
				height          = height + 2;
				backgroundWidth = width + 0.8f;
			}
		}
		if (flashTimer > VALUE_TO_FLASH) {
			Texture objectiveTexture = getObjectiveTexture(imageLoader, myGame);
			// Dont draw the background on inventory screen.
			if (!objectiveTexture.equals(imageLoader.objectiveBuyTheGun)) {
				batch.draw(
						imageLoader.objectiveBackground,
						xPos - 1.5f, 
						yPos - 1.8f, 
						backgroundWidth, 
						-height / 2
						); 
			}
			batch.draw(
					objectiveTexture,
					xPos - 1, 
					yPos - 0.5f, 
					width, 
					-height
					); 
		}
	}

	/**
	 * 
	 * @param ImageLoader imageLoader
	 * @param MyGame      myGame
	 * @return Texture
	 */
	private Texture getObjectiveTexture(ImageLoader imageLoader, MyGame myGame) {
		objectiveTexture = imageLoader.objectiveCollectLoot;
		if (MissionChests.chestMissionIsComplete) {
			// Player is on his way to Trading Post or has hit the Trading Post location marker and needs to enter the Post.
			objectiveTexture = imageLoader.objectiveTradinPost;
			if (!objectiveChangeGoToTradingPostHasBeenPlayed) {
				playObjectiveChangeGoToTradingPost          = true;
				objectiveChangeGoToTradingPostHasBeenPlayed = true;
			}
			if (MissionTradinPost.locationMarkerHasBeenHit) {
				objectiveTexture = imageLoader.objectiveEnterTheTradingPost;
				if (!objectiveChangeEnterTradingPostHasBeenPlayed) {
					playObjectiveChangeEnterTradingPost          = true;
					objectiveChangeEnterTradingPostHasBeenPlayed = true;
				}
			}

			// Player has purchased gun from Trading Post and has left the store and has completed Stump Hole mission.
			if (Store.gunHasBeenPurchasedAtStore) {
				objectiveTexture = imageLoader.objectiveRawBar;
				if (!objectiveChangeGoToRawBarHasBeenPlayed && BossLoader.boss[BossHandler.TRADIN_POST].isDead()) {
					playObjectiveChangeGoToRawBar              = true;
					objectiveChangeGoToRawBarHasBeenPlayed     = true;
				}
				//if (MissionRawBar.rawBarMissionComplete && !MissionRawBar.phasesAreInProgress) {
				if (BossLoader.boss[BossHandler.APALACHICOLA].isDead()) {
					objectiveTexture = imageLoader.objectiveStumpHole;
					if (!objectiveChangeGoToStumpHoleHasBeenPlayed) {
						playObjectiveChangeGoToStumpHole              = true;
						objectiveChangeGoToStumpHoleHasBeenPlayed     = true;
					}
					if (MissionStumpHole.missionIsActive) {
						objectiveTexture = imageLoader.objectiveCollectFeathers;
						if (!objectiveChangeCollectFeathersHasBeenPlayed) {
							playObjectiveChangeCollectFeathers              = true;
							objectiveChangeCollectFeathersHasBeenPlayed     = true;
						}
					}
				}
			}

			// Player has beat the stump hole boss and needs to collect the bird.
			if (BossLoader.boss[BossHandler.STUMP_HOLE].isDead() && !myGame.getGameScreen().getBirdWeapon().hasBeenCollected) {
				objectiveTexture = imageLoader.objectiveCollectTheBird;
				if (!objectiveChangeCollectTheBirdHasBeenPlayed) {
					playObjectiveChangeCollectTheBird              = true;
					objectiveChangeCollectTheBirdHasBeenPlayed     = true;
				}
			}

			// Player has collected the bird and needs to go to Wewa.
			if (!MissionWewa.wewaMissionComplete && myGame.getGameScreen().getBirdWeapon().hasBeenCollected) {
				objectiveTexture = imageLoader.objectiveGoToWewa;
				if (!objectiveChangeGoToWewaHasBeenPlayed && CutSceneBird.shouldPlayWewaObjectiveChangeAudio) {
					playObjectiveChangeGoToWewa              = true;
					objectiveChangeGoToWewaHasBeenPlayed     = true;
				}
			}

			// Player needs to find the cauldron.
			if (MissionWewa.wewaMissionComplete) {
				objectiveTexture = imageLoader.objectiveFindTheCauldron;
				if (!objectiveChangeFindTheCauldronHasBeenPlayed) {
					playObjectiveChangeFindTheCauldron              = true;
					objectiveChangeFindTheCauldronHasBeenPlayed     = true;
				}
			}

			// Player has found the cauldron, and needs to go get the other part of the map at The Point.
			if (MissionCauldron.missionCauldronComplete) {
				objectiveTexture = imageLoader.objectiveCollectTheMapAtThePoint;
				if (!objectiveChangeThePointHasBeenPlayed && BossLoader.boss[BossHandler.WEWA].isDead()) {
					playObjectiveChangeThePoint              = true;
					objectiveChangeThePointHasBeenPlayed     = true;
				}
			}

			// Player has defeated the boss at The Point and needs to go to Blacks Island and find the treasure.
			if (BossLoader.boss[BossHandler.THE_POINT].isDead()) {
				objectiveTexture = imageLoader.objectiveFindTheTreasureAtBlacksIsland;
				if (!objectiveChangeTreasureHasBeenPlayed) {
					playObjectiveChangeTreasure              = true;
					objectiveChangeTreasureHasBeenPlayed     = true;
				}
			}
		}

		if (!MissionRawBar.rawBarMissionComplete && MissionRawBar.phasesAreInProgress) {
			objectiveTexture = imageLoader.objectiveCollectOysters;
			if (!objectiveChangeCollectOystersHasBeenPlayed) {
				playObjectiveChangeCollectOysters              = true;
				objectiveChangeCollectOystersHasBeenPlayed     = true;
			}
		}

		// Bosses.
		handleBossUiObjectives(imageLoader);

		// Player is inside Trading Post and needs to buy the gun.
		if (Store.playerWantsToEnterStore && TradingPost.hasBeenEntered && !Store.gunHasBeenPurchasedAtStore) {
			objectiveTexture = imageLoader.objectiveBuyTheGun;
			if (!objectiveChangeBuyTheGunHasBeenPlayed) {
				playObjectiveChangeBuyTheGun          = true;
				objectiveChangeBuyTheGunHasBeenPlayed = true;
			}
		}

		return objectiveTexture;
	}

	/**
	 * 
	 * @param ImageLoader imageLoader
	 */
	private void handleBossUiObjectives(ImageLoader imageLoader) {
		if (Gun.hasBeenCollected && !BossLoader.boss[BossHandler.TRADIN_POST].isDead()) {
			objectiveTexture = imageLoader.objectiveKillTheBoss;
		}
		if (MissionRawBar.rawBarMissionComplete && !BossLoader.boss[BossHandler.APALACHICOLA].isDead()) {
			objectiveTexture = imageLoader.objectiveKillTheBoss;
		}
		if (MissionStumpHole.stumpHoleMissionComplete && !BossLoader.boss[BossHandler.STUMP_HOLE].isDead()) {
			objectiveTexture = imageLoader.objectiveKillTheBoss;
		}
		if (MissionCauldron.missionCauldronComplete && !BossLoader.boss[BossHandler.WEWA].isDead()) {
			objectiveTexture = imageLoader.objectiveKillTheBoss;
		}
		if (MissionThePoint.missionThePointComplete && !BossLoader.boss[BossHandler.THE_POINT].isDead()) {
			objectiveTexture = imageLoader.objectiveKillTheBoss;
		}
	}

	public void updateObjective() {
		flashTimer++;
		if (flashTimer > RESET_TIMER_VALUE) {
			flashTimer = 0;
		}
	}

	public static void resetGame() {
		playObjectiveChangeGoToTradingPost  = false;
		playObjectiveChangeBuyTheGun        = false;
		playObjectiveChangeEnterTradingPost = false;
		playObjectiveChangeGoToRawBar       = false;
		playObjectiveChangeCollectOysters   = false;
		playObjectiveChangeGoToStumpHole    = false;
		playObjectiveChangeCollectFeathers  = false;
		playObjectiveChangeCollectTheBird   = false;
		playObjectiveChangeGoToWewa         = false;
		playObjectiveChangeFindTheCauldron  = false;
		playObjectiveChangeThePoint         = false;
		playObjectiveChangeTreasure         = false;
	}
}
