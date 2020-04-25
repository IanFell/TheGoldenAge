package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Arrow extends Weapon {

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 * @param int   direction
	 */
	public Arrow(float x, float y, int direction) {
		super(x, y);
		this.width            = 2;
		this.height           = 0.1f;
		this.rectangle.width  = width;
		this.rectangle.height = height;
		switch (direction) {
		case DIRECTION_RIGHT:
			dx = 1.0f;
			break;
		}
	}

	/**
	 * 
	 * @param MyGame     myGamet
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);
		this.rectangle.x = x;
		this.rectangle.y = y;
		x += dx;
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.whiteSquare,
				x, 
				y,
				width,
				height
				);
	}
}
