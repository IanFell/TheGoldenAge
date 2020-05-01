package handlers.arrowhandler;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.weapons.Arrow;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import loaders.ImageLoader;
import maps.MapHandler;
import maps.MapInformationHolder;
import spawners.ArrowSpawner;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ArrowHandler {

	public static ArrowSpawner[] arrowSpawner = new ArrowSpawner[3];

	public static Arrow[] arrows = new Arrow[3];

	public static final int MEXICO_BEACH = 0;
	public static final int APALACHICOLA = 1;
	public static final int STUMP_HOLE   = 2;

	private final int RIGHT_BOUNDARY = GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + MapInformationHolder.CHUNK_WIDTH;
	private final int LEFT_BOUNDARY  = 0;
	private final int TOP_BOUNDARY   = 0;

	/**
	 * 
	 * @param float speed
	 */
	private void initializeArrows(float speed) {
		arrows[MEXICO_BEACH] = new Arrow(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 82,
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 40,
				GameObject.DIRECTION_RIGHT,
				speed,
				0
				);
		arrows[APALACHICOLA] = new Arrow(
				GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 59,
				GameAttributeHelper.CHUNK_SIX_Y_POSITION_START + 8,
				GameObject.DIRECTION_LEFT,
				-speed,
				0
				);
		arrows[STUMP_HOLE] = new Arrow(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START + 9,
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 45,
				GameObject.DIRECTION_UP,
				0,
				-speed
				);
	}

	/**
	 * 
	 * @param float speed
	 */
	private void initializeForts(float speed) {
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
	}

	public void init() {
		float speed = 0.4f;
		initializeArrows(speed);
		initializeForts(speed);
		// Don't add actual arrows to game object list so we can see them at all times.
		// TODO look into changing the above.
		for (int i = 0; i < arrowSpawner.length; i++) {
			GameObjectLoader.gameObjectList.add(arrowSpawner[i]);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderArrows(SpriteBatch batch, ImageLoader imageLoader) {
		for (int i = 0; i < arrows.length; i++) {
			arrows[i].renderObject(batch, imageLoader);
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateArrowHandler(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < arrows.length; i++) {
			arrows[i].updateObject(myGame, mapHandler);
		}
		if (arrows[MEXICO_BEACH].getX() > RIGHT_BOUNDARY) {
			arrows[MEXICO_BEACH].setX(arrowSpawner[ArrowHandler.MEXICO_BEACH].getX() + 1);
			arrows[MEXICO_BEACH].setY(arrowSpawner[ArrowHandler.MEXICO_BEACH].getY() - 3);
		}
		else if (arrows[APALACHICOLA].getX() < LEFT_BOUNDARY) {
			arrows[APALACHICOLA].setX(arrowSpawner[ArrowHandler.APALACHICOLA].getX() + 1);
			arrows[APALACHICOLA].setY(arrowSpawner[ArrowHandler.APALACHICOLA].getY() - 3);
		}
		else if (arrows[STUMP_HOLE].getY() < TOP_BOUNDARY) {
			arrows[STUMP_HOLE].setX(arrowSpawner[ArrowHandler.STUMP_HOLE].getX() + 1);
			arrows[STUMP_HOLE].setY(arrowSpawner[ArrowHandler.STUMP_HOLE].getY());
		}
	}
}
