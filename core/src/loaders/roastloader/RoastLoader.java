package loaders.roastloader;

import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.Roast;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class RoastLoader {

	private final int MEXICO_BEACH    = 0;
	private final int STUMP_HOLE      = 1; 
	private final int THE_POINT       = 2;
	private final int WEWA            = 3;
	private final int APALACHICOLA_01 = 4;
	private final int APALACHICOLA_02 = 5;
	private final int APALACHICOLA_03 = 6;

	public static final int AMOUNT_OF_ROASTS = 7;

	private static Roast[] roast = new Roast[AMOUNT_OF_ROASTS];

	public void loadRoastLoader() {
		roast[MEXICO_BEACH] = new Roast(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 60,
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 20
				);
		roast[STUMP_HOLE] = new Roast(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 6,
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 45
				);
		roast[THE_POINT] = new Roast(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 8,
				GameAttributeHelper.CHUNK_FIVE_Y_POSITION_START - 97
				);
		roast[WEWA] = new Roast(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 40,
				11
				);
		roast[APALACHICOLA_01] = new Roast(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 25,
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 38
				);
		roast[APALACHICOLA_02] = new Roast(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 26,
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 49
				);
		roast[APALACHICOLA_03] = new Roast(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 27,
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 40
				);

		for (int i = 0; i < roast.length; i++) {
			GameObjectLoader.gameObjectList.add(roast[i]);
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public static void updateRoasts(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < roast.length; i++) {
			roast[i].updateObject(myGame, mapHandler);
		}
	}
}
