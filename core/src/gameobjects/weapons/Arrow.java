package gameobjects.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import cutscenes.CutScene;
import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import handlers.CollisionHandler;
import inventory.Inventory;
import loaders.ImageLoader;
import maps.MapHandler;
import ui.MapUi;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Arrow extends Weapon {

	private int directionOfArrow;
	private float dx;
	private float dy;

	/**
	 * Constructor.
	 * 
	 * directionOfArrow helps determine which image to render.
	 * 
	 * @param float x
	 * @param float y
	 * @param int   directionOfArrow
	 * @param float dx
	 * @param float dy
	 */
	public Arrow(float x, float y, int directionOfArrow, float dx, float dy) {
		super(x, y);
		this.dx               = dx;
		this.dy               = dy;
		this.directionOfArrow = directionOfArrow;
		if (directionOfArrow == GameObject.DIRECTION_RIGHT || directionOfArrow == GameObject.DIRECTION_LEFT) {
			this.width  = 1.0f;
			this.height = 2.0f;
		} else {
			this.width  = 2.0f;
			this.height = 1.0f;
		}
		rectangle.width  = width;
		rectangle.height = height;
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
		x += dx;
		y += dy;
		rectangle.x = x;
		rectangle.y = y;

		if (!Inventory.allInventoryShouldBeRendered && !MapUi.mapShouldBeRendered && !CutScene.gameShouldPause) {
			CollisionHandler.checkIfArrowHasCollidedWithPlayer(myGame.getGameObject(Player.PLAYER_ONE), this);
		}
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
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
