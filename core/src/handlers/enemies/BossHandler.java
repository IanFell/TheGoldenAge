package handlers.enemies;

import com.mygdx.mygame.MyGame;

import cutscenes.CutSceneCutthroat;
import gameobjects.weapons.Gun;
import loaders.bossloader.BossLoader;
import maps.MapHandler;
import missions.MissionCauldron;
import missions.MissionRawBar;
import missions.MissionStumpHole;
import missions.MissionThePoint;
import ui.BossHealthUi;
import ui.GameOver;

/**
 * Boss logic goes here.
 * 
 * @author Fabulous Fellini
 *
 */
public class BossHandler {

	public final static int TRADIN_POST  = 0;
	public final static int APALACHICOLA = 1;
	public final static int STUMP_HOLE   = 2;
	public final static int WEWA         = 3;
	public final static int THE_POINT    = 4;

	public static boolean[] shouldPlayLaughSound    = new boolean[5];
	public static boolean[] laughSoundHasBeenPlayed = new boolean[5];

	private static boolean healthMeterAlphaTradingPostSet = false;
	private static boolean healthMeterAlphaRawBarSet      = false;
	private static boolean healthMeterAlphaStumpHoleSet   = false;
	private static boolean healthMeterAlphaCauldronSet    = false;
	private static boolean healthMeterAlphaMapSet         = false;
	
	public static void resetGame() {
		healthMeterAlphaTradingPostSet = false;
		healthMeterAlphaRawBarSet      = false;
		healthMeterAlphaStumpHoleSet   = false;
		healthMeterAlphaCauldronSet    = false;
		healthMeterAlphaMapSet         = false;
	}

	/**
	 * Constructor.
	 */
	public BossHandler() {
		for (int i = 0; i < BossLoader.boss.length; i++) {
			shouldPlayLaughSound[i]    = false;
			laughSoundHasBeenPlayed[i] = false;
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public static void handleBosses(MyGame myGame, MapHandler mapHandler) {
		if (!GameOver.triggerGameOver) {
			if (Gun.hasBeenCollected && CutSceneCutthroat.bossCanLaugh) {
				BossLoader.boss[TRADIN_POST].updateObject(myGame, mapHandler);
				BossLoader.boss[TRADIN_POST].setBattleMusicHasStarted(true);
				handleBossLaughAtStartOfBattle(TRADIN_POST);

				if (!healthMeterAlphaTradingPostSet) {
					BossHealthUi.alpha             = 0;
					healthMeterAlphaTradingPostSet = true;
				}
			}
			if (MissionRawBar.rawBarMissionComplete && !BossLoader.boss[APALACHICOLA].isDead()) {
				BossLoader.boss[APALACHICOLA].updateObject(myGame, mapHandler);
				BossLoader.boss[APALACHICOLA].setBattleMusicHasStarted(true);
				handleBossLaughAtStartOfBattle(APALACHICOLA);

				if (!healthMeterAlphaRawBarSet) {
					BossHealthUi.alpha        = 0;
					healthMeterAlphaRawBarSet = true;
				}
			}
			if (MissionStumpHole.stumpHoleMissionComplete && !BossLoader.boss[STUMP_HOLE].isDead()) {
				BossLoader.boss[STUMP_HOLE].updateObject(myGame, mapHandler);
				BossLoader.boss[STUMP_HOLE].setBattleMusicHasStarted(true);
				handleBossLaughAtStartOfBattle(STUMP_HOLE);

				if (!healthMeterAlphaStumpHoleSet) {
					BossHealthUi.alpha           = 0;
					healthMeterAlphaStumpHoleSet = true;
				}
			}
			if (MissionCauldron.missionCauldronComplete && !BossLoader.boss[WEWA].isDead()) {
				BossLoader.boss[WEWA].updateObject(myGame, mapHandler);
				BossLoader.boss[WEWA].setBattleMusicHasStarted(true);
				handleBossLaughAtStartOfBattle(WEWA);

				if (!healthMeterAlphaCauldronSet) {
					BossHealthUi.alpha          = 0;
					healthMeterAlphaCauldronSet = true;
				}
			}
			if (MissionThePoint.missionThePointComplete && !BossLoader.boss[THE_POINT].isDead()) {
				BossLoader.boss[THE_POINT].updateObject(myGame, mapHandler);
				BossLoader.boss[THE_POINT].setBattleMusicHasStarted(true);
				handleBossLaughAtStartOfBattle(THE_POINT);

				if (!healthMeterAlphaMapSet) {
					BossHealthUi.alpha     = 0;
					healthMeterAlphaMapSet = true;
				}
			}
		}
	}

	/**
	 * 
	 * @param int battle
	 */
	private static void handleBossLaughAtStartOfBattle(int battle) {
		if (!laughSoundHasBeenPlayed[battle]) {
			shouldPlayLaughSound[battle]    = true;
			laughSoundHasBeenPlayed[battle] = true;
		}
	}
}
