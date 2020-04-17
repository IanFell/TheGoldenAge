package transitions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.mygame.MyGame;

import gameobjects.GameObject;
import gameobjects.gamecharacters.Player;
import loaders.ImageLoader;

/**
 * 
 * @author Fabulous Fellini
 *
 */
public class Transition {

	private float x;
	private float y;
	private float width;
	private float height;
	private float initialSize         = 10;
	private float transitionSizeValue = 30.0f;
	private final float MAX_WIDTH     = 2590;

	/**
	 * Constructor.
	 * 
	 * @param MyGame myGame
	 */
	public Transition(MyGame myGame) {
		GameObject player = myGame.getGameObject(Player.PLAYER_ONE);
		this.x            = player.getX() - 5;
		this.y            = player.getY() - 5;
		this.width        = initialSize;
		this.height       = initialSize;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	/**
	 * 
	 * @param SpriteBatch batch
	 * @param ImageLoader imageLoader
	 */
	public void renderTransition(SpriteBatch batch, ImageLoader imageLoader) {
		if (width < MAX_WIDTH) {
			batch.draw(imageLoader.transition, x, y, width, height);
		}
	}

	public void updateTransition() {
		if (width < MAX_WIDTH) {
			x      -= transitionSizeValue;
			y      -= transitionSizeValue;
			width  += transitionSizeValue * 2;
			height += transitionSizeValue * 2;
		}
	}
}
