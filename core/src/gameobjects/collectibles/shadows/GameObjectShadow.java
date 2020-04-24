package gameobjects.collectibles.shadows;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class GameObjectShadow extends GameObject {

	protected Texture shadowTexture;

	/**
	 * Constructor.
	 * 
	 * @param float   x
	 * @param float   y
	 * @param float   width
	 * @param float   height
	 * @param Texture shadowTexture
	 */
	public GameObjectShadow(float x, float y, float width, float height, Texture shadowTexture) {
		this.x             = x;
		this.y             = y;
		this.width         = width;
		this.height        = height;
		this.shadowTexture = shadowTexture;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(shadowTexture, x, y, width, -height);
	}
}
