package gameobjects.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import handlers.CollisionHandler;
import helpers.GamePlayHelper;
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
			this.width  = 1.0f;
			this.height = 0.1f;
		} else {
			this.width  = 0.1f;
			this.height = 1.0f;
		}
		this.rectangle.width  = width;
		this.rectangle.height = height;
		float speed = 1.0f;
		switch (directionOfArrow) {
		case DIRECTION_RIGHT:
			dx = speed;
			break;
		case DIRECTION_LEFT:
			dx = -speed;
			break;
		case DIRECTION_UP:
			dy = -speed;
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
		CollisionHandler.checkIfArrowHasCollidedWithPlayer(myGame.getGameObject(Player.PLAYER_ONE), this);
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		if (GamePlayHelper.gameObjectIsWithinScreenBounds(this)) {
			Texture texture = null;
			switch (directionOfArrow) {
			case GameObject.DIRECTION_RIGHT:
				texture = imageLoader.arrowRight;
				break;
			case GameObject.DIRECTION_LEFT:
				texture = imageLoader.arrowLeft;
				break;
			case GameObject.DIRECTION_UP:
				texture = imageLoader.arrowUp;
				break;
			}
			batch.draw(texture, x, y, width, -height);
		}
	}
}
