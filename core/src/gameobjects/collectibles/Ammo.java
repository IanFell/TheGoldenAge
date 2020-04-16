package gameobjects.collectibles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.mygame.MyGame;

import controllers.PlayerController;
import gameobjects.GameObject;
import handlers.CollisionHandler;
import loaders.ImageLoader;
import maps.MapHandler;

public class Ammo extends GameObject {
	
	public static boolean playSound = false;
	
	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public Ammo(int x, int y) {
		this.x           = x;
		this.y           = y;
		int size         = 1;
		this.width       = size;
		this.height      = size;
		rectangle.x      = x;
		rectangle.y      = y - 1;
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
				imageLoader.ammo,
				x,
				y,
				width,
				-height
				);
		//renderHitBox(batch, shapeRenderer, imageLoader);
	}

	/**
	 * 
	 * @param SpriteBatch   batch
	 * @param ShapeRenderer shapeRenderer
	 * @param ImageLoader   imageLoader
	 */
	private void renderHitBox(SpriteBatch batch, ShapeRenderer shapeRenderer, ImageLoader imageLoader) {
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
			CollisionHandler.checkIfPlayerCollidedWithAmmo(PlayerController.getCurrentPlayer(myGame), this);
		} else {
			grow();
		}
	}

}
