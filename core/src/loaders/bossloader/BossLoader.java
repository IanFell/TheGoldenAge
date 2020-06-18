package loaders.bossloader;

import gameobjects.GameObject;
import gameobjects.gamecharacters.enemies.Boss;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import loaders.tradingpostloader.TradingPostLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class BossLoader {

	public static Boss[] boss = new Boss[5];

	public void loadBosses() {
		boss[0] = new Boss(
				TradingPostLoader.tradingPost.getX(), 
				TradingPostLoader.tradingPost.getY(), 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT
				);
		boss[1] = new Boss(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 40, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 45, 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT
				);
		boss[2] = new Boss(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 12, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 40, 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT
				);
		boss[3] = new Boss(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 62, 
				30, 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT
				);
		boss[4] = new Boss(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 12, 
				GameAttributeHelper.CHUNK_TWO_Y_POSITION_START + 34, 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT
				);
		for (int i = 0; i < boss.length; i++) {
			GameObjectLoader.gameObjectList.add(boss[i]);
		}
	}
}
