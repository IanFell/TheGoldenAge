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
public class TenHearts extends GameObjectCollectible {

	public static final float HEALTH = 10;

	public static boolean playSound  = false;

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public TenHearts(int x, int y) {
		this.x           = x;
		this.y           = y;
		int size         = 2;
		this.width       = size;
		this.height      = size;
		rectangle.x      = x;
		rectangle.y      = y;
		rectangle.width  = size;
		rectangle.height = size;
		hasBeenCollected = false;
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.tenHearts,
				x,
				y,
				width,
				-height
				);
		//renderHitBox(batch, imageLoader);
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ImageLoader   imageLoader
	 */
	private void renderHitBox(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(
				imageLoader.whiteSquare,
				rectangle.x,
				rectangle.y,
				rectangle.width,
				-rectangle.height
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
			CollisionHandler.checkIfPlayerCollidedWithTenHearts(PlayerController.getCurrentPlayer(myGame), this);
			//handleMovement();
		} else {
			grow();
		}
	}
}
