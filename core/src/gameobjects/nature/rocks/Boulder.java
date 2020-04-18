package gameobjects.nature.rocks;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.nature.NatureObject;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Boulder extends NatureObject {

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public Boulder(int x, int y) {
		super(x, y);
		this.width  = 5;
		this.height = 5;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		renderNatureObject(batch, imageLoader.rock);
	}
}
