package handlers.collectibles;

import gameobjects.GameObject;
import screens.GameScreen;

public class CollectibleHandler {
	
	/**
	 * Determines if game object is rendering bounds.
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
