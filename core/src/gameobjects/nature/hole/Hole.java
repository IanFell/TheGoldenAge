package gameobjects.nature.hole;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.gamecharacters.players.Player;
import gameobjects.nature.NatureObject;
import handlers.CollisionHandler;
import loaders.ImageLoader;
import maps.MapHandler;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Hole extends NatureObject {

	/**
	 * Constructor.
	 * 
	 * @param int x
	 * @param int y
	 */
	public Hole(int x, int y) {
		super(x, y);
		this.width       = 3;
		this.height      = 1;
		rectangle.x      = x;
		rectangle.y      = y;
		rectangle.width  = width;
		rectangle.height = height;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.hole, x, y, width, height);
		//batch.draw(imageLoader.whiteSquare, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
	}

	/**
	 * 
	 * @param MyGame     myGame
	 * @param MapHandler mapHandler
	 */
	@Override
	public void updateObject(MyGame myGame, MapHandler mapHandler) {
		CollisionHandler.checkIfPlayerHasCollidedWithHole(myGame.getGameObject(Player.PLAYER_ONE), this);
	}
}
