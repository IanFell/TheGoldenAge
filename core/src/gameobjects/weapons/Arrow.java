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
	 */
	public Arrow(float x, float y) {
		super(x, y);
		this.width            = 3;
		this.height           = 0.1f;
		this.rectangle.width  = width;
		this.rectangle.height = height;
		dx                    = 0.1f;
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
