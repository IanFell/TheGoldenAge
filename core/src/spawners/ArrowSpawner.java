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

	private Arrow[] arrows = new Arrow[4];

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float Y
	 */
	public ArrowSpawner(float x, float y) {
		this.x      = x;
		this.y      = y;
		this.width  = 5;
		this.height = 5;
		float yPos  = y;
		for (int i = 0; i < arrows.length; i++) {
			arrows[i] = new Arrow(x, yPos);
			yPos += 1;
		}
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	public void updateArrowSpawner(MyGame myGame, MapHandler mapHandler) {
		for (int i = 0; i < arrows.length; i++) {
			arrows[i].updateObject(myGame, mapHandler);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderArrowSpawner(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.blackSquare, x, y, width, height);
		for (int i = 0; i < arrows.length; i++) {
			arrows[i].renderObject(batch, imageLoader);
		}
	}
}
