package gameobjects.stationarygameobjects.buildings.shadows;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.GameObject;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class LightHouseShadow extends GameObject {

	private Texture texture;

	/**
	 * Constructor.
	 * 
	 * @param float   x
	 * @param float   y
	 * @param float   width
	 * @param float   height
	 * @param Texture texture
	 */
	public LightHouseShadow(float x, float y, float width, float height, Texture texture) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(texture, x, y, width, -height);
	}
}
