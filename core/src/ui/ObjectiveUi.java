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
import missions.MissionChests;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import missions.MissionTradinPost;
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
			Texture objectiveTexture = getObjectiveTexture(imageLoader);
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
	 * @return Texture
	 */
	private Texture getObjectiveTexture(ImageLoader imageLoader) {
		objectiveTexture = imageLoader.objectiveCollectLoot;
		if (MissionChests.chestMissionIsComplete) {
			// Player is on his way to Trading Post or has hit the Trading Post location marker and needs to enter the Post.
			objectiveTexture = imageLoader.objectiveTradinPost;
			if (MissionTradinPost.locationMarkerHasBeenHit) {
				objectiveTexture = imageLoader.objectiveEnterTheTradingPost;
			}

			// Player has purchased gun from Trading Post and has left the store.
			if (Store.gunHasBeenPurchasedAtStore) {
				objectiveTexture = imageLoader.objectiveRawBar;
				if (MissionRawBar.rawBarMissionComplete && !MissionRawBar.phasesAreInProgress) {
					objectiveTexture = imageLoader.objectiveStumpHole;
					if (MissionStumpHole.missionIsActive) {
						objectiveTexture = imageLoader.objectiveCollectFeathers;
					}
				}
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
	}

	public void updateObjective() {
		flashTimer++;
		if (flashTimer > RESET_TIMER_VALUE) {
			flashTimer = 0;
		}
	}
}
