package gameobjects.gamecharacters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * These birds just fly in the world.  They do not hurt player.
 * 
 * @author Fabulous Fellini
 *
 */
public class FlyingBird extends GameCharacter {

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public FlyingBird(float x, float y) {
		this.x      = x;
		this.y      = y;
		int size    = 1;
		this.width  = size;
		this.height = size;
		this.dy     = 0.3f;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.attackBird, x, y, width, -height);
	}

	public void updateBird() {
		y += dy;
		if (y < 0) {
			dy = 0.3f;
		} else if (y > GameAttributeHelper.CHUNK_EIGHT_Y_POSITION_START + 50) {
			dy = -0.3f;
		}
	}
}
