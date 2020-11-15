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
	
	private int MAX_HEALTH_TRADING_POST = 30;
	private int MAX_HEALTH_APALACHICOLA = 50;
	private int MAX_HEALTH_STUMP_HOLE   = 70;
	private int MAX_HEALTH_WEWA         = 100;
	private int MAX_HEALTH_POINT        = 130;

	public void loadBosses() {
		boss[0] = new Boss(
				TradingPostLoader.tradingPost.getX(), 
				TradingPostLoader.tradingPost.getY(), 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT,
				MAX_HEALTH_TRADING_POST
				);
		boss[1] = new Boss(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 40, 
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 45, 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT,
				MAX_HEALTH_APALACHICOLA
				);
		boss[2] = new Boss(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 12, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 40, 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT,
				MAX_HEALTH_STUMP_HOLE
				);
		boss[3] = new Boss(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 62, 
				30, 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT,
				MAX_HEALTH_WEWA
				);
		boss[4] = new Boss(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 12, 
				GameAttributeHelper.CHUNK_TWO_Y_POSITION_START + 34, 
				Boss.WIDTH, Boss.HEIGHT, 
				GameObject.DIRECTION_LEFT,
				MAX_HEALTH_POINT
				);
		for (int i = 0; i < boss.length; i++) {
			GameObjectLoader.gameObjectList.add(boss[i]);
		}
	}
}
