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

	private final int APALACHICOLA = 0;

	public void loadBosses() {
		boss[APALACHICOLA] = new Boss(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 35, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 43,
				Boss.WIDTH,
				Boss.HEIGHT,
				GameObject.DIRECTION_LEFT
				);
		GameObjectLoader.gameObjectList.add(boss[0]);
	}
}
