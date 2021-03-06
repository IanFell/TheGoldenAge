package ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import handlers.InputHandler;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Pause extends GameObject {

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public Pause(float x, float y) {
		this.x      = x;
		this.dy     = y;
		this.width  = 12;
		this.height = 3;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.blackSquare,
				x - 10,
				y + 10,
				40, 
				-40
				);
		batch.draw(imageLoader.gamePaused, x, y + height / 2, width, -height);
		
		if (InputHandler.inputType == InputHandler.INPUT_CONTROLLER) {
			batch.draw(imageLoader.newGame, x, y + height / 2 + 4, width, -height);
			batch.draw(imageLoader.exitGame, x, y + height / 2 + 6, width, -height);
		} else {
			// If it's mouse and keyboard.  Arcade doesn't have a pause.
			batch.draw(imageLoader.newGameN, x, y + height / 2 + 4, width, -height);
			batch.draw(imageLoader.exitGameQ, x, y + height / 2 + 6, width, -height);
		}
	}

	/**
	 * 
	 * @param GameObject player
	 */
	public void updateObject(GameObject player) {
		x = player.getX() - width / 2;
		y = player.getY() - height / 2;
	}
}
