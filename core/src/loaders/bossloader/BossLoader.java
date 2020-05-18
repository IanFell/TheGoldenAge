package loaders.bossloader;

import gameobjects.GameObject;
import gameobjects.gamecharacters.enemies.Boss;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class BossLoader {

	public static Boss[] boss = new Boss[4];

	public void loadBosses() {
		for (int i = 0; i < boss.length; i++) {
			boss[i] = new Boss(0, 0, Boss.WIDTH, Boss.HEIGHT, GameObject.DIRECTION_LEFT);
			GameObjectLoader.gameObjectList.add(boss[i]);
		}
	}
}
