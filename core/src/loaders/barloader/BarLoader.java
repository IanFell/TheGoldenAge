package loaders.barloader;

import com.mygdx.mygame.MyGame;

import gameobjects.stationarygameobjects.buildings.Bar;
import helpers.GameAttributeHelper;
import loaders.BuildingLoader;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class BarLoader extends BuildingLoader {

	public static Bar bar;

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void loadBar(MyGame myGame) {
		bar = new Bar(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START + 43, 
				GameAttributeHelper.CHUNK_THREE_Y_POSITION_START + 10,
				BUILDING_WIDTH,
				BUILDING_HEIGHT,
				myGame.imageLoader.bar
				);
		GameObjectLoader.gameObjectList.add(bar);
	}
}
