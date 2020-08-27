package loaders.floatingboatloader;

import com.mygdx.mygame.MyGame;

import gameobjects.FloatingBoat;
import gameobjects.GameObject;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class FloatingBoatLoader {

	public static FloatingBoat floatingBoatUp;
	public static FloatingBoat floatingBoatLeft;

	public void loadFloatingBoats() {
		floatingBoatUp = new FloatingBoat(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 30, 
				0, 
				GameObject.DIRECTION_UP
				);
		floatingBoatLeft = new FloatingBoat(
				0, 
				GameAttributeHelper.CHUNK_TWO_Y_POSITION_START, 
				GameObject.DIRECTION_LEFT
				);

		GameObjectLoader.gameObjectList.add(floatingBoatUp);
		GameObjectLoader.gameObjectList.add(floatingBoatLeft);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateFloatingBoats(MyGame myGame, MapHandler mapHandler) {
		floatingBoatUp.updateObject(myGame, mapHandler);
		floatingBoatLeft.updateObject(myGame, mapHandler);
	}
}
