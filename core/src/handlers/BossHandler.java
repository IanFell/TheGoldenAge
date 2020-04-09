package handlers;

import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.Boss;
import gameobjects.weapons.Gun;
import loaders.bossloader.BossLoader;
import maps.MapHandler;

/**
 * Boss logic goes here.
 * 
 * @author Fabulous Fellini
 *
 */
public class BossHandler {
	
	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public static void handleBosses(MyGame myGame, MapHandler mapHandler) {
		if (Gun.hasBeenCollected) {
			BossLoader.boss[BossLoader.APALACHICOLA].updateObject(myGame, mapHandler);
			Boss.battleMusicHasStarted = true;
		}
	}
}
