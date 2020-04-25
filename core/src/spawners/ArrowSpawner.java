package spawners;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.weapons.Arrow;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class ArrowSpawner extends GameObject {

	private Arrow arrow;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float Y
	 */
	public ArrowSpawner(float x, float Y) {
		this.x      = x;
		this.dy     = y;
		this.width  = 5;
		this.height = 5;
		arrow       = new Arrow(x, y);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateArrowSpawner(MyGame myGame, MapHandler mapHandler) {
		arrow.updateObject(myGame, mapHandler);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderArrowSpawner(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.blackSquare, x, y, width, height);
		arrow.renderObject(batch, imageLoader);
	}
}
