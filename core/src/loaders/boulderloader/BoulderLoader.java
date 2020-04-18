package loaders.boulderloader;

import java.util.ArrayList;

import gameobjects.GameObject;
import gameobjects.nature.rocks.Boulder;
import helpers.GameAttributeHelper;
import loaders.ClassObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class BoulderLoader extends ClassObjectLoader {

	public static ArrayList <GameObject> boulders = new ArrayList <GameObject>();

	public void loadBoulders() {
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 44, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 25
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 46, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 23
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 45, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 21
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 46, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 19
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 46, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 17
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 47, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 15
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 47, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 13
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 48, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 11
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 49, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 9
				));
		boulders.add(new Boulder(
				GameAttributeHelper.CHUNK_FOUR_X_POSITION_START - 50, 
				GameAttributeHelper.CHUNK_SEVEN_Y_POSITION_START + 7
				));
		addGameObjectsToGameObjectArrayList(boulders);
	}
}
