package loaders.shockplantloader;

import java.util.ArrayList;

import gameobjects.GameObject;
import gameobjects.nature.shockplant.ShockPlant;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ShockPlantLoader {

	public static ArrayList <ShockPlant> shockPlant = new ArrayList <ShockPlant>();
	
	public final static int AMOUNT_OF_SHOCK_PLANTS = 200;

	public void loadShockPlants() {
		for (int i = 0; i < AMOUNT_OF_SHOCK_PLANTS; i++) {
			addRandomlyPlacedShockPlant();
		}
		for (int i = 0; i < AMOUNT_OF_SHOCK_PLANTS; i++) {
			GameObjectLoader.gameObjectList.add(shockPlant.get(i));
		}
	}
	
	private void addRandomlyPlacedShockPlant() {
		int startX = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60);
		int startY = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 50);
		shockPlant.add(new ShockPlant(startX, startY));
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public static void updateShockPlants(GameObject player) {
		for (int i = 0; i < AMOUNT_OF_SHOCK_PLANTS; i++) {
			shockPlant.get(i).updateObject(player);
		}
	}
}
