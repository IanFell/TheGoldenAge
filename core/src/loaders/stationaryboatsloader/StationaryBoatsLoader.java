package loaders.stationaryboatsloader;

import com.mygdx.mygame.MyGame;

import gameobjects.stationarygameobjects.buildings.StationaryBoats;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class StationaryBoatsLoader {

	public static StationaryBoats[] stationaryBoatsMexicoBeach = new StationaryBoats[3];
	public static StationaryBoats[] stationaryBoatsThePoint    = new StationaryBoats[2];
	public static StationaryBoats[] stationaryBoatsStGeorge    = new StationaryBoats[2];

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void loadStationaryBoats(MyGame myGame) {
		int size = 3;
		stationaryBoatsMexicoBeach[0] = new StationaryBoats(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 31, 
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 3,
				size,
				size,
				myGame.imageLoader.boatSide
				);
		stationaryBoatsMexicoBeach[1] = new StationaryBoats(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 33, 
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 6,
				size,
				size,
				myGame.imageLoader.boatSide
				);
		stationaryBoatsMexicoBeach[2] = new StationaryBoats(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 29, 
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 6,
				size,
				size,
				myGame.imageLoader.boatSide
				);

		stationaryBoatsThePoint[0] = new StationaryBoats(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 6, 
				GameAttributeHelper.CHUNK_FIVE_Y_POSITION_START - 114,
				size,
				size,
				myGame.imageLoader.boatSide
				);
		stationaryBoatsThePoint[1] = new StationaryBoats(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 4, 
				GameAttributeHelper.CHUNK_FIVE_Y_POSITION_START - 111,
				size,
				size,
				myGame.imageLoader.boatSide
				);

		stationaryBoatsStGeorge[0] = new StationaryBoats(
				GameAttributeHelper.CHUNK_SEVEN_X_POSITION_START + 15, 
				GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START,
				size,
				size,
				myGame.imageLoader.boatSide
				);
		stationaryBoatsStGeorge[1] = new StationaryBoats(
				GameAttributeHelper.CHUNK_SEVEN_X_POSITION_START + 12, 
				GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 3,
				size,
				size,
				myGame.imageLoader.boatSide
				);

		GameObjectLoader.gameObjectList.add(stationaryBoatsMexicoBeach[0]);
		GameObjectLoader.gameObjectList.add(stationaryBoatsMexicoBeach[1]);
		GameObjectLoader.gameObjectList.add(stationaryBoatsMexicoBeach[2]);
		GameObjectLoader.gameObjectList.add(stationaryBoatsThePoint[0]);
		GameObjectLoader.gameObjectList.add(stationaryBoatsThePoint[1]);
		GameObjectLoader.gameObjectList.add(stationaryBoatsStGeorge[0]);
		GameObjectLoader.gameObjectList.add(stationaryBoatsStGeorge[1]);
	}
}
