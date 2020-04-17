package handlers.enemies;

import com.mygdx.mygame.MyGame;

import gameobjects.weapons.Gun;
import loaders.bossloader.BossLoader;
import maps.MapHandler;
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
	
	public static boolean playLaughSound          = false;
	public static boolean laughSoundHasBeenPlayed = false;

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public static void handleBosses(MyGame myGame, MapHandler mapHandler) {
		if (Gun.hasBeenCollected) {
			BossLoader.boss[TRADIN_POST].updateObject(myGame, mapHandler);
			BossLoader.boss[TRADIN_POST].setBattleMusicHasStarted(true);
			if (!laughSoundHasBeenPlayed) {
				playLaughSound          = true;
				laughSoundHasBeenPlayed = true;
			}
		}
		if (MissionRawBar.rawBarMissionComplete && !BossLoader.boss[APALACHICOLA].isDead()) {
			BossLoader.boss[APALACHICOLA].updateObject(myGame, mapHandler);
			BossLoader.boss[APALACHICOLA].setBattleMusicHasStarted(true);
		}
		if (MissionStumpHole.stumpHoleMissionComplete && !BossLoader.boss[STUMP_HOLE].isDead()) {
			BossLoader.boss[STUMP_HOLE].updateObject(myGame, mapHandler);
			BossLoader.boss[STUMP_HOLE].setBattleMusicHasStarted(true);
		}
	}
}
