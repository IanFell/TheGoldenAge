package gameobjects.nature;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.players.Player;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Bubble extends NatureObject {

	private float size;

	/**
	 * Constructor.
	 * 
	 * @param int   x
	 * @param int   y
	 * @param float size
	 * @param float dy
	 */
	public Bubble(int x, int y, float size, float dy) {
		super(x, y);
		this.size = size;
		this.dy   = dy;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	@Override
	public void renderObject(SpriteBatch batch, ImageLoader imageLoader) {
		batch.draw(imageLoader.bubble, x, y, size, -size);
	}

	/**
	 * 
	 * @param MyGame myGame
	 */
	public void updateObject(MyGame myGame) {
		GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
		float top         = player.getY() - 7;
		float bottom      = player.getY() + 7;
		y -= dy;
		if (y < top) {
			y = bottom;
		}
	}
}
