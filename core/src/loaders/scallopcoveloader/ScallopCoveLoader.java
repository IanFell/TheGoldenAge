package loaders.scallopcoveloader;

import com.mygdx.mygame.MyGame;

import gameobjects.stationarygameobjects.buildings.ScallopCove;
import helpers.GameAttributeHelper;
import loaders.BuildingLoader;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ScallopCoveLoader extends BuildingLoader {

	public static ScallopCove scallopCove;

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void loadScallopCove(MyGame myGame) {
		scallopCove = new ScallopCove(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 10, 
				GameAttributeHelper.CHUNK_FIVE_Y_POSITION_START - 90,
				BUILDING_WIDTH,
				BUILDING_HEIGHT,
				myGame.imageLoader.scallopCove
				);
		GameObjectLoader.gameObjectList.add(scallopCove);
	}
}
