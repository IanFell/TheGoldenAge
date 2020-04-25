package gameobjects.collectibles;

import gameobjects.GameObject;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class GameObjectCollectible extends GameObject {

	protected float offset        = 0.1f;
	protected float movementSpeed = .01f;
	protected float top;
	protected float bottom;

	protected void handleMovement() {
		if (y < top) {
			dy = movementSpeed;
		} else  if (y > bottom) {
			dy = -movementSpeed;
		}
		y += dy;
	}

	protected void setMovement() {
		top    = y - offset;
		bottom = y + offset;
		dy     = movementSpeed;
	}
}
