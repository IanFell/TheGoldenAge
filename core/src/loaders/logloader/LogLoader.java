package loaders.logloader;

import java.util.ArrayList;

import gameobjects.GameObject;
import gameobjects.logs.Log;
import helpers.GameAttributeHelper;
import helpers.RandomNumberGenerator;
import loaders.ClassObjectLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class LogLoader extends ClassObjectLoader {

	private final int AMOUNT_OF_LOGS = 300;

	public static ArrayList <GameObject> logs = new ArrayList <GameObject>();

	public void loadLogs() {
		for (int i = 0; i < AMOUNT_OF_LOGS; i++) {
			addRandomlyPlacedLog();
		}
		addGameObjectsToGameObjectArrayList(logs);
	}

	private void addRandomlyPlacedLog() {
		int startX = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_X_POSITION_START + 60);
		int startY = RandomNumberGenerator.generateRandomInteger(GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 50);
		logs.add(new Log(startX, startY));
	}
}
