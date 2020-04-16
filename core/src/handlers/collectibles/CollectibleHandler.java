package handlers.collectibles;

import gameobjects.GameObject;
import screens.GameScreen;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class CollectibleHandler {

	/**
	 * Determines if game object is rendering bounds.
	 * We use this method opposed to the method in GamePlayHelper, because the game play helper method
	 * deals with larger objects that need a larger offset.  For this, we don't need the offset as large,
	 * hopefully helping on some rendering memory.
	 * 
	 * @param GameObject gameObject
	 * @return boolean
	 */
	protected boolean gameObjectIsWithinScreenBounds(GameObject gameObject) {
		float cameraXPosition   = GameScreen.camera.position.x;
		float cameraYPosition   = GameScreen.camera.position.y;
		float playerXPosition   = gameObject.getX();
		float playerYPosition   = gameObject.getY();
		float screenBoundOffset = 17.0f;
		if (
				playerXPosition < cameraXPosition + screenBoundOffset &&
				playerXPosition > cameraXPosition - screenBoundOffset &&
				playerYPosition < cameraYPosition + screenBoundOffset &&
				playerYPosition > cameraYPosition - screenBoundOffset
				) {
			return true;
		}
		return false;
	}
}
