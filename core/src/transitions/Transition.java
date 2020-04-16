package transitions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Transition {

	private float x;
	private float y;
	private float width;
	private float height;
	private float initialSize         = 10;
	private float transitionSizeValue = 15.0f;
	private final float MAX_WIDTH     = 2590;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public Transition(float x, float y) {
		this.x      = x;
		this.y      = y;
		this.width  = initialSize;
		this.height = initialSize;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderTransition(SpriteBatch batch, ImageLoader imageLoader) {
		if (width < MAX_WIDTH) {
			batch.draw(imageLoader.transition, x, y, width, height);
		}
	}

	public void updateTransition() {
		if (width < MAX_WIDTH) {
			x      -= transitionSizeValue;
			y      -= transitionSizeValue;
			width  += transitionSizeValue * 2;
			height += transitionSizeValue * 2;
		}
	}
}
