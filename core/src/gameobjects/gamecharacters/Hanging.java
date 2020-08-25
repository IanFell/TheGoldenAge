package gameobjects.gamecharacters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Hanging extends GameCharacter {

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public Hanging(float x, float y) {
		this.x      = x;
		this.y      = y;
		int size    = 7;
		this.width  = size;
		this.height = size;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.hangingShadow, x - 0.5f, y - 0.5f, width, -height);
		batch.draw(imageLoader.hanging, x, y, width, -height);
	}
}
