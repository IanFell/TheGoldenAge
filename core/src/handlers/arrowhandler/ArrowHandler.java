package handlers.arrowhandler;

import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import maps.MapHandler;
import maps.MapInformationHolder;
import spawners.ArrowSpawner;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ArrowHandler {

	private ArrowSpawner[] arrowSpawner = new ArrowSpawner[3];

	public static final int MEXICO_BEACH = 0;
	public static final int APALACHICOLA = 1;
	public static final int STUMP_HOLE   = 2;

	private final int RIGHT_BOUNDARY = GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + MapInformationHolder.CHUNK_WIDTH;
	private final int LEFT_BOUNDARY  = 0;
	private final int TOP_BOUNDARY   = 0;

	public void init() {
		float speed = 0.4f;
		arrowSpawner[MEXICO_BEACH] = new ArrowSpawner(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 82,
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 40,
				MEXICO_BEACH,
				GameObject.DIRECTION_RIGHT,
				speed,
				0
				);
		arrowSpawner[APALACHICOLA] = new ArrowSpawner(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 59,
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 8,
				APALACHICOLA,
				GameObject.DIRECTION_LEFT,
				-speed,
				0
				);
		arrowSpawner[STUMP_HOLE] = new ArrowSpawner(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START + 9,
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 45,
				STUMP_HOLE,
				GameObject.DIRECTION_UP,
				0,
				-speed
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
		if (arrowSpawner[ArrowHandler.MEXICO_BEACH].getArrow().getX() > RIGHT_BOUNDARY) {
			arrowSpawner[ArrowHandler.MEXICO_BEACH].getArrow().setX(arrowSpawner[ArrowHandler.MEXICO_BEACH].getX() + 1);
			arrowSpawner[ArrowHandler.MEXICO_BEACH].getArrow().setY(arrowSpawner[ArrowHandler.MEXICO_BEACH].getY() - 3);
		}
		else if (arrowSpawner[ArrowHandler.APALACHICOLA].getArrow().getX() < LEFT_BOUNDARY) {
			arrowSpawner[ArrowHandler.APALACHICOLA].getArrow().setX(arrowSpawner[ArrowHandler.APALACHICOLA].getX() + 1);
			arrowSpawner[ArrowHandler.APALACHICOLA].getArrow().setY(arrowSpawner[ArrowHandler.APALACHICOLA].getY() - 3);
		}
		else if (arrowSpawner[ArrowHandler.STUMP_HOLE].getArrow().getY() < TOP_BOUNDARY) {
			arrowSpawner[ArrowHandler.STUMP_HOLE].getArrow().setX(arrowSpawner[ArrowHandler.STUMP_HOLE].getX() + 1);
			arrowSpawner[ArrowHandler.STUMP_HOLE].getArrow().setY(arrowSpawner[ArrowHandler.STUMP_HOLE].getY());
		}
	}
}
