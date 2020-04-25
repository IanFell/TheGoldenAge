package gameobjects.weapons;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Arrow extends Weapon {

	private int directionOfArrow;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 * @param int   directionOfArrow
	 */
	public Arrow(float x, float y, int directionOfArrow) {
		super(x, y);
		this.directionOfArrow = directionOfArrow;
		if (directionOfArrow == GameObject.DIRECTION_RIGHT || directionOfArrow == GameObject.DIRECTION_LEFT) {
			this.width  = 2.0f;
			this.height = 0.1f;
		} else {
			this.width  = 0.1f;
			this.height = 2.0f;
		}
		this.rectangle.width  = width;
		this.rectangle.height = height;
		switch (directionOfArrow) {
		case DIRECTION_RIGHT:
			dx = 1.0f;
			break;
		case DIRECTION_LEFT:
			dx = -1.0f;
			break;
		case DIRECTION_UP:
			dy = -.5f;
			break;
		}
	}

	/**
	 * 
	 * @return int
	 */
	public int getDirectionOfArrow() {
		return directionOfArrow;
	}

	/**
	 * 
	 * @param MyGame     myGamet
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		super.updateObject(myGame, mapHandler);
		x += dx;
		y += dy;
		this.rectangle.x = x;
		this.rectangle.y = y;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
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
