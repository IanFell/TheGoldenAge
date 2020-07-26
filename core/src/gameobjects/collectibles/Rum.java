package gameobjects.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import handlers.CollisionHandler;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Rum extends GameObjectCollectible {

	public static boolean playSound;
	
	public static boolean playDrinkingSound = false;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public Rum(int x, int y) {
		this.x           = x;
		this.y           = y;
		int size         = 1;
		this.width       = size;
		this.height      = size;
		rectangle.x      = x;
		rectangle.y      = y - height;
		rectangle.width  = size;
		rectangle.height = size;
		playSound        = false;
		setMovement();
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.rum,
				x,
				y,
				width,
				-height
				);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		if (!hasBeenCollected) {
			CollisionHandler.checkIfPlayerCollidedWithRum(PlayerController.getCurrentPlayer(myGame), this);
			handleMovement();
		} else {
			grow();
		}
	}
}
