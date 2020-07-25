package loaders.poisonplantloader;

import java.util.ArrayList;

import gameobjects.GameObject;
import gameobjects.PoisonPlant;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import loaders.GameObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class PoisonPlantLoader {

	public static ArrayList <PoisonPlant> poisonPlant = new ArrayList <PoisonPlant>();

	public final static int AMOUNT_OF_POISON_PLANTS = 100;

	public void loadPoisonPlants() {
		for (int i = 0; i < AMOUNT_OF_POISON_PLANTS; i++) {
			addRandomlyPlacedPoisonPlant();
		}
		for (int i = 0; i < AMOUNT_OF_POISON_PLANTS; i++) {
			GameObjectLoader.gameObjectList.add(poisonPlant.get(i));
		}
	}

	private void addRandomlyPlacedPoisonPlant() {
		int startX = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60);
		int startY = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 50);
		poisonPlant.add(new PoisonPlant(startX, startY));
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public static void updatePoisonPlants(GameObject player) {
		for (int i = 0; i < AMOUNT_OF_POISON_PLANTS; i++) {
			poisonPlant.get(i).updateObject(player);
		}
	}
}
