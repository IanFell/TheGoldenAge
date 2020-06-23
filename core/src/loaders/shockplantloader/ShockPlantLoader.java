package loaders.shockplantloader;

import gameobjects.GameObject;
import gameobjects.nature.shockplant.ShockPlant;
import helpers.GameAttributeHelper;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ShockPlantLoader {

	public static ShockPlant[] shockPlant = new ShockPlant[1];

	public void loadShockPlants() {
		shockPlant[0] = new ShockPlant(
				GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 45, 
				GameAttributeHelper.CHUNK_ONE_Y_POSITION_START + 10
				);

		for (int i = 0; i < shockPlant.length; i++) {
			GameObjectLoader.gameObjectList.add(shockPlant[i]);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public static void updateShockPlants(GameObject player) {
		for (int i = 0; i < shockPlant.length; i++) {
			shockPlant[i].updateObject(player);
		}
	}
}
