package gameobjects.gamecharacters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class RockBird extends GameCharacter {

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public RockBird(float x, float y) {
		this.x           = x;
		this.y           = y;
		float size       = 1;
		this.width       = size;
		this.height      = size;
		rectangle.width  = width;
		rectangle.height = height;
		this.dx          = 0.5f;
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

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 * @param float      leftBoundary
	 * @param float      rightBoundary
	 */
	public void updateObject(MyGame myGame, MapHandler mapHandler, float leftBoundary, float rightBoundary) {
		x += dx;
		if (x < leftBoundary) {
			dx = 1;
		} else if (x > rightBoundary) {
			dx = -1;
		}
	}
}
