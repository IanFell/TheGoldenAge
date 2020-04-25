package handlers.arrowhandler;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import maps.MapHandler;
import spawners.ArrowSpawner;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ArrowHandler {

	private ArrowSpawner[] arrowSpawner = new ArrowSpawner[3];

	private final int MEXICO_BEACH = 0;
	private final int APALACHICOLA = 1;
	private final int STUMP_HOLE   = 2;

	public void init() {
		arrowSpawner[MEXICO_BEACH] = new ArrowSpawner(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 82,
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 40,
				MEXICO_BEACH,
				GameObject.DIRECTION_RIGHT
				);
		arrowSpawner[APALACHICOLA] = new ArrowSpawner(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 59,
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 3,
				APALACHICOLA,
				GameObject.DIRECTION_LEFT
				);
		arrowSpawner[STUMP_HOLE] = new ArrowSpawner(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START + 9,
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 45,
				STUMP_HOLE,
				GameObject.DIRECTION_UP
				);
		
		for (int i = 0; i < arrowSpawner.length; i++) {
			GameObjectLoader.gameObjectList.add(arrowSpawner[i]);
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateArrowHandler(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < arrowSpawner.length; i++) {
			arrowSpawner[i].updateArrowSpawner(myGame, mapHandler);
		}
	}
}
