package ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

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

	private final int WIDTH  = 5;
	private final int HEIGHT = 1;

	private Texture objectiveTexture = null;

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
		if (flashTimer > VALUE_TO_FLASH) {
			Texture objectiveTexture = getObjectiveTexture(imageLoader, myGame);
			batch.draw(
					objectiveTexture,
					player.getX() + 7, 
					player.getY() - 5, 
					WIDTH, 
					-HEIGHT
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
			if (MissionTradinPost.locationMarkerHasBeenHit) {
				objectiveTexture = imageLoader.objectiveEnterTheTradingPost;
			}

			// Player has purchased gun from Trading Post and has left the store and has completed Stump Hole mission.
			if (Store.gunHasBeenPurchasedAtStore) {
				objectiveTexture = imageLoader.objectiveRawBar;
				if (MissionRawBar.rawBarMissionComplete && !MissionRawBar.phasesAreInProgress) {
					objectiveTexture = imageLoader.objectiveStumpHole;
					if (MissionStumpHole.missionIsActive) {
						objectiveTexture = imageLoader.objectiveCollectFeathers;
					}
				}
			}

			// Player has beat the stump hole boss and needs to collect the bird.
			if (BossLoader.boss[BossHandler.STUMP_HOLE].isDead() && !myGame.getGameScreen().getBirdWeapon().hasBeenCollected) {
				objectiveTexture = imageLoader.objectiveCollectTheBird;
			}

			// Player has collected the bird and needs to go to Wewa.
			if (!MissionWewa.wewaMissionComplete && myGame.getGameScreen().getBirdWeapon().hasBeenCollected) {
				objectiveTexture = imageLoader.objectiveGoToWewa;
			}

			// Player needs to find the cauldron.
			if (MissionWewa.wewaMissionComplete) {
				objectiveTexture = imageLoader.objectiveFindTheCauldron;
			}

			// Player has found the cauldron, and needs to go get the other part of the map at The Point.
			if (MissionCauldron.missionCauldronComplete) {
				objectiveTexture = imageLoader.objectiveCollectTheMapAtThePoint;
			}

			// Player has defeated the boss at The Point and needs to go to Blacks Island and find the treasure.
			if (BossLoader.boss[BossHandler.THE_POINT].isDead()) {
				objectiveTexture = imageLoader.objectiveFindTheTreasureAtBlacksIsland;
			}
		}

		if (!MissionRawBar.rawBarMissionComplete && MissionRawBar.phasesAreInProgress) {
			objectiveTexture = imageLoader.objectiveCollectOysters;
		}

		// Bosses.
		handleBossUiObjectives(imageLoader);

		// Player is inside Trading Post and needs to buy the gun.
		if (Store.playerWantsToEnterStore && TradingPost.hasBeenEntered && !Store.gunHasBeenPurchasedAtStore) {
			objectiveTexture = imageLoader.objectiveBuyTheGun;
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
}
