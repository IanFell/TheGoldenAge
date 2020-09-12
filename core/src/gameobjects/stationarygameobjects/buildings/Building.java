package gameobjects.stationarygameobjects.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import gameobjects.stationarygameobjects.GamePlayObject;
import loaders.ImageLoader;
import store.Store;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Building extends GamePlayObject {

	private Texture texture;
	
	private int enterStoreDisplayTimer = 0;

	/**
	 * Constructor.
	 * 
	 * @param int     x
	 * @param int     y
	 * @param int     width
	 * @param int     height
	 * @param Texture texture
	 */
	public Building(int x, int y, int width, int height, Texture texture) {
		super(x, y);
		this.width       = width;
		this.height      = height;
		this.rectangle.x = width;
		this.rectangle.y = height;
		this.texture     = texture;
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
	
	/**
	 * Handles "enter store" UI display when buildings are scrolled over.
	 */
	protected void handleEnterStoreTimer() {
		if (Store.shouldDisplayEnterStoreMessageAlternate) {
			enterStoreDisplayTimer++;
			if (enterStoreDisplayTimer > 50) {
				enterStoreDisplayTimer = 0;
				Store.shouldDisplayEnterStoreMessageAlternate = false;
			}
		}
	}
}
