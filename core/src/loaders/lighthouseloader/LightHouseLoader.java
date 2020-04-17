package loaders.lighthouseloader;

import com.mygdx.mygame.MyGame;

import gameobjects.stationarygameobjects.buildings.LightHouse;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class LightHouseLoader {

	public static LightHouse lightHouse;

	public static final int LIGHT_HOUSE_WIDTH  = 4;
	public static final int LIGHT_HOUSE_HEIGHT = 15;

	public void loadLightHouse(MyGame myGame) {
		lightHouse = new LightHouse(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 39, 
				GameAttributeHelper.CHUNK_FIVE_Y_POSITION_START + 9,
				LIGHT_HOUSE_WIDTH,
				LIGHT_HOUSE_HEIGHT,
				myGame.imageLoader.lightHouse
				);
		GameObjectLoader.gameObjectList.add(lightHouse);
	}
}
