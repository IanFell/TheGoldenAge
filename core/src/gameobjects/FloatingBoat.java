package gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import helpers.GameAttributeHelper;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class FloatingBoat extends GameObject {

	private int boatDirection;

	private float speed = 0.1f;

	/**
	 * Constructor.
	 * 
	 * @param float x
	 * @param float y
	 * @param innt  boatDirection
	 */
	public FloatingBoat(float x, float y, int boatDirection) {
		this.x             = x;
		this.y             = y;
		int size           = 3;
		this.width         = size;
		this.height        = size;
		this.boatDirection = boatDirection;
		this.dx = speed;
		this.dy = 0;
		if (boatDirection == DIRECTION_UP) {
			this.dx = 0;
			this.dy = speed;
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		Texture boatTexture = imageLoader.boatSide;
		if (boatDirection == DIRECTION_UP) {
			boatTexture = imageLoader.boatDown;
		}
		batch.draw(boatTexture, x, y, width, -height);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		x += dx;
		y += dy;
		if (boatDirection == DIRECTION_LEFT) {
			if (x < 0) {
				dx = speed;
			}
			if (x > GameAttributeHelper.CHUNK_TWO_X_POSITION_START + 31) {
				dx = -speed;
			}
		}
		else if (boatDirection == DIRECTION_UP) {
			if (y < 0) {
				dy = speed;
			}
			if (y > GameAttributeHelper.CHUNK_FOUR_Y_POSITION_START) {
				dy = -speed;
			}
		}
	}
}
