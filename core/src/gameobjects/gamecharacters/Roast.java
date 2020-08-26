package gameobjects.gamecharacters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import loaders.ImageLoader;
import maps.MapHandler;
import physics.Lighting.Fire;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Roast extends GameCharacter {

	private Fire fire;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 */
	public Roast(float x, float y) {
		this.x      = x;
		this.y      = y;
		int size    = 3;
		this.width  = size;
		this.height = size;
		fire        = new Fire(x + 1, y, 1, 2, "Roast", true);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		fire.renderObject(batch, imageLoader);
		batch.draw(imageLoader.roast, x, y, width, -height);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		fire.updateObject(myGame, mapHandler);
	}
}
