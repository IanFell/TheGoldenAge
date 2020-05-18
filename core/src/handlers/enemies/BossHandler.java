package handlers.enemies;

import com.mygdx.mygame.MyGame;

import gameobjects.weapons.Gun;
import loaders.bossloader.BossLoader;
import maps.MapHandler;
import missions.MissionCauldron;
import missions.MissionRawBar;
import missions.MissionStumpHole;

/**
 * Boss logic goes here.
 * 
 * TODO fix laugh SO IT HAPPENS AT THE START OF EVERY BATTLE.
 * 
 * @author Fabulous Fellini
 *
 */
public class BossHandler {

	public final static int TRADIN_POST  = 0;
	public final static int APALACHICOLA = 1;
	public final static int STUMP_HOLE   = 2;
	public final static int WEWA         = 3;

	public static boolean[] shouldPlayLaughSound    = new boolean[4];
	public static boolean[] laughSoundHasBeenPlayed = new boolean[4];

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
		if (Gun.hasBeenCollected) {
			BossLoader.boss[TRADIN_POST].updateObject(myGame, mapHandler);
			BossLoader.boss[TRADIN_POST].setBattleMusicHasStarted(true);
			handleBossLaughAtStartOfBattle(TRADIN_POST);
		}
		if (MissionRawBar.rawBarMissionComplete && !BossLoader.boss[APALACHICOLA].isDead()) {
			BossLoader.boss[APALACHICOLA].updateObject(myGame, mapHandler);
			BossLoader.boss[APALACHICOLA].setBattleMusicHasStarted(true);
			handleBossLaughAtStartOfBattle(APALACHICOLA);
		}
		if (MissionStumpHole.stumpHoleMissionComplete && !BossLoader.boss[STUMP_HOLE].isDead()) {
			BossLoader.boss[STUMP_HOLE].updateObject(myGame, mapHandler);
			BossLoader.boss[STUMP_HOLE].setBattleMusicHasStarted(true);
			handleBossLaughAtStartOfBattle(STUMP_HOLE);
		}
		if (MissionCauldron.missionCauldronComplete && !BossLoader.boss[WEWA].isDead()) {
			BossLoader.boss[WEWA].updateObject(myGame, mapHandler);
			BossLoader.boss[WEWA].setBattleMusicHasStarted(true);
			handleBossLaughAtStartOfBattle(WEWA);
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
