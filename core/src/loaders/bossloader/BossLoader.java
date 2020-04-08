package loaders.bossloader;

import gameobjects.GameObject;
import gameobjects.gamecharacters.Boss;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class BossLoader {

	public static GameObject[] boss = new GameObject[1];

	public static final int APALACHICOLA = 0;

	public void loadBosses() {
		boss[APALACHICOLA] = new Boss(
				0, 
				0,
				Boss.WIDTH,
				Boss.HEIGHT,
				GameObject.DIRECTION_LEFT
				);
		GameObjectLoader.gameObjectList.add(boss[0]);
	}
}
